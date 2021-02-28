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

@Mixin(ItemColors.class)
@Environment(EnvType.CLIENT)
public class ItemColorsMixin {
    @Inject(
            at = @At("RETURN"),
            method = "create",
            cancellable = true
    )
    private static void create(BlockColors blockColors, CallbackInfoReturnable<ItemColors> info) {
        ItemColors colors = info.getReturnValue();

        for (NamedDyeableArmorItem item : ItemRegistry.PADDED_WHEAT_SET) {
            colors.register( (stack, tintIndex) -> tintIndex > 0 ? -1 : ((DyeableItem)stack.getItem()).getColor(stack),
                    item);
        }

        info.setReturnValue(colors);
    }
}
