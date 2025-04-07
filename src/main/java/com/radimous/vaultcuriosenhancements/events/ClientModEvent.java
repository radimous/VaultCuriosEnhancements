package com.radimous.vaultcuriosenhancements.events;

import com.radimous.vaultcuriosenhancements.Keybind;
import com.radimous.vaultcuriosenhancements.pouchkeybind.CurioShardPouchScreen;
import com.radimous.vaultcuriosenhancements.pouchkeybind.ModContainers;
import com.radimous.vaultcuriosenhancements.VaultCuriosEnhancements;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = VaultCuriosEnhancements.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvent {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(Keybind.REBIND_COMPASS);
        ClientRegistry.registerKeyBinding(Keybind.OPEN_POUCH);
        MenuScreens.register(ModContainers.CURIO_SHARD_POUCH_CONTAINER, CurioShardPouchScreen::new);
    }
}