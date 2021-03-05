package io.github.steviegt6.wheatdsmod.utilities;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class CropTier {
    private final Item material;
    private final Block conversionBlock;
    private final float conversionChance;
    private final float boneMealConversionFactor;
    private final float seedDuplicationChance;

    public CropTier(Item item, Block block, float chance) {
        material = item;
        conversionBlock = block;
        conversionChance = chance;
        boneMealConversionFactor = 10;
        seedDuplicationChance = 0.01f;
    }

    public CropTier(Item item, Block block, float chance, float boneMealFactor, float seedDupeChance) {
        material = item;
        conversionBlock = block;
        conversionChance = chance;
        boneMealConversionFactor = boneMealFactor;
        seedDuplicationChance = seedDupeChance;
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

    public float getSeedDuplicationChance() {
        return seedDuplicationChance;
    }
}
