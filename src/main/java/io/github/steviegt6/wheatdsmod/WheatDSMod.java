package io.github.steviegt6.wheatdsmod;

import io.github.steviegt6.wheatdsmod.logging.WheatLogger;
import io.github.steviegt6.wheatdsmod.registry.RegistryManager;
import net.fabricmc.api.ModInitializer;

public class WheatDSMod implements ModInitializer {
    /**
     * The identifier of our mod.
     */
    public static final String ModID = "wheat";

    @Override
    public void onInitialize() {
        WheatLogger.QuickLogInfo("Initializing wheat...");

        RegistryManager.Load();

        WheatLogger.QuickLogInfo("Initialized wheat!");
    }
}
