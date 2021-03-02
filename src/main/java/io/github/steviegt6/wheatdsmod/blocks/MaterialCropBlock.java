package io.github.steviegt6.wheatdsmod.blocks;

import io.github.steviegt6.wheatdsmod.items.crops.MaterialCropItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;

public class MaterialCropBlock extends CropBlock {
    private MaterialCropItem droppedItem;

    public MaterialCropBlock(MaterialCropItem item, Settings settings) {
        super(settings);
        this.droppedItem = item;
    }

    @Override
    @Environment(EnvType.CLIENT)
    protected ItemConvertible getSeedsItem() {
        return super.getSeedsItem(); // TODO: proper seeds
    }

    public MaterialCropItem getDroppedItem() {
        return droppedItem;
    }
}