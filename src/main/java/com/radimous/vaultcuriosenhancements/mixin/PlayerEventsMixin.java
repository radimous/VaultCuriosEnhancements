package com.radimous.vaultcuriosenhancements.mixin;

import iskallia.vault.event.PlayerEvents;
import iskallia.vault.init.ModItems;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(value = PlayerEvents.class, remap = false)
public class PlayerEventsMixin {
    @Inject(method = "hasVaultCharm", at = @At("HEAD"), cancellable = true)
    private static void checkCuriosForJunkID(Inventory inventory, CallbackInfoReturnable<Boolean> cir) {
        if (CuriosApi.getCuriosHelper()
            .findFirstCurio(inventory.player, ModItems.VAULT_CHARM)
            .isPresent()) {
            cir.setReturnValue(true);
        }
    }
}
