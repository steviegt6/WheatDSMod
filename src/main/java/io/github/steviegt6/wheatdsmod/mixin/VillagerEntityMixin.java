package io.github.steviegt6.wheatdsmod.mixin;

import com.google.common.collect.ImmutableSet;
import io.github.steviegt6.wheatdsmod.items.AliasedCompostableBlockItem;
import io.github.steviegt6.wheatdsmod.registry.ItemRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.InteractionObserver;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity implements InteractionObserver, VillagerDataContainer {
    public VillagerEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            at = @At("RETURN"),
            method = "hasSeedToPlant",
            cancellable = true
    )
    public void hasSeedToPlant(CallbackInfoReturnable<Boolean> info) {
        for (AliasedCompostableBlockItem seed : ItemRegistry.REGISTERED_SEEDS)
            if (getInventory().containsAny(ImmutableSet.of(seed)))
                info.setReturnValue(true);
    }
}
