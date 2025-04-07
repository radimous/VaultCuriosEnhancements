package com.radimous.vaultcuriosenhancements.network;

import com.radimous.vaultcuriosenhancements.VaultCuriosEnhancements;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
    private static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(
        VaultCuriosEnhancements.MODID, "main"))
        .serverAcceptedVersions(a -> true)
        .clientAcceptedVersions(a -> true)
        .networkProtocolVersion(() -> "1")
        .simpleChannel();

    public static void register() {
        INSTANCE.messageBuilder(C2SSetCompassTargetPacket.class, 0, NetworkDirection.PLAY_TO_SERVER)
            .encoder(C2SSetCompassTargetPacket::encode)
            .decoder(C2SSetCompassTargetPacket::decode)
            .consumer(C2SSetCompassTargetPacket::handle)
            .add();
        INSTANCE.messageBuilder(C2SOpenCurioShardPouchMessage.class, 1, NetworkDirection.PLAY_TO_SERVER)
            .encoder(C2SOpenCurioShardPouchMessage::encode)
            .decoder(C2SOpenCurioShardPouchMessage::decode)
            .consumer(C2SOpenCurioShardPouchMessage::handle)
            .add();


    }

    public static void sendToServer(Object msg) {
        INSTANCE.sendToServer(msg);
    }
}
