package io.github.steviegt6.wheatdsmod.client;

import io.github.steviegt6.wheatdsmod.logging.WheatLogger;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class WheatDSModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WheatLogger.quickLogInfo("Initializing wheat client...");

        // TODO: What goes here?

        WheatLogger.quickLogInfo("Initialized wheat client!");
    }
}
