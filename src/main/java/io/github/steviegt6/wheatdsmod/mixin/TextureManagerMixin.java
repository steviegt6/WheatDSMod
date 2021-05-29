package io.github.steviegt6.wheatdsmod.mixin;

import io.github.steviegt6.wheatdsmod.utils.WheatIdentifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.MissingSprite;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(TextureManager.class)
public class TextureManagerMixin {
    @Inject(
            at = @At(value = "RETURN"),
            method = "method_24303",
            cancellable = true
    )
    private void replaceMissingSpriteSometimes(Identifier identifier, AbstractTexture abstractTexture, CallbackInfoReturnable<AbstractTexture> ci) {
        if (ci.getReturnValue() == MissingSprite.getMissingSpriteTexture()) {
            TextureManager glorifiedThis = MinecraftClient.getInstance().getTextureManager();

            if (identifier.getPath().endsWith("_wheat_seeds")) {
                ci.setReturnValue(glorifiedThis.getTexture(new WheatIdentifier("null_wheat_seeds")));
            }

            if (identifier.getPath().endsWith("_wheat_flour")) {
                ci.setReturnValue(glorifiedThis.getTexture(new WheatIdentifier("null_wheat_flour")));
            }

            if (identifier.getPath().endsWith("_wheat")) {
                ci.setReturnValue(glorifiedThis.getTexture(new WheatIdentifier("null_wheat")));
            }
        }
    }
}
