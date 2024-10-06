package com.radimous.vaultcuriosenhancements;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    static final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.BooleanValue showShardCountInBlackMarket;

    public static final ForgeConfigSpec.BooleanValue blackMarketShortFmt;

    public static final ForgeConfigSpec.BooleanValue showShardCountInInvhud;

    public static final ForgeConfigSpec.BooleanValue invhudShortFmt;
    public static final ForgeConfigSpec.BooleanValue invhudCharmUses;
    public static final ForgeConfigSpec.BooleanValue invhudTrinketUses;
    public static final ForgeConfigSpec CLIENT_SPEC;

    static {
        builder.push("Black Market");
        showShardCountInBlackMarket = builder
            .comment("Show shard count in black market")
            .define("showShardCountInBlackMarket", false);
        blackMarketShortFmt = builder
            .comment("Show 12.3k instead of 12345 in black market")
            .define("blackMarketShortFmt", false);
        builder.pop();

        builder.push("Inventory HUD");
        showShardCountInInvhud = builder
            .comment("Show shard count in Inventory HUD overlay")
            .define("invHudShardCount", true);
        invhudShortFmt = builder
            .comment("Show 12.3k instead of 12345 in Inventory HUD overlay for shard pouch")
            .define("invHudShardCountShortFmt", true);
        invhudCharmUses = builder
            .comment("Show charm uses left in Inventory HUD overlay")
            .define("invHudCharmUsesLeft", true);
        invhudTrinketUses = builder
            .comment("Show trinket uses left in Inventory HUD overlay")
            .define("invHudTrinketUsesLeft", true);
        builder.pop();

        CLIENT_SPEC = builder.build();
    }
}
