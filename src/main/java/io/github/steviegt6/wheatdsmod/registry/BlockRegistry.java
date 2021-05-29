package io.github.steviegt6.wheatdsmod.registry;

import io.github.steviegt6.wheatdsmod.blocks.MaterialCropBlock;
import io.github.steviegt6.wheatdsmod.items.crops.MaterialCropItem;
import io.github.steviegt6.wheatdsmod.utils.WheatIdentifier;
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

            BLOCK_CONVERTERS.put(Registry.BLOCK.getId(item.getCropTier().getConversionBlock()), block);

            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
        }
    }
}
