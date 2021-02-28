package io.github.steviegt6.wheatdsmod.items;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.DyeableArmorItem;

public class NamedDyeableArmorItem extends DyeableArmorItem implements IItemNameable {
    private final String _itemName;

    public NamedDyeableArmorItem(String name, ArmorMaterial armorMaterial, EquipmentSlot equipmentSlot, Settings settings) {
        super(armorMaterial, equipmentSlot, settings);
        _itemName = name;
    }

    @Override
    public String ItemName() {
        return _itemName;
    }
}
