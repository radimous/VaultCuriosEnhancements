package com.radimous.vaultcuriosenhancements.network;

import com.radimous.vaultcuriosenhancements.mixin.CompassItemAccessor;
import com.radimous.vaultcuriosenhancements.mixin.ItemAccessor;
import iskallia.vault.init.ModItems;
import iskallia.vault.item.CompassItem;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.function.Supplier;

public class C2SSetCompassTargetPacket {
    public static void encode(C2SSetCompassTargetPacket msg, FriendlyByteBuf packetBuffer) {
    }

    public static C2SSetCompassTargetPacket decode(FriendlyByteBuf packetBuffer) {
        return new C2SSetCompassTargetPacket();
    }

    public static void handle(C2SSetCompassTargetPacket msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> setCurioCompassPos(msg, context.getSender()));
        context.setPacketHandled(true);
    }

    public static void setCurioCompassPos(C2SSetCompassTargetPacket msg, ServerPlayer player) {
        CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.VAULT_COMPASS).ifPresent(slotRes -> {
                ItemStack stack = slotRes.stack();
                if (stack.getItem() instanceof CompassItem compassItem) {
                    Level level = player.level;
                    BlockPos newTargetPos = player.blockPosition();
                    BlockHitResult blockHitResult = ItemAccessor.invokeGetPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);
                    if (blockHitResult != null && blockHitResult.getType() == HitResult.Type.BLOCK) {
                        newTargetPos = blockHitResult.getBlockPos();
                    }
                    ((CompassItemAccessor) compassItem).invokeSetTarget(player, level, newTargetPos);
                    player.getCooldowns().addCooldown(stack.getItem(), 10);
                    player.displayClientMessage(new TextComponent("New compass target set"), true);
                }
            }
        );
    }
}
