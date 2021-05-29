package io.github.steviegt6.wheatdsmod.mixin;

import io.github.steviegt6.wheatdsmod.registry.ItemRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnimalEntity.class)
public abstract class AnimalEntityMixin {
    @Inject(
            at = @At(value = "RETURN"),
            method = "isBreedingItem",
            cancellable = true
    )
    public void isBreedingItem(ItemStack stack, CallbackInfoReturnable<Boolean> ci) {
        if (!ci.getReturnValue() && ItemRegistry.REGISTERED_CROPS.contains(stack.getItem()))
            ci.setReturnValue(true);
    }
}
