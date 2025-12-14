package com.radimous.vaultcuriosenhancements.mixin;

import iskallia.vault.init.ModOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ModOptions.class, remap = false)
public class MixinModOptions {
    @Inject(method = "init", at = @At("HEAD"))
    private static void initVCEOptions(CallbackInfo ci){
        // init the class (to register) just before reading the file
        com.radimous.vaultcuriosenhancements.ModOptions.init();
    }
}
