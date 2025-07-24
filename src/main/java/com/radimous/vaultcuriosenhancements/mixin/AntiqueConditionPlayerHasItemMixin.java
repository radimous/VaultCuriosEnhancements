package com.radimous.vaultcuriosenhancements.mixin;

import iskallia.vault.antique.condition.AntiqueConditionPlayerHasItem;
import iskallia.vault.core.world.data.item.ItemPredicate;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(value = AntiqueConditionPlayerHasItem.class, remap = false)
public class AntiqueConditionPlayerHasItemMixin {
    @Inject(method = "hasItem", at = @At(value = "HEAD"), cancellable = true)
    private void hasItem(final ServerPlayer player, ItemPredicate pred, CallbackInfoReturnable<Boolean> cir) {
        var curios = CuriosApi.getCuriosHelper().findFirstCurio(player, pred::test);
        if (curios.isPresent()){
            cir.setReturnValue(true);
        }
    }
}
