package io.github.steviegt6.wheatdsmod.items;

import net.minecraft.block.Block;
import net.minecraft.item.AliasedBlockItem;

// NOTE: This doesn't need to be in the .crops package since it has
// potentially applicable uses outside of crops.
// I (Stevie) have plans for non-crop items that can be composted!
public class AliasedCompostableBlockItem extends AliasedBlockItem implements Compostable {
    public AliasedCompostableBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public float getLevelIncreaseChance() {
        return 0.3f;
    }
}
