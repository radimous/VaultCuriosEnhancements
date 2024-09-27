package com.radimous.vaultcuriosenhancements.mixin.invhud;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.radimous.vaultcuriosenhancements.Config;
import dlovin.inventoryhud.gui.renderers.EachArmorRenderer;
import iskallia.vault.item.ItemShardPouch;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = EachArmorRenderer.class, remap = false)
public class EachArmorRendererMixin {
    @WrapOperation(method = "renderItem", at = @At(value = "INVOKE", target = "Ldlovin/inventoryhud/armorstatus/ArmorStatus;getDamage(Lnet/minecraft/world/item/ItemStack;)I"))
    private int shardPouchDmg(ItemStack item, Operation<Integer> original) {
        if (Config.showShardCountInInvhud.get() && item.getItem() instanceof ItemShardPouch) {
            return 1;
        }
        return original.call(item);
    }
}
