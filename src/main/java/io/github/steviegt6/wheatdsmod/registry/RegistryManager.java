package io.github.steviegt6.wheatdsmod.registry;

import io.github.steviegt6.wheatdsmod.logging.WheatLogger;

public class RegistryManager {
    public static void Load() {
        WheatLogger.QuickLogInfo("Registering content...");

        ItemRegistry.Register();

        WheatLogger.QuickLogInfo("Registered content!");
    }
}
