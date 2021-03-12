package io.github.steviegt6.wheatdsmod;

import io.github.steviegt6.wheatdsmod.logging.WheatLogger;
import io.github.steviegt6.wheatdsmod.registry.ItemRegistry;
import io.github.steviegt6.wheatdsmod.registry.RegistryManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import vazkii.patchouli.common.base.Patchouli;

public class WheatDSMod implements ModInitializer {
    /**
     * The identifier of our mod.
     */
    public static final String MOD_ID = "wheat";

    public static boolean PatchouliLoaded;

    @Override
    public void onInitialize() {
        WheatLogger.info("Initializing wheat...");

        PatchouliLoaded = FabricLoader.getInstance().isModLoaded("patchouli");

        RegistryManager.load();

        WheatLogger.info("Initialized wheat!");
    }
}
