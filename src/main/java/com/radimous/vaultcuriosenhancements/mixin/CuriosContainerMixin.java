package com.radimous.vaultcuriosenhancements.mixin;

import iskallia.vault.item.gear.CharmItem;
import iskallia.vault.item.gear.TrinketItem;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import top.theillusivec4.curios.common.inventory.container.CuriosContainer;

@Mixin(value = CuriosContainer.class)
public abstract class CuriosContainerMixin extends InventoryMenu {
    private CuriosContainerMixin(Inventory p_39706_, boolean p_39707_, Player p_39708_) {
        super(p_39706_, p_39707_, p_39708_);
    }

    @Inject(method = "quickMoveStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Mob;getEquipmentSlotForItem(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/entity/EquipmentSlot;", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void quickMoveTrinkets(Player playerIn, int index, CallbackInfoReturnable<ItemStack> cir, ItemStack itemstack, Slot slot, ItemStack itemstack1) {
        Item item = itemstack1.getItem();
        // original: if (index < 46 && !CuriosApi.getCuriosHelper().getCurioTags(itemstack.getItem()).isEmpty())
        // we are pretending that charms and trinkets have curio tags
        if (index < 46 && (item instanceof CharmItem || item instanceof TrinketItem)) {
            if (!this.moveItemStackTo(itemstack1, 46, this.slots.size(), false)) {
                cir.setReturnValue(ItemStack.EMPTY);
            }
        }
    }
}
