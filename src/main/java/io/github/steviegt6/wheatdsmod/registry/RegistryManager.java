package io.github.steviegt6.wheatdsmod.registry;

import io.github.steviegt6.wheatdsmod.logging.WheatLogger;

/**
 * Helper class responsible for calling registry Load methods.
 */
public class RegistryManager {
    /**
     * Load registries.
     */
    public static void Load() {
        WheatLogger.QuickLogInfo("Registering content...");

        ItemRegistry.Register();

        WheatLogger.QuickLogInfo("Registered content!");
    }
}
