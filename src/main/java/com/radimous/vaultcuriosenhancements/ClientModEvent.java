package com.radimous.vaultcuriosenhancements;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = VaultCuriosEnhancements.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvent {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        Keybind.register();
    }
}