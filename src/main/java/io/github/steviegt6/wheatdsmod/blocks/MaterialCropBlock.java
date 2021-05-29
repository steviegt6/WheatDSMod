package io.github.steviegt6.wheatdsmod.blocks;

import io.github.steviegt6.wheatdsmod.items.crops.MaterialCropItem;
import io.github.steviegt6.wheatdsmod.utils.CropTier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class MaterialCropBlock extends CropBlock {
    private final MaterialCropItem droppedItem;

    public MaterialCropBlock(MaterialCropItem item, Settings settings) {
        super(settings);
        this.droppedItem = item;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getBaseLightLevel(pos, 0) >= 9) {
            int age = this.getAge(state);

            if (age < this.getMaxAge()) {
                float moisture = getAvailableMoisture(this, world, pos);

                if (random.nextInt((int)(25.0F / moisture) + 1) == 0 && random.nextFloat() <= getCropTier().getGrowthChance()) {
                    world.setBlockState(pos, this.withAge(age + 1), 2);
                }
            }
        }

    }

    @Override
    @Environment(EnvType.CLIENT)
    protected ItemConvertible getSeedsItem() {
        return super.getSeedsItem(); // TODO: proper seeds
    }

    public MaterialCropItem getDroppedItem() {
        return droppedItem;
    }

    public CropTier getCropTier() {
        return droppedItem.getCropTier();
    }
}