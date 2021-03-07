package io.github.steviegt6.wheatdsmod.registry;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;

import java.util.Random;

public class MerchantRegistry {

    public static void registerTrades() {
        registerFarmerTrades();
    }

    public static void registerFarmerTrades() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 1, factories -> {
            //18 coal wheat for 1 emerald
            factories.add(new BulkTradeFactory(ItemRegistry.REGISTERED_CROPS.get(0), 18, 16, 2));
        });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 2, factories -> {
            //16 iron wheat for 1 emerald
            factories.add(new BulkTradeFactory(ItemRegistry.REGISTERED_CROPS.get(1), 16, 16, 4));
        });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 3, factories -> {
            //14 gold wheat for 1 emerald
            factories.add(new BulkTradeFactory(ItemRegistry.REGISTERED_CROPS.get(2), 14, 16, 8));
        });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 4, factories -> {
            //12 diamond wheat for 1 emerald
            factories.add(new BulkTradeFactory(ItemRegistry.REGISTERED_CROPS.get(5), 12, 16, 16));
        });
    }

    static class GeneralTradeFactory implements TradeOffers.Factory {
        private final Item buyItem;
        private final int buyPrice;
        private final Item sellItem;
        private final int sellAmount;
        private final int maxUses;
        private final int experience;
        private final float priceMultiplier;

        public GeneralTradeFactory(ItemConvertible buyItem, int buyPrice, ItemConvertible sellItem, int sellAmount, int maxUses, int experience, float priceMultiplier) {
            this.buyItem = buyItem.asItem();
            this.buyPrice = buyPrice;
            this.sellItem = sellItem.asItem();
            this.sellAmount = sellAmount;
            this.maxUses = maxUses;
            this.experience = experience;
            this.priceMultiplier = priceMultiplier;
        }

        public TradeOffer create(Entity entity, Random random) {
            ItemStack buyItemStack = new ItemStack(this.buyItem, this.buyPrice);
            ItemStack sellItemStack = new ItemStack(this.sellItem, this.sellAmount);
            return new TradeOffer(buyItemStack, sellItemStack, this.maxUses, this.experience, this.priceMultiplier);
        }
    }

    /**
     * Shorthand factory for the common "bulk item" trades found in vanilla. You always get 1 emerald in return for these trades, with a fixed price multiplier.
     * Vanilla's usage of "bulk trades" include trading carrots/potatoes in exchange for 1 emerald from farmers. This factory is used to mimic this functionality.
     */
    static class BulkTradeFactory extends GeneralTradeFactory {

        public BulkTradeFactory(ItemConvertible buyItem, int buyPrice, int maxUses, int experience) {
            super(buyItem, buyPrice, Items.EMERALD, 1, maxUses, experience, 0.005F);
        }

    }

}
