package com.radimous.vaultcuriosenhancements;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    static final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.BooleanValue showShardCountInBlackMarket = builder
        .comment("Show shard count in black market")
        .define("showShardCountInBlackMarket", false);
    public static final ForgeConfigSpec CLIENT_SPEC = builder.build();
}
