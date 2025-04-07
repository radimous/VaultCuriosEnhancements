package com.radimous.vaultcuriosenhancements.pouchkeybind;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;

public class ModContainers {
    public static MenuType<CurioShardPouchContainer> CURIO_SHARD_POUCH_CONTAINER = IForgeMenuType.create((windowId, inventory, data) -> new CurioShardPouchContainer(windowId, inventory));

}
