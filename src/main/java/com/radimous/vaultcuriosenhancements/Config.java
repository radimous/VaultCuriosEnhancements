package com.radimous.vaultcuriosenhancements;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    static final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.BooleanValue showShardCountInInvhud;
    public static final ForgeConfigSpec.BooleanValue showCoinsInInvhud;

    public static final ForgeConfigSpec.IntValue invhudCoinsCurrency;
    public static final ForgeConfigSpec.BooleanValue invhudCoinsCurrencySuffix;
    public static final ForgeConfigSpec.BooleanValue invhudCoinsShortFmt;
    public static final ForgeConfigSpec.BooleanValue invhudShardShortFmt;
    public static final ForgeConfigSpec.BooleanValue invhudCharmUses;
    public static final ForgeConfigSpec.BooleanValue invhudVoidStoneUses;
    public static final ForgeConfigSpec.BooleanValue invhudTrinketUses;
    public static final ForgeConfigSpec CLIENT_SPEC;

    static {
        builder.push("Inventory HUD");
        showShardCountInInvhud = builder
            .comment("Show shard count in Inventory HUD overlay")
            .define("invHudShardCount", true);
        showCoinsInInvhud = builder
            .comment("Show coins in Inventory HUD overlay")
            .define("invHudCoins", true);

        invhudCoinsCurrency = builder
            .comment("Currency to show in Inventory HUD overlay (0 = bronze, 1 = silver, 2 = gold, 3 = platinum), more currencies might be added by other mods - setting higher value than max will show highest currency available")
            .defineInRange("invHudCoinsCurrency", 2, 0, 32);
        invhudCoinsCurrencySuffix = builder
            .comment("Enable suffix after coin count in Inventory HUD overlay (B for bronze, S for silver, G for gold, P for platinum)")
            .define("invHudCoinsCurrencySuffix", false);
        invhudCoinsShortFmt = builder
            .comment("Show 12.3k instead of 12345 in Inventory HUD overlay for coin pouch")
            .define("invHudCoinCountShortFmt", false);
        invhudShardShortFmt = builder
            .comment("Show 12.3k instead of 12345 in Inventory HUD overlay for shard pouch")
            .define("invHudShardCountShortFmt", true);
        invhudCharmUses = builder
            .comment("Show charm uses left in Inventory HUD overlay")
            .define("invHudCharmUsesLeft", true);
        invhudTrinketUses = builder
            .comment("Show trinket uses left in Inventory HUD overlay")
            .define("invHudTrinketUsesLeft", true);
        invhudVoidStoneUses = builder
            .comment("Show void stone uses left in Inventory HUD overlay")
            .define("invHudVoidStoneUsesLeft", true);
        builder.pop();

        CLIENT_SPEC = builder.build();
    }
}
