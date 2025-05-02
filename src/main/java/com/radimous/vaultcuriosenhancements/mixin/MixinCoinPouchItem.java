package com.radimous.vaultcuriosenhancements.mixin;

import iskallia.vault.init.ModItems;
import iskallia.vault.item.CoinPouchItem;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

@Mixin(value = CoinPouchItem.class, remap = false)
public class MixinCoinPouchItem {
    @Inject(method = "getTotalBronzeValue(Lnet/minecraft/world/entity/player/Player;)I", at = @At("RETURN"), cancellable = true)
    private static void addPouchBronze(Player player, CallbackInfoReturnable<Integer> cir) {
        var pouch = CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.COIN_POUCH).map(SlotResult::stack).orElse(null);
        if (pouch != null && pouch.getItem() instanceof CoinPouchItem) {
            cir.setReturnValue(cir.getReturnValueI() + CoinPouchItem.getCoinCount(pouch));
        }
    }

    // why does this take in inventory and not player?
    @Inject(method = "getGoldAmount", at = @At("RETURN"), cancellable = true)
    private static void addPouchGold(Inventory playerInventory, CallbackInfoReturnable<Integer> cir) {
        var pouch = CuriosApi.getCuriosHelper().findFirstCurio(playerInventory.player, ModItems.COIN_POUCH).map(SlotResult::stack).orElse(null);
        if (pouch != null && pouch.getItem() instanceof CoinPouchItem) {
            cir.setReturnValue(cir.getReturnValueI() + CoinPouchItem.getCoinCount(pouch) / 81);
        }
    }
}
