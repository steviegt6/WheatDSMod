package io.github.steviegt6.wheatdsmod.registry;

import com.google.common.collect.ImmutableSet;
import io.github.steviegt6.wheatdsmod.logging.WheatLogger;
import io.github.steviegt6.wheatdsmod.utils.ReflectionHelper;
import net.minecraft.entity.ai.brain.task.FarmerWorkTask;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Item;
import net.minecraft.village.VillagerProfession;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Helper class responsible for calling registry Load methods.
 */
public class RegistryManager {
    /**
     * Load registries.
     */
    public static void load() {
        WheatLogger.info("Registering content...");

        ItemRegistry.registerNonCrops();
        ItemRegistry.registerCrops();
        ItemRegistry.registerFlours();
        BlockRegistry.registerCropBlocks();
        ItemRegistry.registerCropSeeds();
        RecipeRegistry.register();
        MerchantRegistry.registerTrades();

        try {
            Field compostableField = ReflectionHelper.makeModifiable(FarmerWorkTask.class.getDeclaredField("COMPOSTABLES"));
            List<Item> compostables = (List<Item>) compostableField.get(null);

            if (compostables != null) {
                Set<Item> cloneList = new HashSet<>(compostables);
                cloneList.addAll(ItemRegistry.REGISTERED_SEEDS);
                // Don't compost the crops. Consistency.
                // REMOVED: cloneList.addAll(ItemRegistry.REGISTERED_CROPS);
                compostables = new ArrayList<>(cloneList);
                compostableField.set(null, compostables);
            }

            Field gatherableItemsVEField = ReflectionHelper.makeModifiable(VillagerEntity.class.getDeclaredField("GATHERABLE_ITEMS"));
            Field gatherableItemsFarmerVPField = ReflectionHelper.makeModifiable(VillagerProfession.class.getDeclaredField("gatherableItems"));
            Set<Item> gatherableItemsVE = (Set<Item>) gatherableItemsVEField.get(null);
            ImmutableSet<Item> gatherableItemsFarmerVP = (ImmutableSet<Item>) gatherableItemsFarmerVPField.get(VillagerProfession.FARMER);

            if (gatherableItemsVE != null) {
                Set<Item> cloneList = new HashSet<>(gatherableItemsVE);
                cloneList.addAll(ItemRegistry.REGISTERED_SEEDS);
                cloneList.addAll(ItemRegistry.REGISTERED_CROPS);
                gatherableItemsVE = new HashSet<>(cloneList);
                gatherableItemsVEField.set(null, gatherableItemsVE);
            }

            if (gatherableItemsFarmerVP != null) {
                Set<Item> cloneSet = new HashSet<>(gatherableItemsFarmerVP);
                cloneSet.addAll(ItemRegistry.REGISTERED_SEEDS);
                cloneSet.addAll(ItemRegistry.REGISTERED_CROPS);
                gatherableItemsFarmerVP = ImmutableSet.copyOf(cloneSet);
                gatherableItemsFarmerVPField.set(VillagerProfession.FARMER, gatherableItemsFarmerVP);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        WheatLogger.info("Registered content!");
    }
}
