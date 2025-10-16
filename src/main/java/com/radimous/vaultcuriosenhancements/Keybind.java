package com.radimous.vaultcuriosenhancements;

import com.mojang.blaze3d.platform.InputConstants;
import com.radimous.vaultcuriosenhancements.network.C2SOpenAntiqueBookPacket;
import com.radimous.vaultcuriosenhancements.network.C2SOpenCoinPouchPacket;
import com.radimous.vaultcuriosenhancements.network.C2SOpenShardPouchPacket;
import com.radimous.vaultcuriosenhancements.network.PacketHandler;
import iskallia.vault.client.gui.screen.VaultCompassScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = VaultCuriosEnhancements.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class Keybind {
    @SubscribeEvent
    public static void handleEventInput(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || event.phase == TickEvent.Phase.START) {
            return;
        }

        if (OPEN_ANTIQUE_BOOK.consumeClick()) {
            PacketHandler.sendToServer(new C2SOpenAntiqueBookPacket());
        }
        if (OPEN_SHARD_POUCH.consumeClick()) {
            PacketHandler.sendToServer(new C2SOpenShardPouchPacket());
        }
        if (OPEN_COIN_POUCH.consumeClick()) {
            PacketHandler.sendToServer(new C2SOpenCoinPouchPacket());
        }
        if (OPEN_COMPASS_SCREEN.consumeClick()) {
            Minecraft.getInstance().setScreen(new VaultCompassScreen());
        }
    }


    public static final String VAULT_CURIOS_ENHANCEMENTS_CAT = "key.categories.vault_curios_enhancements";

    public static final KeyMapping OPEN_ANTIQUE_BOOK =
        new KeyMapping(
            "vault_curios_enhancements.openantiquebook",
            KeyConflictContext.IN_GAME,
            InputConstants.UNKNOWN,
            VAULT_CURIOS_ENHANCEMENTS_CAT
        );

    public static final KeyMapping OPEN_SHARD_POUCH =
        new KeyMapping(
            "vault_curios_enhancements.openshardpouch",
            KeyConflictContext.IN_GAME,
            InputConstants.UNKNOWN,
            VAULT_CURIOS_ENHANCEMENTS_CAT
        );

    public static final KeyMapping OPEN_COIN_POUCH =
        new KeyMapping(
            "vault_curios_enhancements.opencoinpouch",
            KeyConflictContext.IN_GAME,
            InputConstants.UNKNOWN,
            VAULT_CURIOS_ENHANCEMENTS_CAT
        );

    public static final KeyMapping OPEN_COMPASS_SCREEN =
        new KeyMapping(
            "vault_curios_enhancements.opencompass",
            KeyConflictContext.IN_GAME,
            InputConstants.UNKNOWN,
            VAULT_CURIOS_ENHANCEMENTS_CAT
        );

    public static void register() {
        ClientRegistry.registerKeyBinding(Keybind.OPEN_ANTIQUE_BOOK);
        ClientRegistry.registerKeyBinding(Keybind.OPEN_SHARD_POUCH);
        ClientRegistry.registerKeyBinding(Keybind.OPEN_COIN_POUCH);
        ClientRegistry.registerKeyBinding(Keybind.OPEN_COMPASS_SCREEN);
    }
}