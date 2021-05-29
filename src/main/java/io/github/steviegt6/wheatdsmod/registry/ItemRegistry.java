package io.github.steviegt6.wheatdsmod.registry;

import io.github.steviegt6.wheatdsmod.WheatDSMod;
import io.github.steviegt6.wheatdsmod.blocks.MaterialCropBlock;
import io.github.steviegt6.wheatdsmod.items.AliasedCompostableBlockItem;
import io.github.steviegt6.wheatdsmod.items.NamedDyeableArmorItem;
import io.github.steviegt6.wheatdsmod.items.WheatArmorMaterial;
import io.github.steviegt6.wheatdsmod.items.crops.MaterialCropItem;
import io.github.steviegt6.wheatdsmod.logging.WheatLogger;
import io.github.steviegt6.wheatdsmod.utils.CropTier;
import io.github.steviegt6.wheatdsmod.utils.ReflectionHelper;
import io.github.steviegt6.wheatdsmod.utils.WheatIdentifier;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import vazkii.patchouli.api.PatchouliAPI;

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
            new NamedDyeableArmorItem("padded_helmet", WheatArmorMaterial.PADDED_LEATHER, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT)),
            new NamedDyeableArmorItem("padded_chestplate", WheatArmorMaterial.PADDED_LEATHER, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT)),
            new NamedDyeableArmorItem("padded_leggings", WheatArmorMaterial.PADDED_LEATHER, EquipmentSlot.LEGS,new FabricItemSettings().group(ItemGroup.COMBAT)),
            new NamedDyeableArmorItem("padded_boots", WheatArmorMaterial.PADDED_LEATHER, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT))
    };

    public static final Item FLOUR = new Item(new FabricItemSettings().group(ItemGroup.MATERIALS));

    public static final Item GUIDE_BOOK = new Item(new FabricItemSettings().group(ItemGroup.MATERIALS).maxCount(1)) {
        @Override
        public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
            ItemStack stack = user.getStackInHand(hand);

            if (user instanceof ServerPlayerEntity && WheatDSMod.getPatchouliLoaded()) {
                ServerPlayerEntity player = (ServerPlayerEntity) user;
                PatchouliAPI.get().openBookGUI(player, Registry.ITEM.getId(this));
            }

            return new TypedActionResult<>(ActionResult.SUCCESS, stack);
        }
    };

    /**
     * An array of CropTiers that represent the different materials this mod will target.
     * Each element in this array will have crops auto-registered for it.
     */
    public static final CropTier[] CROP_TIERS = new CropTier[] {
            new CropTier.Builder(Items.COAL).conversionBlock(Blocks.COAL_BLOCK).conversionChance(0.005f).seedDupeChance(0.1f).growthChance(0.95f).build(),
            new CropTier.Builder(Items.IRON_INGOT).conversionBlock(Blocks.IRON_BLOCK).conversionChance(0.001f).seedDupeChance(0.03f).growthChance(0.9f).build(),
            new CropTier.Builder(Items.GOLD_INGOT).conversionBlock(Blocks.GOLD_BLOCK).conversionChance(0.0005f).seedDupeChance(0.01f).growthChance(0.8f).build(),
            new CropTier.Builder(Items.REDSTONE).conversionBlock(Blocks.REDSTONE_BLOCK).conversionChance(0.0012f).seedDupeChance(0.07f).growthChance(0.8f).build(),
            new CropTier.Builder(Items.LAPIS_LAZULI).conversionBlock(Blocks.LAPIS_BLOCK).conversionChance(0.0007f).seedDupeChance(0.015f).growthChance(0.8f).build(),
            new CropTier.Builder(Items.DIAMOND).conversionBlock(Blocks.DIAMOND_BLOCK).conversionChance(0.0001f).seedDupeChance(0.005f).growthChance(0.6f).build(),
            new CropTier.Builder(Items.NETHERITE_SCRAP).conversionBlock(Blocks.ANCIENT_DEBRIS).conversionChance(0.00001f).seedDupeChance(0.001f).growthChance(0.5f).build()
    };

    /**
     * A list that gets populated with every single item the mod has.
     */
    public static final List<Item> REGISTERED_ITEMS = new ArrayList<>();

    /**
     * A list that gets populated with all registered crop items. Used in later content registrations.
     */
    public static final List<MaterialCropItem> REGISTERED_CROPS = new ArrayList<>();

    /**
     * A list that gets populated with all registered crop seeds. Used elsewhere, such as mixins.
     */
    public static final List<AliasedCompostableBlockItem> REGISTERED_SEEDS = new ArrayList<>();

    private static void registerItem(Item item, String identifier) {
        Registry.register(Registry.ITEM, new WheatIdentifier(identifier), item);
        REGISTERED_ITEMS.add(item);
    }

    /**
     * Registers non-crop items in the mod.
     */
    public static void registerNonCrops() {
        for (NamedDyeableArmorItem item : PADDED_WHEAT_SET) {
            registerItem(item, item.getIdentifierName());
        }

        if (FabricLoader.getInstance().isModLoaded("patchouli"))
            registerItem(GUIDE_BOOK, "guide_book");

        registerItem(FLOUR, "flour");

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

            registerItem(item, item.getIdentifierName());
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

            registerItem(seedItem, itemName + "_seeds");
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
            Item flourItem = new Item(new FabricItemSettings().group(ItemGroup.MATERIALS));

            registerItem(flourItem, itemName + "_flour");
        }
    }
}
