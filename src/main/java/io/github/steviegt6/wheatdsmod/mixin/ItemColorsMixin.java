package io.github.steviegt6.wheatdsmod.mixin;

import io.github.steviegt6.wheatdsmod.items.NamedDyeableArmorItem;
import io.github.steviegt6.wheatdsmod.registry.ItemRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.item.DyeableItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin that hooks into ItemColor's create method and gives our Padded Leather armor a proper default color along with allowing the sprite to change color depending on its color NBT data.
 */
@Mixin(ItemColors.class)
@Environment(EnvType.CLIENT)
public class ItemColorsMixin {
    @Inject(
            // Hook into the return of the method, no matter where it returns.
            at = @At("RETURN"),
            method = "create",
            // Set cancellable to true to allow us to modify the return value.
            cancellable = true
    )
    private static void create(BlockColors blockColors, CallbackInfoReturnable<ItemColors> info) {
        ItemColors colors = info.getReturnValue();

        // Register a color for our dyeable Padded Leather armor.
        for (NamedDyeableArmorItem item : ItemRegistry.PADDED_WHEAT_SET) {
            colors.register( (stack, tintIndex) -> tintIndex > 0 ? -1 : ((DyeableItem)stack.getItem()).getColor(stack),
                    item);
        }

        // Return with the new ItemColors instance we modified.
        info.setReturnValue(colors);
    }
}
