package com.radimous.vaultcuriosenhancements.network;

import com.radimous.vaultcuriosenhancements.VaultCuriosEnhancements;
import iskallia.vault.init.ModItems;
import iskallia.vault.item.AntiqueStampCollectorBook;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
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


                    int index = VaultCuriosEnhancements.getFirstItemIndex(sender, ModItems.ANTIQUE_COLLECTOR_BOOK);
                    if (index == -2) {
                        sender.displayClientMessage(new TextComponent("You don't have a Antique Collector Book in your inventory!"), true);
                        return;
                    }

                    SlotResult antiqueSlot = CuriosApi.getCuriosHelper().findFirstCurio(sender, ModItems.ANTIQUE_COLLECTOR_BOOK).orElse(null);
                    if (antiqueSlot != null) {
                        AntiqueStampCollectorBook.openBook(sender, -1, antiqueSlot.stack());
                        return;
                    }

                    if (index != -1) {
                        ItemStack stack = sender.getInventory().items.get(index);
                        AntiqueStampCollectorBook.openBook(sender, index, stack);
                    }
                }
            }
        );
        context.setPacketHandled(true);
    }
}
