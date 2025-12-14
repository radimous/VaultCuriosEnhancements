package com.radimous.vaultcuriosenhancements.network;

import com.radimous.vaultcuriosenhancements.VaultCuriosEnhancements;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
    private static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(VaultCuriosEnhancements.id("main"))
        .serverAcceptedVersions(a -> true)
        .clientAcceptedVersions(a -> true)
        .networkProtocolVersion(() -> "1")
        .simpleChannel();

    public static void register() {
        INSTANCE.messageBuilder(C2SOpenAntiqueBookPacket.class, 1, NetworkDirection.PLAY_TO_SERVER)
            .encoder(C2SOpenAntiqueBookPacket::encode)
            .decoder(C2SOpenAntiqueBookPacket::decode)
            .consumer(C2SOpenAntiqueBookPacket::handle)
            .add();
        INSTANCE.messageBuilder(C2SOpenShardPouchPacket.class, 2, NetworkDirection.PLAY_TO_SERVER)
            .encoder(C2SOpenShardPouchPacket::encode)
            .decoder(C2SOpenShardPouchPacket::decode)
            .consumer(C2SOpenShardPouchPacket::handle)
            .add();
        INSTANCE.messageBuilder(C2SOpenCoinPouchPacket.class, 3, NetworkDirection.PLAY_TO_SERVER)
            .encoder(C2SOpenCoinPouchPacket::encode)
            .decoder(C2SOpenCoinPouchPacket::decode)
            .consumer(C2SOpenCoinPouchPacket::handle)
            .add();
    }

    public static void sendToServer(Object msg) {
        INSTANCE.sendToServer(msg);
    }
}
