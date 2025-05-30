package com.radimous.vaultcuriosenhancements;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
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
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
            () -> new SlotTypeMessage.Builder("vault_compass")
                .icon(new ResourceLocation(CuriosApi.MODID + ":slot/vault_compass"))
                .priority(800).build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
            () -> new SlotTypeMessage.Builder("shard_pouch")
                .icon(new ResourceLocation(CuriosApi.MODID + ":slot/shard_pouch"))
                .priority(800).build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
            () -> new SlotTypeMessage.Builder("coin_pouch")
                .icon(new ResourceLocation(CuriosApi.MODID + ":slot/coin_pouch"))
                .priority(800).build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
            () -> new SlotTypeMessage.Builder("antique_collector_book")
                .icon(new ResourceLocation(CuriosApi.MODID + ":slot/antique_collector_book"))
                .priority(800).build());
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

    /**
     * Get the first index of the item in the player's inventory. -1 if in curio, -2 if not found
     * @param player player
     * @param item item to find
     * @return index of the item in the player's inventory, -1 if in curio, -2 if not found
     */
    public static int getFirstItemIndex(ServerPlayer player, Item item) {
        SlotResult shardPouchSlot = CuriosApi.getCuriosHelper().findFirstCurio(player,item).orElse(null);
        if (shardPouchSlot != null) {
            return -1;
        }

        for (int i = 0; i < player.getInventory().items.size(); ++i) {
            ItemStack stack = player.getInventory().items.get(i);
            if (!stack.isEmpty() && stack.is(item)) {
                return i;
            }
        }
        return -2;
    }
}
