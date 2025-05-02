package com.radimous.vaultcuriosenhancements.mixin;

import iskallia.vault.item.CoinPouchItem;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = CoinPouchItem.class, remap = false)
public interface CoinPouchItemAccessor {
    @Invoker
    static boolean invokeIsCoinItem(Item item) {return false;}
    @Invoker
    static int invokeGetCoinSlotIndex(Item item) {return 0;}
}
