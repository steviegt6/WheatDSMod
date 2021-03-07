package io.github.steviegt6.wheatdsmod.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class with an instance of a log4j logger & helpful logging methods.
 */
public class WheatLogger {
    /**
     * Instance of a log4j logger for our mod.
     */
    public static final Logger LOGGER = LogManager.getLogger("WheatDSMod");

    /**
     * Quickly calls our Logger's info method without the need to reference our instance.
     * @param message The message to log.
     */
    public static void info(Object message) {
        LOGGER.info(message);
    }

    /**
     * Quickly calls our Logger's warn method without the need to reference our instance.
     * @param message
     */
    public static void error(Object message) {
        LOGGER.error(message);
    }
}
