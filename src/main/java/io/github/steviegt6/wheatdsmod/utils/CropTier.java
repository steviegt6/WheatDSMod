package io.github.steviegt6.wheatdsmod.utils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class CropTier {
    private final Item material;
    private final Block conversionBlock;
    private final float conversionChance;
    private final float boneMealConversionFactor;
    private final float seedDupeChance;
    private final boolean beeGrowable;

    private CropTier(Item item, Block block, float chance, float boneMealFactor, float seedDuplicationChance, boolean beeGrown) {
        material = item;
        conversionBlock = block;
        conversionChance = chance;
        boneMealConversionFactor = boneMealFactor;
        seedDupeChance = seedDuplicationChance;
        beeGrowable = beeGrown;
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

    public float getSeedDupeChance() {
        return seedDupeChance;
    }

    public boolean isBeeGrowable() {
        return beeGrowable;
    }

    public static class Builder {
        private final Item material;
        private Block conversionBlock;
        private float conversionChance;
        private float boneMealConversionFactor;
        private float seedDupeChance;
        private boolean beeGrowable;

        public Builder(Item materialItem) {
            material = materialItem;
            boneMealConversionFactor = 1f;
            beeGrowable = true;
        }

        public CropTier build() {
            return new CropTier(material, conversionBlock, conversionChance, boneMealConversionFactor, seedDupeChance, beeGrowable);
        }

        public Builder conversionBlock(Block block) {
            conversionBlock = block;
            return this;
        }

        public Builder conversionChance(float chance) {
            conversionChance = chance;
            return this;
        }

        public Builder boneMealConversionFactor(float factor) {
            boneMealConversionFactor = factor;
            return this;
        }

        public Builder seedDupeChance(float chance) {
            seedDupeChance = chance;
            return this;
        }

        public Builder beeGrowable(boolean growable) {
            beeGrowable = growable;
            return this;
        }
    }
}
