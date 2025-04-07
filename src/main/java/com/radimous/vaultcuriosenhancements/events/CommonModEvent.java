package com.radimous.vaultcuriosenhancements.events;

import com.radimous.vaultcuriosenhancements.VaultCuriosEnhancements;
import com.radimous.vaultcuriosenhancements.network.PacketHandler;
import com.radimous.vaultcuriosenhancements.pouchkeybind.ModContainers;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = VaultCuriosEnhancements.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvent {
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(PacketHandler::register);
    }

    @SubscribeEvent
    public static void onContainerRegister(RegistryEvent.Register<MenuType<?>> event) {
        event.getRegistry().registerAll(
                ModContainers.CURIO_SHARD_POUCH_CONTAINER.setRegistryName(VaultCuriosEnhancements.MODID, "curio_shard_pouch_container")
        );
    }
}
