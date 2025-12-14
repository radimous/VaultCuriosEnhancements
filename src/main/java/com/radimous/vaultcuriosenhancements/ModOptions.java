package com.radimous.vaultcuriosenhancements;


import iskallia.vault.client.render.HudPosition;
import iskallia.vault.core.data.adapter.Adapters;
import iskallia.vault.options.VaultOption;
import iskallia.vault.options.VaultOptionsRegistry;
import iskallia.vault.options.types.InventoryHudElementOptions;

public class ModOptions {
    private static final String FILENAME = VaultCuriosEnhancements.MODID + "__vaultOptions.json";
    public static final VaultOption<InventoryHudElementOptions> SHARD_POUCH = VaultOptionsRegistry.register(FILENAME, VaultCuriosEnhancements.id("inv_hud_shard_pouch"), InventoryHudElementOptions.createDefault(new HudPosition(0.9F, 0.13F)), null, InventoryHudElementOptions.ADAPTER);
    public static final VaultOption<InventoryHudElementOptions> COIN_POUCH = VaultOptionsRegistry.register(FILENAME, VaultCuriosEnhancements.id("inv_hud_coin_pouch"), InventoryHudElementOptions.createDefault(new HudPosition(0.9F, 0.22F)), null, InventoryHudElementOptions.ADAPTER);

    public static final VaultOption<Boolean> SHORT_COIN_VALUE = VaultOptionsRegistry.register(FILENAME, VaultCuriosEnhancements.id("short_coin_value"), true, null, Adapters.BOOLEAN);
    public static final VaultOption<Boolean> SHORT_SHARD_VALUE = VaultOptionsRegistry.register(FILENAME, VaultCuriosEnhancements.id("short_shard_value"), true, null, Adapters.BOOLEAN);
    public static final VaultOption<Boolean> COLORED_SHARD_TEXT = VaultOptionsRegistry.register(FILENAME, VaultCuriosEnhancements.id("colored_shard_text"), false, null, Adapters.BOOLEAN);
    public static final VaultOption<Boolean> COLORED_COIN_TEXT = VaultOptionsRegistry.register(FILENAME, VaultCuriosEnhancements.id("colored_coin_text"), false, null, Adapters.BOOLEAN);

    public static void init() {
        VaultCuriosEnhancements.LOGGER.info("Initializing vault mod options");
    }
}