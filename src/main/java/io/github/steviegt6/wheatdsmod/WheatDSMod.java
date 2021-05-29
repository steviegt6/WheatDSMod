package io.github.steviegt6.wheatdsmod;

import io.github.steviegt6.wheatdsmod.logging.WheatLogger;
import io.github.steviegt6.wheatdsmod.registry.ItemRegistry;
import io.github.steviegt6.wheatdsmod.registry.RegistryManager;
import io.github.steviegt6.wheatdsmod.utils.WheatIdentifier;
import io.github.steviegt6.wheatdsmod.worldgen.VillageLootPool;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class WheatDSMod implements ModInitializer {
    /**
     * The identifier of our mod.
     */
    public static final String MOD_ID = "wheat";

    public static ItemGroup WheatTab;

    @Override
    public void onInitialize() {
        WheatLogger.info("Initializing wheat...");

        RegistryManager.load();

        WheatTab = FabricItemGroupBuilder.create(new WheatIdentifier("wheat_tab"))
                .icon(() -> new ItemStack(ItemRegistry.FLOUR))
                .appendItems(stack -> {
                    for (Item item : ItemRegistry.REGISTERED_ITEMS)
                        stack.add(new ItemStack(item));
                })
                .build();

        VillageLootPool.init();

        WheatLogger.info("Initialized wheat!");
    }

    public static Boolean getPatchouliLoaded() {
        return FabricLoader.getInstance().isModLoaded("patchouli");
    }
}
