package io.github.steviegt6.wheatdsmod.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

public class WheatLogger {
    public static final Logger logger = Logger.getLogger("WheatDSMod");

    public static void QuickLogInfo(String message) {
        logger.log(Level.INFO, message);
    }
}
