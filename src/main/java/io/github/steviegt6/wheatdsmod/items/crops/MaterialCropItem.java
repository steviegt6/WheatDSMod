package io.github.steviegt6.wheatdsmod.items.crops;

import io.github.steviegt6.wheatdsmod.items.Compostable;
import io.github.steviegt6.wheatdsmod.items.NamedItem;
import net.minecraft.item.Item;

public class MaterialCropItem extends NamedItem implements Compostable {
    private final Item material;

    public MaterialCropItem(Item material, String name, Settings settings) {
        super(name, settings);
        this.material = material;
    }

    public Item getMaterial() {
        return material;
    }

    @Override
    public float getLevelIncreaseChance() {
        return 0.65f;
    }
}