package io.github.steviegt6.wheatdsmod.mixin;

import com.google.gson.JsonElement;
import io.github.steviegt6.wheatdsmod.blocks.MaterialCropBlock;
import io.github.steviegt6.wheatdsmod.logging.WheatLogger;
import io.github.steviegt6.wheatdsmod.registry.BlockRegistry;
import io.github.steviegt6.wheatdsmod.utilities.WheatIdentifier;
import net.minecraft.loot.LootManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(LootManager.class)
public class LootManagerMixin {
    @Inject(method = "apply", at = @At("HEAD"))
    public void interceptApply(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo info) {
        for (MaterialCropBlock block : BlockRegistry.REGISTERED_CROP_BLOCKS.values()) {
            JsonElement lootJson = BlockRegistry.createCropBlockLootJson(block);
            WheatLogger.quickLogInfo(lootJson.toString());
            map.put(new WheatIdentifier("blocks/" + block.getDroppedItem().getIdentifierName() + "_crop"), lootJson);
        }
    }
}
