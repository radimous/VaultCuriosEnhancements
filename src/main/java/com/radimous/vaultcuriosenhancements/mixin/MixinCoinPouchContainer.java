package com.radimous.vaultcuriosenhancements.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import iskallia.vault.container.inventory.CoinPouchContainer;
import iskallia.vault.init.ModItems;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

@Mixin(value = CoinPouchContainer.class, remap = false)
public class MixinCoinPouchContainer {

    @Shadow @Final private Inventory inventory;

    @WrapOperation(method = {"hasPouch", "lambda$new$1"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;getItem(I)Lnet/minecraft/world/item/ItemStack;", remap = true))
    private ItemStack getItem(Inventory instance, int i, Operation<ItemStack> original) {
        if (i == -1) {
            ItemStack stack = CuriosApi.getCuriosHelper().findFirstCurio(this.inventory.player, ModItems.COIN_POUCH).map(SlotResult::stack).orElse(null);
            if (stack == null) {
                return ItemStack.EMPTY;
            }
            return stack;
        }
        return original.call(instance, i);
    }
}
