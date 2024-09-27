package com.radimous.vaultcuriosenhancements;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    static final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.BooleanValue showShardCountInBlackMarket = builder
        .comment("Show shard count in black market")
        .define("showShardCountInBlackMarket", false);

    public static final ForgeConfigSpec.BooleanValue blackMarketShortFmt = builder
        .comment("Show 12.3k instead of 12345")
        .define("blackMarketShortFmt", false);

    public static final ForgeConfigSpec.BooleanValue showShardCountInInvhud = builder
        .comment("Show shard count in Inventory HUD overlay")
        .define("showShardCountInInventoryHud", true);

    public static final ForgeConfigSpec.BooleanValue invhudShortFmt = builder
        .comment("Show 12.3k instead of 12345")
        .define("inventoryHudShortFmt", true);
    public static final ForgeConfigSpec CLIENT_SPEC = builder.build();
}
