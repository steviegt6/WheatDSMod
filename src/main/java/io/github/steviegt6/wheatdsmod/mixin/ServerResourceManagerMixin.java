package io.github.steviegt6.wheatdsmod.mixin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import io.github.steviegt6.wheatdsmod.blocks.MaterialCropBlock;
import io.github.steviegt6.wheatdsmod.logging.WheatLogger;
import io.github.steviegt6.wheatdsmod.registry.BlockRegistry;
import io.github.steviegt6.wheatdsmod.utilities.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.SetTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.stream.Collectors;

@Mixin(ServerResourceManager.class)
public class ServerResourceManagerMixin {
    @Inject(method = "loadRegistryTags", at = @At("TAIL"))
    private void injectReload(CallbackInfo ci) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
        Class tagWrapper = Class.forName("net.minecraft.tag.RequiredTagList$TagWrapper");
        SetTag<Block> tag = (SetTag<Block>)ReflectionHelper.getInstanceFieldValue(tagWrapper, "delegate", BlockTags.BEE_GROWABLES);

        List<MaterialCropBlock> beeGrowableBlocks = BlockRegistry.REGISTERED_CROP_BLOCKS.values()
                .stream()
                .filter(block -> block.getDroppedItem().getCropTier().isBeeGrowable())
                .collect(Collectors.toList());

        ImmutableList newTags = ImmutableList.builder()
                .addAll(BlockTags.BEE_GROWABLES.values())
                .addAll(beeGrowableBlocks)
                .build();

        ReflectionHelper.modifyInstanceField(SetTag.class, "valueList", tag, newTags);

        ImmutableSet newTagsSet = ImmutableSet.builder()
                .addAll(BlockTags.BEE_GROWABLES.values())
                .build();

        ReflectionHelper.modifyInstanceField(SetTag.class, "valueSet", tag, newTagsSet);
    }
}
