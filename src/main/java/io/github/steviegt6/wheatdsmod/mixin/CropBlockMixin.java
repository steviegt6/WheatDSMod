package io.github.steviegt6.wheatdsmod.mixin;

import io.github.steviegt6.wheatdsmod.blocks.MaterialCropBlock;
import io.github.steviegt6.wheatdsmod.logging.WheatLogger;
import io.github.steviegt6.wheatdsmod.registry.BlockRegistry;
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
    @Inject(method = "randomTick", at = @At("RETURN"))
    public void injectRandomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo info) {
        tryConvertWheat(world, random, pos, state);
    }

    @Inject(method = "grow", at = @At("RETURN"))
    public void injectGrow(ServerWorld world, Random random, BlockPos pos, BlockState state, CallbackInfo info) {
        tryConvertWheat(world, random, pos, state);
    }

    private void tryConvertWheat(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        Block cropBlock = state.getBlock();

        if (cropBlock.getClass() != CropBlock.class)
            return;

        BlockPos blockPos = pos.down(2);
        Block beneathFarmlandBlock = world.getBlockState(blockPos).getBlock();
        Identifier beneathFarmlandBlockIdentifier = Registry.BLOCK.getId(beneathFarmlandBlock);

        if (BlockRegistry.BLOCK_CONVERTERS.containsKey(beneathFarmlandBlockIdentifier)) {
            int age = state.get(CropBlock.AGE).intValue();
            state = BlockRegistry.BLOCK_CONVERTERS.get(beneathFarmlandBlockIdentifier).withAge(age + 1);
            world.setBlockState(pos, state, 2);
        }
    }
}
