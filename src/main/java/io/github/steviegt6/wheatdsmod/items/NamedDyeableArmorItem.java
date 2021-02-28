package io.github.steviegt6.wheatdsmod.items;

import io.github.steviegt6.wheatdsmod.IdentifierName;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.DyeableArmorItem;

public class NamedDyeableArmorItem extends DyeableArmorItem implements IdentifierName {
    private final String _itemName;

    public NamedDyeableArmorItem(String name, ArmorMaterial armorMaterial, EquipmentSlot equipmentSlot, Settings settings) {
        super(armorMaterial, equipmentSlot, settings);
        _itemName = name;
    }

    @Override
    public String getIdentifierName() {
        return _itemName;
    }
}
