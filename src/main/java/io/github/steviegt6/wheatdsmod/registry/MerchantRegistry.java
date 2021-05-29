package io.github.steviegt6.wheatdsmod.registry;

import io.github.steviegt6.wheatdsmod.factories.BulkTradeFactory;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.village.VillagerProfession;

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
}
