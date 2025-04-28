package com.radimous.vaultcuriosenhancements.mixin.invhud;

import com.radimous.vaultcuriosenhancements.Config;
import com.radimous.vaultcuriosenhancements.VaultCuriosEnhancements;
import dlovin.inventoryhud.gui.renderers.ArmorRenderer;
import iskallia.vault.item.CoinPouchItem;
import iskallia.vault.item.ItemShardPouch;
import iskallia.vault.item.gear.CharmItem;
import iskallia.vault.item.gear.TrinketItem;
import iskallia.vault.item.gear.VoidStoneItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ArmorRenderer.class, remap = false)
public class ArmorRendererMixin {
    @Inject(method = "getText", at = @At("HEAD"), cancellable = true)
    private void getText(ItemStack item, int damage, CallbackInfoReturnable<String> cir) {
        if (item.getItem() instanceof ItemShardPouch) {
            ItemStack contained = ItemShardPouch.getContainedStack(item);
            if (contained.isEmpty()) {
                cir.setReturnValue("0");
            }
            if (Config.invhudShardShortFmt.get()) {
                cir.setReturnValue(VaultCuriosEnhancements.fmtNum(contained.getCount()));
            } else {
                cir.setReturnValue(String.valueOf(contained.getCount()));
            }
        }
        if (item.getItem() instanceof TrinketItem){
            cir.setReturnValue(String.valueOf(TrinketItem.getUses(item) - TrinketItem.getUsedVaults(item).size()));
        }
        if (item.getItem() instanceof CharmItem){
            cir.setReturnValue(String.valueOf(CharmItem.getUses(item) - CharmItem.getUsedVaults(item).size()));
        }
        if (item.getItem() instanceof VoidStoneItem){
            cir.setReturnValue(String.valueOf(VoidStoneItem.getUses(item) - VoidStoneItem.getUsedVaults(item).size()));
        }

        if (item.getItem() instanceof CoinPouchItem) {
            var stacks = CoinPouchItem.getContainedStacks(item);
            var currencyIdx = Config.invhudCoinsCurrency.get();
            if (stacks.length == 0){
                cir.setReturnValue("0");
                return;
            }
            if (currencyIdx >= stacks.length){
               currencyIdx = stacks.length - 1;
            }
            String currencyName = stacks[currencyIdx].getItem().getName(stacks[currencyIdx]).getString().replace("Vault ", "");
            String coinPouchText = "";
            if (Config.invhudCoinsShortFmt.get()) {
                coinPouchText += VaultCuriosEnhancements.fmtNum(stacks[currencyIdx].getCount());
            } else {
               coinPouchText += String.valueOf(stacks[currencyIdx].getCount());
            }
            if (Config.invhudCoinsCurrencySuffix.get()) {
                coinPouchText += currencyName.charAt(0);
            }
            cir.setReturnValue(coinPouchText);
        }
    }
}
