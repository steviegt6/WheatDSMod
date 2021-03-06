package io.github.steviegt6.wheatdsmod.mixin;

import io.github.steviegt6.wheatdsmod.blocks.MaterialCropBlock;
import io.github.steviegt6.wheatdsmod.registry.BlockRegistry;
import io.github.steviegt6.wheatdsmod.utilities.CropTier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(CropBlock.class)
public class CropBlockMixin {
    @Inject(
            method = "randomTick",
            at = @At("TAIL")
    )
    public void injectRandomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo info) {
        tryConvertWheat(world, random, pos, state, false);
    }

    @Inject(
            method = "grow",
            at = @At("TAIL")
    )
    public void injectGrow(ServerWorld world, Random random, BlockPos pos, BlockState state, CallbackInfo info) {
        tryConvertWheat(world, random, pos, state, true);
    }

    private void tryConvertWheat(ServerWorld world, Random random, BlockPos pos, BlockState state, Boolean boneMeal) {
        CropBlock cropBlock = (CropBlock) state.getBlock();

        if (cropBlock.getClass() != CropBlock.class)
            return;

        BlockPos blockPos = pos.down(2);
        Block beneathFarmlandBlock = world.getBlockState(blockPos).getBlock();
        Identifier beneathFarmlandBlockIdentifier = Registry.BLOCK.getId(beneathFarmlandBlock);

        if (BlockRegistry.BLOCK_CONVERTERS.containsKey(beneathFarmlandBlockIdentifier)) {
            MaterialCropBlock materialCropBlock = BlockRegistry.BLOCK_CONVERTERS.get(beneathFarmlandBlockIdentifier);
            CropTier tier = materialCropBlock.getDroppedItem().getCropTier();

            float chance = tier.getConversionChance();
            if (boneMeal)
                chance /= tier.getBoneMealConversionFactor();

            if (random.nextFloat() > chance)
                return;

            int age = state.get(CropBlock.AGE);
            state = materialCropBlock.withAge(age + 1);
            world.setBlockState(pos, state, 2);
        }
    }
}
