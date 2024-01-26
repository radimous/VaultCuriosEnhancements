package com.radimous.vaultcuriosenhancements;

import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.SlotTypeMessage;

@Mod("vault_curios_enhancements")
public class VaultCuriosEnhancements {

    public VaultCuriosEnhancements() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
            () -> new SlotTypeMessage.Builder("junk_identifier").priority(1000).build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
            () -> new SlotTypeMessage.Builder("vault_compass").priority(800).build());
    }
}
