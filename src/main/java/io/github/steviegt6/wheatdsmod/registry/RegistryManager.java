package io.github.steviegt6.wheatdsmod.registry;

import io.github.steviegt6.wheatdsmod.logging.WheatLogger;
import net.minecraft.entity.ai.brain.task.FarmerWorkTask;
import net.minecraft.entity.ai.brain.task.GatherItemsVillagerTask;
import net.minecraft.item.Item;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * Helper class responsible for calling registry Load methods.
 */
public class RegistryManager {
    /**
     * Load registries.
     */
    public static void load() {
        WheatLogger.quickLogInfo("Registering content...");

        ItemRegistry.registerNonCrops();
        ItemRegistry.registerCrops();
        BlockRegistry.registerCropBlocks();
        ItemRegistry.registerCropSeeds();
        RecipeRegistry.register();

        try {
            // https://stackoverflow.com/questions/3301635/change-private-static-final-field-using-java-reflection
            // Jesus Christ, what a mess.
            Field compostableField = FarmerWorkTask.class.getDeclaredField("COMPOSTABLES");
            compostableField.setAccessible(true);

            Field compostableModifiers = Field.class.getDeclaredField("modifiers");
            compostableModifiers.setAccessible(true);
            compostableModifiers.setInt(compostableField, compostableField.getModifiers() & ~Modifier.FINAL);

            List<Item> compostables = (List<Item>) compostableField.get(null);
            if (compostables != null) {
                compostables.addAll(ItemRegistry.REGISTERED_SEEDS);
                compostables.addAll(ItemRegistry.REGISTERED_CROPS);
                compostableField.set(null, compostables);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        WheatLogger.quickLogInfo("Registered content!");
    }
}
