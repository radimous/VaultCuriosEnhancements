package com.radimous.vaultcuriosenhancements;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;

@Mod("vault_curios_enhancements")
public class VaultCuriosEnhancements {

    public VaultCuriosEnhancements() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
            () -> new SlotTypeMessage.Builder("vault_compass").priority(800).build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("shard_pouch").icon(new ResourceLocation(CuriosApi.MODID + ":slot/shard_pouch")).priority(800).size(1).build());
    }
}
