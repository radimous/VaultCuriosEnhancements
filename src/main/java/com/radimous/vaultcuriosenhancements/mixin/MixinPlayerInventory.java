package com.radimous.vaultcuriosenhancements.mixin;

import iskallia.vault.container.inventory.ShardPouchContainer;
import iskallia.vault.init.ModItems;
import iskallia.vault.world.data.InventorySnapshotData;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;

@Mixin(value = {Inventory.class}, priority = 9999)
public abstract class MixinPlayerInventory implements InventorySnapshotData.InventoryAccessor {
    @Shadow
    @Final
    public Player player;
    @Shadow
    @Final
    private List<NonNullList<ItemStack>> compartments;

    public MixinPlayerInventory() {
    }

    @Inject(
            method = {"add(Lnet/minecraft/world/item/ItemStack;)Z"},
            at = {@At("HEAD")},
            cancellable = true
    )
    public void interceptItemAddition(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.getItem() == ModItems.SOUL_SHARD) {
            if (!(this.player.containerMenu instanceof ShardPouchContainer)) {
                ItemStack pouchStack = ItemStack.EMPTY;
                if(CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.SHARD_POUCH).isPresent()) {
                    pouchStack = CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.SHARD_POUCH).get().stack();
                }

                if (!pouchStack.isEmpty()) {
                    pouchStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent((handler) -> {
                        ItemStack remainder = handler.insertItem(0, stack, false);
                        stack.setCount(remainder.getCount());
                        if (stack.isEmpty()) {
                            cir.setReturnValue(true);
                        }

                    });
                }
            }
        }
    }

    public int getSize() {
        return this.compartments.stream().mapToInt(NonNullList::size).sum();
    }
}

