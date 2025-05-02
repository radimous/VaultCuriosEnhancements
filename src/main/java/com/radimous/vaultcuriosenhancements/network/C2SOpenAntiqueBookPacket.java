package com.radimous.vaultcuriosenhancements.network;

import iskallia.vault.init.ModItems;
import iskallia.vault.item.AntiqueStampCollectorBook;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent.Context;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.function.Supplier;

public class C2SOpenAntiqueBookPacket {
    public static void encode(C2SOpenAntiqueBookPacket msg, FriendlyByteBuf packetBuffer) {
    }

    public static C2SOpenAntiqueBookPacket decode(FriendlyByteBuf packetBuffer) {
        return new C2SOpenAntiqueBookPacket();
    }

    public static void handle(C2SOpenAntiqueBookPacket pkt, Supplier<Context> contextSupplier) {
        Context context = contextSupplier.get();
        context.enqueueWork(
            () -> {
                ServerPlayer sender = context.getSender();
                if (sender != null) {

                    SlotResult antiqueSlot = CuriosApi.getCuriosHelper().findFirstCurio(sender, ModItems.ANTIQUE_COLLECTOR_BOOK).orElse(null);
                    if (antiqueSlot != null) {
                        AntiqueStampCollectorBook.openBook(sender, -1, antiqueSlot.stack());
                        return;
                    }
                    int index = getAntiqueStackIndex(sender);
                    if (index != -1) {
                        ItemStack stack = sender.getInventory().items.get(index);
                        AntiqueStampCollectorBook.openBook(sender, index, stack);
                    }
                }
            }
        );
        context.setPacketHandled(true);
    }

    private static int getAntiqueStackIndex(ServerPlayer sender) {
        for (int i = 0; i < sender.getInventory().items.size(); ++i) {
            ItemStack stack = sender.getInventory().items.get(i);
            if (!stack.isEmpty() && stack.is(ModItems.ANTIQUE_COLLECTOR_BOOK)) {
                return i;
            }
        }
        return -1;
    }

}
