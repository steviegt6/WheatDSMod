package io.github.steviegt6.wheatdsmod.utils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class CropTier {
    private final Item material;
    private final Block conversionBlock;
    private final float conversionChance;
    private final float boneMealConversionFactor;
    private final float seedDupeChance;
    private final float growthChance;
    private final boolean beeGrowable;

    private CropTier(Item material, Block conversionBlock, float conversionChance, float boneMealConversionFactor, float seedDupeChance, float growthChance, boolean beeGrowable) {
        this.material = material;
        this.conversionBlock = conversionBlock;
        this.conversionChance = conversionChance;
        this.boneMealConversionFactor = boneMealConversionFactor;
        this.seedDupeChance = seedDupeChance;
        this.growthChance = growthChance;
        this.beeGrowable = beeGrowable;
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

    public float getGrowthChance() {
        return growthChance;
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
        private float growthChance;
        private boolean beeGrowable;

        public Builder(Item materialItem) {
            material = materialItem;
            boneMealConversionFactor = 1f;
            growthChance = 1f;
            beeGrowable = true;
        }

        public CropTier build() {
            return new CropTier(material, conversionBlock, conversionChance, boneMealConversionFactor, seedDupeChance, growthChance, beeGrowable);
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

        public Builder growthChance(float chance) {
            growthChance = chance;
            return this;
        }

        public Builder beeGrowable(boolean growable) {
            beeGrowable = growable;
            return this;
        }
    }
}
