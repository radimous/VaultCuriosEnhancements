package com.radimous.vaultcuriosenhancements.network;

import com.radimous.vaultcuriosenhancements.mixin.ItemAccessor;
import iskallia.vault.core.vault.Vault;
import iskallia.vault.core.vault.player.Listener;
import iskallia.vault.core.vault.player.Listeners;
import iskallia.vault.world.data.ServerVaults;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SSetCompassTargetPacket {
    public static void encode(C2SSetCompassTargetPacket msg, FriendlyByteBuf packetBuffer) {
    }

    public static C2SSetCompassTargetPacket decode(FriendlyByteBuf packetBuffer) {
        return new C2SSetCompassTargetPacket();
    }

    public static void handle(C2SSetCompassTargetPacket msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> setCompassPos(msg, context.getSender()));
        context.setPacketHandled(true);
    }

    public static void setCompassPos(C2SSetCompassTargetPacket msg, ServerPlayer player) {
        Level level = player.level;
        BlockPos newTargetPos = player.blockPosition();
        BlockHitResult blockHitResult = ItemAccessor.invokeGetPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);
        if (blockHitResult != null && blockHitResult.getType() == HitResult.Type.BLOCK) {
            newTargetPos = blockHitResult.getBlockPos();
        }
        setTarget(player, level, newTargetPos);
        player.displayClientMessage(new TextComponent("New compass target set"), true);
    }

    private static void setTarget(Player player, Level level, BlockPos pos) {
        ServerVaults.get(level).ifPresent((vault) -> {
            Listeners listeners = vault.get(Vault.LISTENERS);
            if (listeners.contains(player.getUUID())) {
                listeners.get(player.getUUID()).set(Listener.COMPASS_TARGET, pos);
            }

        });
    }

}
