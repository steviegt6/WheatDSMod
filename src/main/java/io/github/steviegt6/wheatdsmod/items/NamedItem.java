package io.github.steviegt6.wheatdsmod.items;

import io.github.steviegt6.wheatdsmod.IdentifierName;
import net.minecraft.item.Item;

public class NamedItem extends Item implements IdentifierName {
    private final String _itemName;

    public NamedItem(String name, Settings settings) {
        super(settings);
        _itemName = name;
    }

    @Override
    public String getIdentifierName() {
        return _itemName;
    }
}
