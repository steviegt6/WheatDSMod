package io.github.steviegt6.wheatdsmod.registry;

import io.github.steviegt6.wheatdsmod.blocks.MaterialCropBlock;
import io.github.steviegt6.wheatdsmod.items.AliasedCompostableBlockItem;
import io.github.steviegt6.wheatdsmod.items.crops.MaterialCropItem;
import io.github.steviegt6.wheatdsmod.items.NamedDyeableArmorItem;
import io.github.steviegt6.wheatdsmod.items.WheatArmorMaterial;
import io.github.steviegt6.wheatdsmod.utilities.CropTier;
import io.github.steviegt6.wheatdsmod.utilities.WheatIdentifier;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

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
     * An array of CropTiers that represent the different materials this mod will target.
     * Each element in this array will have crops autoregistered for it.
     */
    public static final CropTier[] CROP_TIERS = new CropTier[] {
            new CropTier(Items.COAL, Blocks.COAL_BLOCK),
            new CropTier(Items.IRON_INGOT, Blocks.IRON_BLOCK),
            new CropTier(Items.GOLD_INGOT, Blocks.GOLD_BLOCK),
            new CropTier(Items.REDSTONE, Blocks.REDSTONE_BLOCK),
            new CropTier(Items.LAPIS_LAZULI, Blocks.LAPIS_BLOCK),
            new CropTier(Items.DIAMOND, Blocks.DIAMOND_BLOCK),
            new CropTier(Items.NETHERITE_SCRAP, Blocks.NETHERITE_BLOCK)
    };

    /**
     * A list that gets populated with all registered crop items. Used in later content registrations.
     */
    public static final List<MaterialCropItem> REGISTERED_CROPS = new ArrayList<>();

    /**
     * A list that gets populated with all registered crop seeds. Used elsewhere, such as mixins.
     */
    public static final List<AliasedCompostableBlockItem> REGISTERED_SEEDS = new ArrayList<>();

    /**
     * Registers non-crop items in the mod.
     */
    public static void registerNonCrops() {
        for (NamedDyeableArmorItem item : PADDED_WHEAT_SET) {
            Registry.register(Registry.ITEM, new WheatIdentifier(item.getIdentifierName()), item);
        }
    }

    /**
     * Registers all wheat crops that are allowed.
     */
    public static void registerCrops() {
        for (CropTier tier : CROP_TIERS) {
            String materialName = Registry.ITEM.getId(tier.getMaterial()).getPath();
            MaterialCropItem item = new MaterialCropItem(tier, materialName + "_wheat", new FabricItemSettings().group(ItemGroup.MATERIALS));

            Registry.register(Registry.ITEM, new WheatIdentifier(item.getIdentifierName()), item);
            REGISTERED_CROPS.add(item);

            registerCompostableItem(item.getLevelIncreaseChance(), item);
        }
    }

    /**
     * Automatically registers seeds for all the registered wheat crops.
     */
    public static void registerCropSeeds() {
        for (MaterialCropItem item : REGISTERED_CROPS) {
            String itemName = item.getIdentifierName();
            MaterialCropBlock block = BlockRegistry.REGISTERED_CROP_BLOCKS.get(itemName);
            AliasedCompostableBlockItem newItem = new AliasedCompostableBlockItem(block, new FabricItemSettings().group(ItemGroup.MATERIALS));

            Registry.register(Registry.ITEM, new WheatIdentifier(itemName + "_seeds"), newItem);
            REGISTERED_SEEDS.add(newItem);
            registerCompostableItem(newItem.getLevelIncreaseChance(), newItem);
        }
    }

    /**
     * Registers an item's compostibility.
     * @param levelIncreaseChance The chance that the item input will increase a composter block's level.
     * @param item The item we want to registry to.
     */
    public static void registerCompostableItem(float levelIncreaseChance, ItemConvertible item) {
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(item.asItem(), levelIncreaseChance);
    }
}
