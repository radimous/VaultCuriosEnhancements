package com.radimous.vaultcuriosenhancements.mixin;

import com.radimous.vaultcuriosenhancements.ModOptions;
import com.radimous.vaultcuriosenhancements.invhud.CoinPouchHudModule;
import com.radimous.vaultcuriosenhancements.invhud.ShardPouchHudModule;
import iskallia.vault.client.render.InventoryHudRenderer;
import iskallia.vault.client.render.hud.module.InventoryHudModule;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = InventoryHudRenderer.class, remap = false)
public abstract class MixinInventoryHudRenderer {


    @Shadow
    @Final
    public static List<String> HUD_KEYS;

    @Shadow
    @Final
    private static List<InventoryHudModule<?>> MODULES;

    @Shadow
    private static ItemStack getCurioSlotByName(Player player, String identifier) {
        return null;
    }

    @Inject(method = "initModules", at = @At("TAIL"))
    private static void registerWoldsModules(CallbackInfo ci) {
        MODULES.add(new ShardPouchHudModule(() -> getCurioSlotByName(Minecraft.getInstance().player, "shard_pouch"), ModOptions.SHARD_POUCH));
        MODULES.add(new CoinPouchHudModule(() -> getCurioSlotByName(Minecraft.getInstance().player, "coin_pouch"), ModOptions.COIN_POUCH));
    }

    static {
        List<String> hudKeys = new ArrayList<>(HUD_KEYS);
        hudKeys.add("shard_pouch");
        hudKeys.add("coin_pouch");
        HUD_KEYS = hudKeys;
    }
}