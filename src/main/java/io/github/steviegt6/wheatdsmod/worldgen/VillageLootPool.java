package io.github.steviegt6.wheatdsmod.worldgen;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.item.Item;
import net.minecraft.loot.UniformLootTableRange;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import io.github.steviegt6.wheatdsmod.registry.ItemRegistry;

public class VillageLootPool {
    public static void init() {
        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, supplier, setter) -> {
            if (id.getPath().contains("chests") && id.getPath().contains("village")) {
                FabricLootPoolBuilder wheat = FabricLootPoolBuilder.builder()
                        .rolls(UniformLootTableRange.between(0, 5))
                        .withCondition(RandomChanceLootCondition.builder(1.0f).build());

                for (Item item : ItemRegistry.REGISTERED_SEEDS)
                    wheat.with(ItemEntry.builder(item));

                supplier.pool(wheat);
            }
        });
    }
}
