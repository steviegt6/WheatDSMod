package io.github.steviegt6.wheatdsmod.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.GatherItemsVillagerTask;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(GatherItemsVillagerTask.class)
public abstract class GatherItemsVillagerTaskMixin {
    @Shadow
    private static void giveHalfOfStack(VillagerEntity villager, Set<Item> validItems, LivingEntity target) {
    }

    @Shadow private Set<Item> items;

    @Inject(
            at = @At("TAIL"),
            method = "keepRunning"
    )
    protected void keepRunning(ServerWorld serverWorld, VillagerEntity villagerEntity, long l, CallbackInfo info) {
        // Not needed since our Wheat isn't edible, lol
        /*if (villagerEntity.getBrain().getOptionalMemory(MemoryModuleType.INTERACTION_TARGET).isPresent()) {
            VillagerEntity villager2 = (VillagerEntity) villagerEntity.getBrain().getOptionalMemory(MemoryModuleType.INTERACTION_TARGET).get();

            if (villagerEntity.squaredDistanceTo(villager2) > 5)
                return;

            LookTargetUtil.lookAtAndWalkTowardsEachOther(villagerEntity, villager2, 0.5f);
            villagerEntity.talkWithVillager(serverWorld, villager2, l);

            if (villager2.getVillagerData().getProfession() == VillagerProfession.FARMER && villagerEntity.getInventory().count(Items.WHEAT) > Items.WHEAT.getMaxCount() / 2)
                giveHalfOfStack(villagerEntity, ImmutableSet.of(Items.WHEAT), villager2);
        }*/
    }
}
