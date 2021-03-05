package io.github.steviegt6.wheatdsmod.mixin;

import io.github.steviegt6.wheatdsmod.items.AliasedCompostableBlockItem;
import io.github.steviegt6.wheatdsmod.registry.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.ai.brain.BlockPosLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.FarmerVillagerTask;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(FarmerVillagerTask.class)
public abstract class FarmerVillagerTaskMixin {
    @Shadow @Nullable private BlockPos currentTarget;

    @Shadow private long nextResponseTime;

    @Shadow @Final private List<BlockPos> targetPositions;

    @Shadow @Nullable protected abstract BlockPos chooseRandomTarget(ServerWorld world);

    @Inject(
            at = @At("TAIL"),
            method = "keepRunning"
    )
    protected void keepRunning(ServerWorld serverWorld, VillagerEntity villagerEntity, long l, CallbackInfo info) {
        if (currentTarget != null && currentTarget.isWithinDistance(villagerEntity.getPos(), 1) && l > nextResponseTime) {
            BlockState blockState = serverWorld.getBlockState(currentTarget);
            Block block = blockState.getBlock();
            Block underBlock = serverWorld.getBlockState(currentTarget.down()).getBlock();

            if (block instanceof CropBlock && ((CropBlock) block).isMature(blockState))
                serverWorld.breakBlock(currentTarget, true, villagerEntity);

            // TODO: Remake this method entirely, along with other usages of FarmlandBlock
            //  to account for future custom farmland types along with easier changes in the future
            if (blockState.isAir() && underBlock instanceof FarmlandBlock && villagerEntity.hasSeedToPlant()) {
                SimpleInventory inventory = villagerEntity.getInventory();

                for (int i = 0; i < inventory.size(); ++i) {
                    ItemStack stack = inventory.getStack(i);
                    boolean itemToPlace = false;

                    if (!stack.isEmpty() && stack.getItem() instanceof AliasedCompostableBlockItem && ItemRegistry.REGISTERED_SEEDS.contains(stack.getItem())) {
                        serverWorld.setBlockState(currentTarget, ((AliasedCompostableBlockItem) stack.getItem()).getBlock().getDefaultState(), 3);
                        itemToPlace = true;
                    }

                    if (!itemToPlace)
                        continue;

                    serverWorld.playSound(null, currentTarget.getX(), currentTarget.getY(), currentTarget.getZ(), SoundEvents.ITEM_CROP_PLANT, SoundCategory.BLOCKS, 1f, 1f);
                    stack.decrement(1);

                    if (stack.isEmpty())
                        inventory.setStack(i, ItemStack.EMPTY);
                }
            }

            if (block instanceof CropBlock && !((CropBlock) block).isMature(blockState)) {
                targetPositions.remove(currentTarget);
                currentTarget = chooseRandomTarget(serverWorld);

                if (currentTarget != null) {
                    nextResponseTime = l + 20;
                    villagerEntity.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(new BlockPosLookTarget(currentTarget), 0.5f, 1));
                    villagerEntity.getBrain().remember(MemoryModuleType.LOOK_TARGET, new BlockPosLookTarget(currentTarget));
                }
            }
        }
    }
}
