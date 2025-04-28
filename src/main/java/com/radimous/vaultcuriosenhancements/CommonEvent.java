package com.radimous.vaultcuriosenhancements;

import com.radimous.vaultcuriosenhancements.mixin.CoinPouchItemAccessor;
import iskallia.vault.antique.Antique;
import iskallia.vault.container.inventory.AntiqueCollectorBookContainer;
import iskallia.vault.container.inventory.CoinPouchContainer;
import iskallia.vault.container.inventory.ShardPouchContainer;
import iskallia.vault.init.ModItems;
import iskallia.vault.item.AntiqueItem;
import iskallia.vault.item.AntiqueStampCollectorBook;
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
        ItemStack toAdd = event.getItem().getItem();
        var player = event.getEntity() instanceof net.minecraft.world.entity.player.Player pl?  pl : null;
        if (player == null) {
            return;
        }

        if (toAdd.getItem() == ModItems.SOUL_SHARD) {
            if (!(player.containerMenu instanceof ShardPouchContainer)) {
                ItemStack shardPouchStack = ItemStack.EMPTY;
                if(CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.SHARD_POUCH).isPresent()) {
                    shardPouchStack = CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.SHARD_POUCH).get().stack();
                }

                if (!shardPouchStack.isEmpty()) {
                    shardPouchStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent((handler) -> {
                        ItemStack remainder = handler.insertItem(0, toAdd, false);
                        toAdd.setCount(remainder.getCount());
                        if (toAdd.isEmpty()) {
                            event.setCanceled(true);
                        }
                    });
                }
            }
        }

        if (CoinPouchItemAccessor.invokeIsCoinItem(toAdd.getItem())) {
            if (!(player.containerMenu instanceof CoinPouchContainer)) {
                ItemStack coinPouchStack = ItemStack.EMPTY;

                if(CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.COIN_POUCH).isPresent()) {
                    coinPouchStack = CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.COIN_POUCH).get().stack();
                }
                if (!coinPouchStack.isEmpty()) {
                    coinPouchStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent((handler) -> {
                        ItemStack remainder = handler.insertItem(CoinPouchItemAccessor.invokeGetCoinSlotIndex(toAdd.getItem()), toAdd, false);
                        toAdd.setCount(remainder.getCount());
                        if (toAdd.isEmpty()) {
                            event.setCanceled(true);
                        }
                    });
                }
            }
        }

        if (toAdd.is(ModItems.ANTIQUE)) {
            Antique antique = AntiqueItem.getAntique(toAdd);
            if (antique != null) {
                if (!(player.containerMenu instanceof AntiqueCollectorBookContainer)) {
                    ItemStack antiqueBook = ItemStack.EMPTY;

                    if (CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.ANTIQUE_COLLECTOR_BOOK).isPresent()) {
                        antiqueBook = CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.ANTIQUE_COLLECTOR_BOOK).get().stack();
                    }

                    if (!antiqueBook.isEmpty()) {
                        AntiqueStampCollectorBook.StoredAntiques antiques = AntiqueStampCollectorBook.getStoredAntiques(antiqueBook);
                        AntiqueStampCollectorBook.StoredAntiqueInfo info = antiques.getInfo(antique);
                        int count = info.getCount();
                        int result = Math.min(count + toAdd.getCount(), 2147483582);
                        int added = result - count;
                        info.setCount(result);
                        toAdd.setCount(toAdd.getCount() - added);
                        AntiqueStampCollectorBook.setStoredAntiques(antiqueBook, antiques);
                        if (toAdd.isEmpty()) {
                            event.setCanceled(true);
                        }
                    }
                }
            }
        }
    }
}
