package io.github.steviegt6.wheatdsmod.factories;

import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;

/**
 * Shorthand factory for the common "bulk item" trades found in vanilla. You always get 1 emerald in return for these trades, with a fixed price multiplier.
 * Vanilla's usage of "bulk trades" include trading carrots/potatoes in exchange for 1 emerald from farmers. This factory is used to mimic this functionality.
 */
public class BulkTradeFactory extends GeneralTradeFactory {
    public BulkTradeFactory(ItemConvertible buyItem, int buyPrice, int maxUses, int experience) {
        super(buyItem, buyPrice, Items.EMERALD, 1, maxUses, experience, 0.005F);
    }
}