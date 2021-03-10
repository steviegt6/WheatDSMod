package io.github.steviegt6.wheatdsmod;

import io.github.steviegt6.wheatdsmod.logging.WheatLogger;
import io.github.steviegt6.wheatdsmod.registry.ItemRegistry;
import io.github.steviegt6.wheatdsmod.registry.RegistryManager;
import net.fabricmc.api.ModInitializer;

public class WheatDSMod implements ModInitializer {
    /**
     * The identifier of our mod.
     */
    public static final String MOD_ID = "wheat";

    @Override
    public void onInitialize() {
        WheatLogger.info("Initializing wheat...");

        RegistryManager.load();

        WheatLogger.info("Initialized wheat!");
    }
}
