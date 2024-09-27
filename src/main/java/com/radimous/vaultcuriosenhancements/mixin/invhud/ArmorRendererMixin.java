package com.radimous.vaultcuriosenhancements.mixin.invhud;

import com.radimous.vaultcuriosenhancements.Config;
import com.radimous.vaultcuriosenhancements.VaultCuriosEnhancements;
import dlovin.inventoryhud.gui.renderers.ArmorRenderer;
import iskallia.vault.item.ItemShardPouch;
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
            if (!contained.isEmpty()) {
                if (Config.invhudShortFmt.get()) {
                    cir.setReturnValue(VaultCuriosEnhancements.fmtNum(contained.getCount()));
                } else {
                    cir.setReturnValue(String.valueOf(contained.getCount()));
                }
            }
        }
    }
}
