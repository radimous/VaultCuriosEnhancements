package com.radimous.vaultcuriosenhancements.network;

import com.radimous.vaultcuriosenhancements.VaultCuriosEnhancements;
import iskallia.vault.container.inventory.ShardPouchContainer;
import iskallia.vault.init.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class C2SOpenShardPouchPacket {
    public static void encode(C2SOpenShardPouchPacket msg, FriendlyByteBuf packetBuffer) {
    }

    public static C2SOpenShardPouchPacket decode(FriendlyByteBuf packetBuffer) {
        return new C2SOpenShardPouchPacket();
    }

    public static void handle(C2SOpenShardPouchPacket pkt, Supplier<Context> contextSupplier) {
        Context context = contextSupplier.get();
        context.enqueueWork(
            () -> {
                ServerPlayer sender = context.getSender();
                if (sender != null) {

                    int index = VaultCuriosEnhancements.getFirstItemIndex(sender, ModItems.SHARD_POUCH);
                    if (index == -2) {
                        sender.displayClientMessage(new TextComponent("You don't have a Shard Pouch in your inventory!"), true);
                        return;
                    }
                    NetworkHooks.openGui(sender, new MenuProvider() {
                        public @NotNull Component getDisplayName() {
                            return new TextComponent("Shard Pouch");
                        }

                        public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
                            return new ShardPouchContainer(windowId, inventory, index);
                        }
                    }, buf -> buf.writeInt(index));

                }
            }
        );
        context.setPacketHandled(true);
    }

}
