package io.github.steviegt6.wheatdsmod.factories;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;

import java.util.Random;

public class GeneralTradeFactory implements TradeOffers.Factory {
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