package com.radimous.vaultcuriosenhancements.mixin;

import iskallia.vault.item.CompassItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = CompassItem.class, remap = false)
public interface CompassItemAccessor {
    @Invoker
    void invokeSetTarget(Player player, Level level, BlockPos pos);
}