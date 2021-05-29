package io.github.steviegt6.wheatdsmod.mixin;

import io.github.steviegt6.wheatdsmod.registry.ItemRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CowEntity.class)
public abstract class CowEntityMixin extends AnimalEntity {
    public CowEntityMixin(EntityType<? extends CowEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            at = @At(value = "TAIL"),
            // <init> is the constructor of classes.
            method = "initGoals"
    )
    protected void initGoals(CallbackInfo ci) {
        for (Item item : ItemRegistry.REGISTERED_CROPS) {
            // TODO: lmfao ofItems so like don't use a loop to add our items??
            goalSelector.add(3, new TemptGoal(this, 1.25D, Ingredient.ofItems(item), false));
        }
    }
}
