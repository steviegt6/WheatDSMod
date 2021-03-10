package io.github.steviegt6.wheatdsmod.registry;

import io.github.steviegt6.wheatdsmod.blocks.MaterialCropBlock;
import io.github.steviegt6.wheatdsmod.items.AliasedCompostableBlockItem;
import io.github.steviegt6.wheatdsmod.items.NamedDyeableArmorItem;
import io.github.steviegt6.wheatdsmod.items.WheatArmorMaterial;
import io.github.steviegt6.wheatdsmod.items.crops.MaterialCropItem;
import io.github.steviegt6.wheatdsmod.logging.WheatLogger;
import io.github.steviegt6.wheatdsmod.utilities.CropTier;
import io.github.steviegt6.wheatdsmod.utilities.ReflectionHelper;
import io.github.steviegt6.wheatdsmod.utilities.WheatIdentifier;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.tag.BlockTags;
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

    public static final Item FLOUR = new Item(new FabricItemSettings().group(ItemGroup.MATERIALS));

    /**
     * An array of CropTiers that represent the different materials this mod will target.
     * Each element in this array will have crops autoregistered for it.
     */
    public static final CropTier[] CROP_TIERS = new CropTier[] {
            new CropTier.Builder(Items.COAL).conversionBlock(Blocks.COAL_BLOCK).conversionChance(0.005f).seedDupeChance(0.1f).build(),
            new CropTier.Builder(Items.IRON_INGOT).conversionBlock(Blocks.IRON_BLOCK).conversionChance(0.001f).seedDupeChance(0.03f).build(),
            new CropTier.Builder(Items.GOLD_INGOT).conversionBlock(Blocks.GOLD_BLOCK).conversionChance(0.0005f).seedDupeChance(0.01f).build(),
            new CropTier.Builder(Items.REDSTONE).conversionBlock(Blocks.REDSTONE_BLOCK).conversionChance(0.0012f).seedDupeChance(0.07f).build(),
            new CropTier.Builder(Items.LAPIS_LAZULI).conversionBlock(Blocks.LAPIS_BLOCK).conversionChance(0.0007f).seedDupeChance(0.015f).build(),
            new CropTier.Builder(Items.DIAMOND).conversionBlock(Blocks.DIAMOND_BLOCK).conversionChance(0.0001f).seedDupeChance(0.005f).build(),
            new CropTier.Builder(Items.NETHERITE_SCRAP).conversionBlock(Blocks.ANCIENT_DEBRIS).conversionChance(0.00001f).seedDupeChance(0.001f).build()
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

        Registry.register(Registry.ITEM, new WheatIdentifier("flour"), FLOUR);

        try {
            ReflectionHelper.modifyInstanceField(Item.class, "foodComponent", Items.BREAD, new FoodComponent.Builder().hunger(7).saturationModifier(0.7f).build());
        } catch (Exception exception) {
            WheatLogger.error("Failed to modify bread foodcomponent: " + exception);
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
            AliasedCompostableBlockItem seedItem = new AliasedCompostableBlockItem(block, new FabricItemSettings().group(ItemGroup.MATERIALS));

            Registry.register(Registry.ITEM, new WheatIdentifier(itemName + "_seeds"), seedItem);
            REGISTERED_SEEDS.add(seedItem);
            registerCompostableItem(seedItem.getLevelIncreaseChance(), seedItem);
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

    /**
     * Registers a flour item for all registered crops.
     */
    public static void registerFlours() {
        for (MaterialCropItem item : REGISTERED_CROPS) {
            String itemName = item.getIdentifierName();

            Registry.register(Registry.ITEM, new WheatIdentifier(itemName + "_flour"), new Item(new FabricItemSettings().group(ItemGroup.MATERIALS)));
        }
    }
}
