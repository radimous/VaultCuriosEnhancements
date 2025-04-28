package com.radimous.vaultcuriosenhancements.mixin.invhud;

import com.radimous.vaultcuriosenhancements.Config;
import dlovin.inventoryhud.armorstatus.ArmorStatus;
import iskallia.vault.item.CoinPouchItem;
import iskallia.vault.item.ItemShardPouch;
import iskallia.vault.item.gear.CharmItem;
import iskallia.vault.item.gear.TrinketItem;
import iskallia.vault.item.gear.VoidStoneItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ArmorStatus.class, remap = false)
public class ArmorStatusMixin {

    // This tells invhud that items have 1% of durability left
    // Invhud then passes the item to method to get damage text to display
    // We can then override the text with more useful information (shard count, trinket uses,...)
    @Inject(method = "getDamage", at = @At("HEAD"), cancellable = true)
    private static void durabilityCheckBypass(ItemStack itemStack, CallbackInfoReturnable<Integer> cir) {
        Item item = itemStack.getItem();
        if (Config.showShardCountInInvhud.get() && item instanceof ItemShardPouch) {
            cir.setReturnValue(1);
        }
        if (Config.showCoinsInInvhud.get() && item instanceof CoinPouchItem) {
            cir.setReturnValue(1);
        }
        if (Config.invhudTrinketUses.get() && item instanceof TrinketItem) {
            cir.setReturnValue(1);
        }
        if (Config.invhudCharmUses.get() && item instanceof CharmItem) {
            cir.setReturnValue(1);
        }
        if (Config.invhudVoidStoneUses.get() && item instanceof VoidStoneItem) {
            cir.setReturnValue(1);
        }
    }

}
