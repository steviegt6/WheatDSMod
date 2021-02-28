package io.github.steviegt6.wheatdsmod;

import io.github.steviegt6.wheatdsmod.logging.WheatLogger;
import io.github.steviegt6.wheatdsmod.registry.RegistryManager;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;

public class WheatDSMod implements ModInitializer {
    public static final String ModID = "wheat"; // Our mod's identifier

    @Override
    public void onInitialize() {
        WheatLogger.QuickLogInfo("Initializing wheat...");

        RegistryManager.Load();

        WheatLogger.QuickLogInfo("Initialized wheat!");
    }
}
