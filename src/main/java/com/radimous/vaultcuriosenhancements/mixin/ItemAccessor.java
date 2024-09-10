package com.radimous.vaultcuriosenhancements.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
@Mixin(Item.class)
public interface ItemAccessor {
    @Invoker
    static BlockHitResult invokeGetPlayerPOVHitResult(Level p_41436_, Player p_41437_, ClipContext.Fluid p_41438_){
        return null;
    }

}
