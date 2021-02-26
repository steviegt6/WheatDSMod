package io.github.steviegt6.wheatdsmod.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WheatLogger {
    public static final Logger Logger = LogManager.getLogger("WheatDSMod");

    public static void QuickLogInfo(String message) {
        Logger.info(message);
    }
}
