package io.github.steviegt6.wheatdsmod.utilities;

import io.github.steviegt6.wheatdsmod.WheatDSMod;
import net.minecraft.util.Identifier;

public class WheatIdentifier extends Identifier {
    public WheatIdentifier(String path) {
        super(WheatDSMod.ModID, path);
    }
}
