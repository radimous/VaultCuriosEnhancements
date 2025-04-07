package com.radimous.vaultcuriosenhancements;

import iskallia.vault.container.inventory.ShardPouchContainer;
import iskallia.vault.init.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import top.theillusivec4.curios.api.CuriosApi;

public class CommonEvent {

    @SubscribeEvent(
        priority = EventPriority.HIGH // above backpacks
    )
    public void onItemPickup(EntityItemPickupEvent event){
        ItemStack stack = event.getItem().getItem();
        var player = event.getEntity() instanceof net.minecraft.world.entity.player.Player pl?  pl : null;
        if (player == null) {
            return;
        }
        if (stack.getItem() == ModItems.SOUL_SHARD) {
            if (!(player.containerMenu instanceof ShardPouchContainer)) {
                ItemStack pouchStack = ItemStack.EMPTY;
                if(CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.SHARD_POUCH).isPresent()) {
                    pouchStack = CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.SHARD_POUCH).get().stack();
                }

                if (!pouchStack.isEmpty()) {
                    pouchStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent((handler) -> {
                        ItemStack remainder = handler.insertItem(0, stack, false);
                        stack.setCount(remainder.getCount());
                        if (stack.isEmpty()) {
                            event.setCanceled(true);
                        }
                    });
                }
            }
        }
    }
}
