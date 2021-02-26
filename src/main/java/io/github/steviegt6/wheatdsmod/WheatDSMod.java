package io.github.steviegt6.wheatdsmod;

import io.github.steviegt6.wheatdsmod.logging.WheatLogger;
import net.fabricmc.api.ModInitializer;

public class WheatDSMod implements ModInitializer {
    public static final String ModID = "wheat"; // Our mod's identifier

    @Override
    public void onInitialize() {
        WheatLogger.QuickLogInfo("Initializing wheat...");

        // TODO: Registry calls

        WheatLogger.QuickLogInfo("Initialized wheat!");
    }
}
