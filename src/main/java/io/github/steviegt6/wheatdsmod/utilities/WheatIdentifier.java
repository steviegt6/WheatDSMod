package io.github.steviegt6.wheatdsmod.utilities;

import io.github.steviegt6.wheatdsmod.WheatDSMod;
import net.minecraft.util.Identifier;

/**
 * An identifier identical to the base Identifier but with a pre-defined namespace.
 */
public class WheatIdentifier extends Identifier {
    public WheatIdentifier(String path) {
        super(WheatDSMod.MOD_ID, path);
    }
}
