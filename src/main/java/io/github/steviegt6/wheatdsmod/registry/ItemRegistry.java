package io.github.steviegt6.wheatdsmod.registry;

import io.github.steviegt6.wheatdsmod.items.NamedDyeableArmorItem;
import io.github.steviegt6.wheatdsmod.items.WheatArmorMaterial;
import io.github.steviegt6.wheatdsmod.utilities.WheatIdentifier;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

/**
 * Registry responsible for registering items.
 */
public class ItemRegistry {
    /**
     * Array of Padded Leather armor items.
     */
    public static final NamedDyeableArmorItem[] PADDED_WHEAT_SET = new NamedDyeableArmorItem[] {
            new NamedDyeableArmorItem("padded_leather_helmet", WheatArmorMaterial.PADDED_LEATHER, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT)),
            new NamedDyeableArmorItem("padded_leather_chestplate", WheatArmorMaterial.PADDED_LEATHER, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT)),
            new NamedDyeableArmorItem("padded_leather_leggings", WheatArmorMaterial.PADDED_LEATHER, EquipmentSlot.LEGS,new FabricItemSettings().group(ItemGroup.COMBAT)),
            new NamedDyeableArmorItem("padded_leather_boots", WheatArmorMaterial.PADDED_LEATHER, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT))
    };

    /**
     * Registers items.
     */
    public static void Register() {
        for (NamedDyeableArmorItem item : PADDED_WHEAT_SET) {
            Registry.register(Registry.ITEM, new WheatIdentifier(item.getIdentifierName()), item);
        }
    }
}
