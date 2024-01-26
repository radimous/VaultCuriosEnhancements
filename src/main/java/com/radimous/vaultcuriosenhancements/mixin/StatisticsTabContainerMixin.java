package com.radimous.vaultcuriosenhancements.mixin;

import iskallia.vault.container.NBTElementContainer;
import iskallia.vault.container.StatisticsTabContainer;
import iskallia.vault.container.spi.AbstractElementContainer;
import iskallia.vault.core.vault.stat.StatTotals;
import iskallia.vault.item.gear.CharmItem;
import iskallia.vault.item.gear.TrinketItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.function.Supplier;

@Mixin(value = StatisticsTabContainer.class)
public class StatisticsTabContainerMixin extends NBTElementContainer<StatTotals> {
    @Final
    @Shadow(remap = false)
    private AbstractElementContainer.SlotIndexRange curioSlotIndexRange;

    private StatisticsTabContainerMixin(Supplier<MenuType<?>> menuTypeSupplier, int id, Player player, StatTotals data, SlotIndexRange curioSlotIndexRange) {
        super(menuTypeSupplier, id, player, data);
    }

    @Inject(method = "quickMoveStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Mob;getEquipmentSlotForItem(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/entity/EquipmentSlot;", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void quickMoveTrinkets(Player player, int index, CallbackInfoReturnable<ItemStack> cir, ItemStack itemStack, Slot slot, ItemStack slotItemStack) {
        Item item = slotItemStack.getItem();
        // original: if (this.curioSlotIndexRange.start() && !CuriosApi.getCuriosHelper().getCurioTags(itemstack.getItem()).isEmpty())
        // we are pretending that charms and trinkets have curio tags
        if (index < this.curioSlotIndexRange.start() && (item instanceof CharmItem || item instanceof TrinketItem)) {
            this.moveItemStackTo(slotItemStack, this.curioSlotIndexRange, false);
        }
    }
}
