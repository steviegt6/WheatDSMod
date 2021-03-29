package io.github.steviegt6.wheatdsmod.items.bonemeal;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.item.Item;

public class BoneMealItem extends Item {

    public BoneMealItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        if (!world.isClient) {
            boolean completed = false;
            int decrementAmount = 1;
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() == Blocks.DIRT) {
                world.setBlockState(pos, Blocks.GRASS_BLOCK.getDefaultState());
                completed = true;
            }
            if (completed) {
                context.getStack().decrement(decrementAmount);
                world.syncWorldEvent(2005, pos, 0);
            }
        }
        return super.useOnBlock(context);
    }
}