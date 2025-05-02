package com.radimous.vaultcuriosenhancements.network;

import com.radimous.vaultcuriosenhancements.VaultCuriosEnhancements;
import iskallia.vault.init.ModItems;
import iskallia.vault.item.CoinPouchItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class C2SOpenCoinPouchPacket {
    public static void encode(C2SOpenCoinPouchPacket msg, FriendlyByteBuf packetBuffer) {
    }

    public static C2SOpenCoinPouchPacket decode(FriendlyByteBuf packetBuffer) {
        return new C2SOpenCoinPouchPacket();
    }

    public static void handle(C2SOpenCoinPouchPacket pkt, Supplier<Context> contextSupplier) {
        Context context = contextSupplier.get();
        context.enqueueWork(
            () -> {
                ServerPlayer sender = context.getSender();
                if (sender != null) {

                    int index = VaultCuriosEnhancements.getFirstItemIndex(sender, ModItems.COIN_POUCH);
                    if (index == -2) {
                        sender.displayClientMessage(new TextComponent("You don't have a Coin Pouch in your inventory!"), true);
                        return;
                    }
                    CoinPouchItem.openGUI(sender, index);
                }
            }
        );
        context.setPacketHandled(true);
    }

}
