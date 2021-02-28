package io.github.steviegt6.wheatdsmod.server;

import io.github.steviegt6.wheatdsmod.logging.WheatLogger;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.SERVER)
public class WheatDSModServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        WheatLogger.quickLogInfo("Initializing wheat server...");

        // TODO: What goes here?

        WheatLogger.quickLogInfo("Initialized wheat server!");
    }
}
