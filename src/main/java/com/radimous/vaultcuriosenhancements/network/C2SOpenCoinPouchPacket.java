package com.radimous.vaultcuriosenhancements.network;

import iskallia.vault.init.ModItems;
import iskallia.vault.item.CoinPouchItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent.Context;
import top.theillusivec4.curios.api.CuriosApi;

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
                    CuriosApi.getCuriosHelper().findFirstCurio(sender, ModItems.COIN_POUCH).ifPresent(antiqueSlot ->  CoinPouchItem.openGUI(sender, -1));
                }
            }
        );
        context.setPacketHandled(true);
    }

}
