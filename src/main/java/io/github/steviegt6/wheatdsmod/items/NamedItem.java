package io.github.steviegt6.wheatdsmod.items;

import net.minecraft.item.Item;

/**
 * Regular Item implementing IItemNameable.
 */
public class NamedItem extends Item implements IItemNameable {
    private final String _itemName;

    public NamedItem(String name, Settings settings) {
        super(settings);
        _itemName = name;
    }

    @Override
    public String ItemName() {
        return _itemName;
    }
}
