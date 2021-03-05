package io.github.steviegt6.wheatdsmod.items.crops;

import io.github.steviegt6.wheatdsmod.items.Compostable;
import io.github.steviegt6.wheatdsmod.items.NamedItem;
import io.github.steviegt6.wheatdsmod.utilities.CropTier;

public class MaterialCropItem extends NamedItem implements Compostable {
    private final CropTier tier;

    public MaterialCropItem(CropTier tier, String name, Settings settings) {
        super(name, settings);
        this.tier = tier;
    }

    public CropTier getTier() {
        return tier;
    }

    @Override
    public float getLevelIncreaseChance() {
        return 0.65f;
    }
}