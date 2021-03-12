package io.github.steviegt6.wheatdsmod.mixin;

import io.github.steviegt6.wheatdsmod.items.AliasedCompostableBlockItem;
import io.github.steviegt6.wheatdsmod.items.crops.MaterialCropItem;
import io.github.steviegt6.wheatdsmod.registry.ItemRegistry;
import io.github.steviegt6.wheatdsmod.utilities.GuideBookReceiver;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
    @Inject(method = "onPlayerCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;setCount(I)V"), locals = LocalCapture.CAPTURE_FAILSOFT)
    public void injectOnPlayerCollision(PlayerEntity player, CallbackInfo ci, ItemStack itemStack, Item item, int i) {
        GuideBookReceiver bookReceiver = (GuideBookReceiver) player;
        if (bookReceiver.receivedBookAlready())
            return;

        if (item instanceof MaterialCropItem || item instanceof AliasedCompostableBlockItem) {
            player.inventory.offerOrDrop(player.world, new ItemStack(ItemRegistry.GUIDE_BOOK));
            bookReceiver.receiveBook();
        }
    }
}
