package com.radimous.vaultcuriosenhancements.pouchkeybind;

import iskallia.vault.container.oversized.OverSizedSlotContainer;
import iskallia.vault.container.slot.ConditionalReadSlot;
import iskallia.vault.init.ModItems;
import iskallia.vault.item.ItemShardPouch;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import javax.annotation.Nonnull;

public class CurioShardPouchContainer extends OverSizedSlotContainer {
    public CurioShardPouchContainer(int id, Inventory playerInventory) {
        super(ModContainers.CURIO_SHARD_POUCH_CONTAINER, id, playerInventory.player);
        if (this.hasPouch()) {
            playerInventory.player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent((playerInvHandler) -> {
                ItemStack pouch = getPouchStack();
                pouch.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent((pouchHandler) -> this.initSlots(playerInvHandler, pouchHandler));
            });
        }

    }

    private void initSlots(IItemHandler playerInvHandler, final IItemHandler pouchHandler) {
        for(int row = 0; row < 3; ++row) {
            for(int column = 0; column < 9; ++column) {
                this.addSlot(new ConditionalReadSlot(playerInvHandler, column + row * 9 + 9, 8 + column * 18, 55 + row * 18, this::canAccess));
            }
        }

        for(int hotbarSlot = 0; hotbarSlot < 9; ++hotbarSlot) {
            this.addSlot(new ConditionalReadSlot(playerInvHandler, hotbarSlot, 8 + hotbarSlot * 18, 113, this::canAccess));
        }

        this.addSlot(new ConditionalReadSlot(pouchHandler, 0, 80, 16, (slot, stack) -> this.canAccess(slot, stack) && stack.getItem() == ModItems.SOUL_SHARD) {
            public int getMaxStackSize(@Nonnull ItemStack stack) {
                return pouchHandler.getSlotLimit(0);
            }

            public void setChanged() {
                ((IItemHandlerModifiable)this.getItemHandler()).setStackInSlot(this.getSlotIndex(), this.getItem());
            }
        });
    }

    public boolean stillValid(Player player) {
        return this.hasPouch();
    }

    public boolean canAccess(int slot, ItemStack slotStack) {
        return this.hasPouch() && !(slotStack.getItem() instanceof ItemShardPouch);
    }

    public boolean hasPouch() {
        return getPouchStack().getItem() instanceof ItemShardPouch;
    }

    public ItemStack getPouchStack() {
        return CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.SHARD_POUCH).map(SlotResult::stack).orElse(ItemStack.EMPTY);
    }

    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            itemstack = slotStack.copy();
            if (index >= 0 && index < 36 && this.moveItemStackTo(slotStack, 36, 37, false)) {
                return itemstack;
            }

            if (index >= 0 && index < 27) {
                if (!this.moveItemStackTo(slotStack, 27, 36, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 27 && index < 36) {
                if (!this.moveItemStackTo(slotStack, 0, 27, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(slotStack, 0, 36, false)) {
                return ItemStack.EMPTY;
            }

            if (slotStack.getCount() == 0) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (slotStack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, slotStack);
        }

        return itemstack;
    }
}