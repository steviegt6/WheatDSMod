package io.github.steviegt6.wheatdsmod.mixin;

import com.google.gson.JsonElement;
import io.github.steviegt6.wheatdsmod.items.crops.MaterialCropItem;
import io.github.steviegt6.wheatdsmod.registry.ItemRegistry;
import io.github.steviegt6.wheatdsmod.utils.JsonGenerators;
import io.github.steviegt6.wheatdsmod.utils.WheatIdentifier;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {
    @Inject(method = "apply", at = @At("HEAD"))
    public void interceptApply(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo info) {
        for (MaterialCropItem item : ItemRegistry.REGISTERED_CROPS) {
            String cropName = item.getIdentifierName();
            JsonElement flourRecipeJson = JsonGenerators.createCropsToFlourRecipe(item);
            JsonElement resourceRecipeJson = JsonGenerators.createFlourToResourceRecipe(item);

            map.put(new WheatIdentifier(cropName + "_flour"), flourRecipeJson);
            map.put(new WheatIdentifier(cropName + "_flour_smelt"), resourceRecipeJson);
        }
    }
}
