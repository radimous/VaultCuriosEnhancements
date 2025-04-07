package com.radimous.vaultcuriosenhancements;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;

@Mod(VaultCuriosEnhancements.MODID)
public class VaultCuriosEnhancements {
    public static final String MODID = "vault_curios_enhancements";

    public VaultCuriosEnhancements() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        MinecraftForge.EVENT_BUS.register(new CommonEvent());
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_SPEC);
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
            () -> new SlotTypeMessage.Builder("vault_compass").priority(800).build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("shard_pouch").icon(new ResourceLocation(CuriosApi.MODID + ":slot/shard_pouch")).priority(800).size(1).build());
    }

    public static String fmtNum(int num) {
        if (num >= 1_000_000_000) {      // 1.0B
            return String.format("%.1fB", num / 1_000_000_000.0);
        } else if (num >= 100_000_000) { // 100M
            return String.format("%.0fM", num / 1_000_000.0);
        } else if (num >= 1_000_000) {   // 1.0M 10.0M
            return String.format("%.1fM", num / 1_000_000.0);
        } else if (num >= 100_000) {     // 100K
            return String.format("%.0fK", num / 1_000.0);
        } else if (num >= 10_000) {      // 10.0K
            return String.format("%.1fK", num / 1_000.0);
        } else if (num >= 1_000) {       // 1.00K
            return String.format("%.2fK", num / 1_000.0);
        } else {
            return String.valueOf(num);
        }
    }
}
