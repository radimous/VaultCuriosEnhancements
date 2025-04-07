package com.radimous.vaultcuriosenhancements.mixin;

import iskallia.vault.init.ModItems;
import iskallia.vault.item.ItemShardPouch;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.Optional;

import static iskallia.vault.item.ItemShardPouch.setContainedStack;

@Debug(export = true)
@Mixin(value = ItemShardPouch.class, remap = false)
public class ItemShardPouchMixin {
    @ModifyVariable(method = "getShardCount(Lnet/minecraft/world/entity/player/Inventory;)I", at = @At(value = "STORE", ordinal = 0), ordinal = 0)
    private static int getShardCount(int original, Inventory inventory){
        ItemStack stack = CuriosApi.getCuriosHelper().findFirstCurio(inventory.player, ModItems.SHARD_POUCH).map(SlotResult::stack).orElse(ItemStack.EMPTY);
        if (stack.isEmpty()) {
            return original;
        }

        return ItemShardPouch.getContainedStack(stack).getCount();
    }
    @ModifyVariable(method = "reduceShardAmount", at = @At(value = "HEAD"), argsOnly = true, ordinal = 0)
    private static int reduceShardAmount(int value, Inventory inventory, int count, boolean simulate){
        ItemStack stack = CuriosApi.getCuriosHelper().findFirstCurio(inventory.player, ModItems.SHARD_POUCH).map(SlotResult::stack).orElse(ItemStack.EMPTY);
        if (stack.isEmpty()) {
            return count;
        }
        ItemStack shardStack = ItemShardPouch.getContainedStack(stack);
        int toReduce = Math.min(count, shardStack.getCount());
        if (!simulate) {
            shardStack.setCount(shardStack.getCount() - toReduce);
            setContainedStack(stack, shardStack);
        }
        count -= toReduce;
        return Math.max(count, 0);
    }
}
