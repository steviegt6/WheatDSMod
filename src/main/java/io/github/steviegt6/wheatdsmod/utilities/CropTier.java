package io.github.steviegt6.wheatdsmod.utilities;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class CropTier {
    private final Item material;
    private final Block conversionBlock;

    public CropTier(Item item, Block block) {
        material = item;
        conversionBlock = block;
    }

    public Item getMaterial() {
        return material;
    }

    public Block getConversionBlock() {
        return conversionBlock;
    }
}
