package io.github.steviegt6.wheatdsmod.mixin;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import io.github.steviegt6.wheatdsmod.items.WheatArmorMaterial;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

/**
 * Mixin that makes it so our Padded Leather armor actually has knockback resistance.
 */
@Mixin(ArmorItem.class)
public class ArmorItemMixin {
    @Shadow
    @Final
    private static UUID[] MODIFIERS;

    @Mutable
    @Shadow
    @Final
    private Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;
    @Shadow @Final protected float knockbackResistance;

    @Inject(
            at = @At(value = "RETURN"),
            // <init> is the constructor of classes.
            method = "<init>"
    )
    private void constructor(ArmorMaterial material, EquipmentSlot slot, Item.Settings settings, CallbackInfo info) {
        // Check if the material is an instance of our custom modded ArmorMaterial
        // If it is, check if our list of WheatArmorMaterials that change armor toughness contains the material
        if (material instanceof WheatArmorMaterial && WheatArmorMaterial.ARMOR_TOUGHNESS_MATERIALS.contains(material)) {
            // Essentially "copy" attributeModifiers, add the armor toughness, and set attributeModifiers to our "copy"
            ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
            attributeModifiers.forEach(builder::put);
            builder.put(
                    EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE,
                    new EntityAttributeModifier(
                            MODIFIERS[slot.getEntitySlotId()],
                            "Armor knockback resistance",
                            knockbackResistance,
                            EntityAttributeModifier.Operation.ADDITION
                    )
            );
            attributeModifiers = builder.build();
        }
    }
}
