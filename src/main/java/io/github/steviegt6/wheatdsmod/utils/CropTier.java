package io.github.steviegt6.wheatdsmod.utils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

/**
 * Represents a tier/crop material that can be used to craft the specified material.
 *
 */
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

    /**
     * @return The Item instance the CropTier represents.
     */
    public Item getMaterial() {
        return material;
    }

    /**
     * @return The block required for vanilla wheat to convert into the tier's crop.
     */
    public Block getConversionBlock() {
        return conversionBlock;
    }

    /**
     * @return The chance for vanilla wheat to convert into the tier's crop each time it grows naturally without bonemeal.
     */
    public float getConversionChance() {
        return conversionChance;
    }

    /**
     * @return The reduction in chance for a vanilla wheat to convert into the tier's crop if grown using bonemeal.
     * The resultant chance is getConversionChance() / getBoneMealConversionFactor().
     */
    public float getBoneMealConversionFactor() {
        return boneMealConversionFactor;
    }

    /**
     * @return The chance for an extra crop seed to drop if a fully grown crop block of the tier is broken.
     */
    public float getSeedDupeChance() {
        return seedDupeChance;
    }

    /**
     * @return The chance for the tier's crop to grow when it experiences a random tick.
     */
    public float getGrowthChance() {
        return growthChance;
    }

    /**
     * @return Whether or not bees can pollinate the tier's crop.
     */
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

        /**
         * Sets the block type needed to be 2 blocks beneath vanilla wheat for it to be able to convert into the tier's crop.
         */
        public Builder conversionBlock(Block block) {
            conversionBlock = block;
            return this;
        }

        /**
         * Sets the chance for vanilla wheat to convert to the tier's crop when it experiences a growth.
         * This requires the conversion block of the tier to be 2 blocks beneath the vanilla wheat.
         */
        public Builder conversionChance(float chance) {
            conversionChance = chance;
            return this;
        }

        /**
         * Sets the bonemeal conversion factor. The tier's conversion chance is divided by this to get the chance for the tier's crop to grow when bonemealed.
         * Can be any number that's not zero. Defaults to 1f.
         */
        public Builder boneMealConversionFactor(float factor) {
            boneMealConversionFactor = factor;
            return this;
        }

        /**
         * The chance for the tier's crop to drop an extra seed when broken if fully grown.
         * The value should range from 0f - 1f.
         */
        public Builder seedDupeChance(float chance) {
            seedDupeChance = chance;
            return this;
        }

        /**
         * Sets the chance for the tier's crop to grow when it experiences a random tick.
         * Lower values means the crop grows slower. The value should range from 0f - 1f.
         * Defaults to 1f.
         */
        public Builder growthChance(float chance) {
            growthChance = chance;
            return this;
        }

        /**
         * Sets whether or not bees can pollinate the tier's crop.
         * Defaults to true.
         */
        public Builder beeGrowable(boolean growable) {
            beeGrowable = growable;
            return this;
        }
    }
}
