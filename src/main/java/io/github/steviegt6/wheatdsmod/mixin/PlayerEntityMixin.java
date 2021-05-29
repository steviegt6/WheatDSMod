package io.github.steviegt6.wheatdsmod.mixin;

import io.github.steviegt6.wheatdsmod.utils.GuideBookReceiver;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements GuideBookReceiver {
    private boolean receivedBook;

    @Override
    public void receiveBook() {
        receivedBook = true;
    }

    @Override
    public boolean receivedBookAlready() {
        return receivedBook;
    }

    @Inject(method = "writeCustomDataToTag", at = @At("TAIL"))
    public void injectWriteCustomDataToTag(CompoundTag tag, CallbackInfo ci) {
        tag.putBoolean("GotWheatGuide", receivedBook);
    }

    @Inject(method = "readCustomDataFromTag", at = @At("TAIL"))
    public void injectReadCustomDataFromTag(CompoundTag tag, CallbackInfo ci) {
        receivedBook = tag.getBoolean("GotWheatGuide");
    }
}
