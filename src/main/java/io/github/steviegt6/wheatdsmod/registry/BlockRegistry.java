package io.github.steviegt6.wheatdsmod.registry;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.steviegt6.wheatdsmod.blocks.MaterialCropBlock;
import io.github.steviegt6.wheatdsmod.items.crops.MaterialCropItem;
import io.github.steviegt6.wheatdsmod.utilities.WheatIdentifier;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class BlockRegistry {
    /**
     * A hashmap that gets populated with all crop blocks that grow wheat crops.
     * The keys are the non-namespaced identifier name of the crop they drop.
     */
    public static final Map<String, MaterialCropBlock> REGISTERED_CROP_BLOCKS = new HashMap<>();

    /**
     * A hashmap that acts as a mapping between blocks (specifically their identifiers) and to the type of wheat they may convert vanilla wheat into.
     * This is used during the wheat conversion process (for example, a coal block has a chance of converting vanilla wheat into coal wheat).
     * Gets populated during crop item registering.
     */
    public static final Map<Identifier, MaterialCropBlock> BLOCK_CONVERTERS = new HashMap<>();

    /**
     * Automatically registers all crop blocks for different types of wheat.
     */
    public static void registerCropBlocks() {
        for (MaterialCropItem item : ItemRegistry.REGISTERED_CROPS) {
            String itemName = item.getIdentifierName();

            MaterialCropBlock block = new MaterialCropBlock(item, AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().nonOpaque().sounds(BlockSoundGroup.CROP));
            Registry.register(Registry.BLOCK, new WheatIdentifier(itemName + "_crop"), block);
            REGISTERED_CROP_BLOCKS.put(itemName, block);

            BLOCK_CONVERTERS.put(Registry.BLOCK.getId(item.getTier().getConversionBlock()), block);

            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
        }
    }

    public static JsonObject createCropBlockLootJson(MaterialCropBlock block) {
        String cropName = new WheatIdentifier(block.getDroppedItem().getIdentifierName()).toString();
        String cropSeedsName = cropName + "_seeds";
        String cropBlockName = cropName + "_crop";

        JsonObject json = new JsonObject();
        json.addProperty("type", "minecraft:block");

        JsonArray pools = new JsonArray();
        json.add("pools", pools);

        JsonObject primaryPool = new JsonObject();
        pools.add(primaryPool);

        primaryPool.addProperty("rolls", 1.0);

        JsonArray primaryPoolEntries = new JsonArray();
        primaryPool.add("entries", primaryPoolEntries);

        JsonObject primaryPoolEntry = new JsonObject();
        primaryPoolEntries.add(primaryPoolEntry);

        primaryPoolEntry.addProperty("type", "minecraft:alternatives");

        JsonArray primaryPoolEntryChildren = new JsonArray();
        primaryPoolEntry.add("children", primaryPoolEntryChildren);

        JsonObject firstChild = new JsonObject();
        primaryPoolEntryChildren.add(firstChild);

        firstChild.addProperty("type", "minecraft:item");

        JsonArray firstChildConditions = new JsonArray();
        firstChild.add("conditions", firstChildConditions);

        JsonObject firstCondition = new JsonObject();
        firstChildConditions.add(firstCondition);

        firstCondition.addProperty("condition", "minecraft:block_state_property");
        firstCondition.addProperty("block", cropBlockName);

        firstChild.addProperty("name", cropName);

        JsonObject firstConditionProperties = new JsonObject();
        firstCondition.add("properties", firstConditionProperties);

        firstConditionProperties.addProperty("age", "7");

        JsonObject secondChild = new JsonObject();
        primaryPoolEntryChildren.add(secondChild);

        secondChild.addProperty("type", "minecraft:item");
        secondChild.addProperty("name", cropSeedsName);

        JsonObject secondaryPool = new JsonObject();
        pools.add(secondaryPool);

        secondaryPool.addProperty("rolls", 1.0);

        JsonArray secondaryPoolEntries = new JsonArray();
        secondaryPool.add("entries", secondaryPoolEntries);

        JsonObject secondaryPoolEntry = new JsonObject();
        secondaryPoolEntries.add(secondaryPoolEntry);

        secondaryPoolEntry.addProperty("type", "minecraft:item");
        secondaryPoolEntry.addProperty("name", cropSeedsName);

        JsonArray secondaryPoolConditions = new JsonArray();
        secondaryPool.add("conditions", secondaryPoolConditions);

        JsonObject secondaryPoolCondition = new JsonObject();
        secondaryPoolConditions.add(secondaryPoolCondition);

        secondaryPoolCondition.addProperty("condition", "minecraft:block_state_property");
        secondaryPoolCondition.addProperty("block", cropBlockName);

        JsonObject secondaryConditionProperties = new JsonObject();
        secondaryPoolCondition.add("properties", secondaryConditionProperties);

        secondaryConditionProperties.addProperty("age", "7");

        JsonObject thirdPool = new JsonObject();
        pools.add(thirdPool);

        thirdPool.addProperty("rolls", 1.0);

        JsonArray thirdPoolEntries = new JsonArray();
        thirdPool.add("entries", thirdPoolEntries);

        JsonObject thirdPoolEntry = new JsonObject();
        thirdPoolEntries.add(thirdPoolEntry);

        thirdPoolEntry.addProperty("type", "minecraft:item");
        thirdPoolEntry.addProperty("name", cropSeedsName);

        JsonArray thirdPoolConditions = new JsonArray();
        thirdPool.add("conditions", thirdPoolConditions);

        JsonObject thirdPoolPrimaryCondition = new JsonObject();
        thirdPoolConditions.add(thirdPoolPrimaryCondition);

        thirdPoolPrimaryCondition.addProperty("condition", "minecraft:random_chance");
        thirdPoolPrimaryCondition.addProperty("chance", block.getDroppedItem().getTier().getSeedDuplicationChance());

        JsonObject thirdPoolAgeCondition = new JsonObject();
        thirdPoolConditions.add(thirdPoolAgeCondition);

        thirdPoolAgeCondition.addProperty("condition", "minecraft:block_state_property");
        thirdPoolAgeCondition.addProperty("block", cropBlockName);

        JsonObject thirdPoolAgeConditionProperties = new JsonObject();
        thirdPoolAgeCondition.add("properties", thirdPoolAgeConditionProperties);

        thirdPoolAgeConditionProperties.addProperty("age", "7");

        JsonArray functions = new JsonArray();
        json.add("functions", functions);

        JsonObject function = new JsonObject();
        functions.add(function);

        function.addProperty("function", "minecraft:explosion_decay");

        return json;
    }
}
