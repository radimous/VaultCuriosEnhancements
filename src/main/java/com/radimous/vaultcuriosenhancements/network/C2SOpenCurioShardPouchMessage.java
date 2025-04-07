package com.radimous.vaultcuriosenhancements.network;

import com.radimous.vaultcuriosenhancements.pouchkeybind.CurioShardPouchContainer;
import iskallia.vault.init.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class C2SOpenCurioShardPouchMessage {
    public static void encode(C2SOpenCurioShardPouchMessage msg, FriendlyByteBuf packetBuffer) {
    }

    public static C2SOpenCurioShardPouchMessage decode(FriendlyByteBuf packetBuffer) {
        return new C2SOpenCurioShardPouchMessage();
    }

    public static void handle(C2SOpenCurioShardPouchMessage msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> openShardPouch(msg, context.getSender()));
        context.setPacketHandled(true);
    }

    public static void openShardPouch(C2SOpenCurioShardPouchMessage msg, ServerPlayer player) {
        if (CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.SHARD_POUCH).map(SlotResult::stack).orElse(ItemStack.EMPTY).isEmpty()) {
            player.displayClientMessage(new TextComponent("You don't have a shard pouch in curio slot!"), true);
            return;
        }
        NetworkHooks.openGui(player, new MenuProvider() {
            public Component getDisplayName() {
                return new TextComponent("Shard Pouch");
            }

            @Nullable
            public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
                return new CurioShardPouchContainer(windowId, inventory);
            }
        }, buf -> buf.writeInt(0)); // hack
    }

}
