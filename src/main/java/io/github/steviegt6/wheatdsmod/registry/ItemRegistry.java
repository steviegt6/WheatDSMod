package io.github.steviegt6.wheatdsmod.registry;

import io.github.steviegt6.wheatdsmod.items.crops.CropType;
import io.github.steviegt6.wheatdsmod.items.crops.MaterialCropItem;
import io.github.steviegt6.wheatdsmod.items.NamedDyeableArmorItem;
import io.github.steviegt6.wheatdsmod.items.WheatArmorMaterial;
import io.github.steviegt6.wheatdsmod.utilities.WheatIdentifier;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
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
     * Array of items/materials that can be crafted from material crops.
     * Used to automatically register crops that target different materials.
     */
    public static final Item[] CROP_TIERS = new Item[] {
            Items.COAL,
            Items.IRON_INGOT,
            Items.GOLD_INGOT,
            Items.REDSTONE,
            Items.LAPIS_LAZULI,
            Items.DIAMOND,
            Items.NETHERITE_SCRAP
    };

    /**
     * Array of crop types. Each allowed material will have all of these as variants.
     */
    public static final CropType[] CROP_TYPES = new CropType[] {
            new CropType("wheat", 3),
            new CropType("barley", 6),
            new CropType("rye", 9)
    };

    /**
     * Registers items.
     */
    public static void register() {
        for (NamedDyeableArmorItem item : PADDED_WHEAT_SET) {
            Registry.register(Registry.ITEM, new WheatIdentifier(item.getIdentifierName()), item);
        }

        for (Item material : CROP_TIERS) {
            String materialName = Registry.ITEM.getId(material).getPath();

            for (CropType cropType : CROP_TYPES) {
                MaterialCropItem item = new MaterialCropItem(material, materialName + "_" + cropType.getName(), new FabricItemSettings().group(ItemGroup.MATERIALS));
                Registry.register(Registry.ITEM, new WheatIdentifier(item.getIdentifierName()), item);
            }
        }
    }
}
