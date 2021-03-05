package io.github.steviegt6.wheatdsmod.utilities;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class CropTier {
    private final Item material;
    private final Block conversionBlock;
    private final float conversionChance;
    private final float boneMealConversionFactor;

    public CropTier(Item item, Block block, float chance) {
        material = item;
        conversionBlock = block;
        conversionChance = chance;
        boneMealConversionFactor = 10;
    }

    public CropTier(Item item, Block block, float chance, float boneMealFactor) {
        material = item;
        conversionBlock = block;
        conversionChance = chance;
        boneMealConversionFactor = boneMealFactor;
    }

    public Item getMaterial() {
        return material;
    }

    public Block getConversionBlock() {
        return conversionBlock;
    }

    public float getConversionChance() {
        return conversionChance;
    }

    public float getBoneMealConversionFactor() {
        return boneMealConversionFactor;
    }
}
