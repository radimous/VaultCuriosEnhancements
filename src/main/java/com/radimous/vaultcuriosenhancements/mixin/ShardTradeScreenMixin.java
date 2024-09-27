package com.radimous.vaultcuriosenhancements.mixin;


import com.radimous.vaultcuriosenhancements.Config;
import com.radimous.vaultcuriosenhancements.ShardLabel;
import com.radimous.vaultcuriosenhancements.VaultCuriosEnhancements;
import iskallia.vault.client.gui.framework.render.spi.IElementRenderer;
import iskallia.vault.client.gui.framework.render.spi.ITooltipRendererFactory;
import iskallia.vault.client.gui.framework.screen.AbstractElementContainerScreen;
import iskallia.vault.client.gui.framework.spatial.Spatials;
import iskallia.vault.client.gui.framework.text.LabelTextStyle;
import iskallia.vault.client.gui.screen.ShardTradeScreen;
import iskallia.vault.container.inventory.ShardTradeContainer;
import iskallia.vault.item.ItemShardPouch;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(value = ShardTradeScreen.class, remap = false)
public class ShardTradeScreenMixin extends AbstractElementContainerScreen<ShardTradeContainer> {
    private ShardTradeScreenMixin(ShardTradeContainer container, Inventory inventory,
                                 Component title,
                                 IElementRenderer elementRenderer,
                                 ITooltipRendererFactory<AbstractElementContainerScreen<ShardTradeContainer>> tooltipRendererFactory) {
        super(container, inventory, title, elementRenderer, tooltipRendererFactory);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        if (!Config.showShardCountInBlackMarket.get()) {
            return;
        }
        var player = Minecraft.getInstance().player;
        if (player == null) return;
        Supplier<String> shardCountFn;
        if (Config.blackMarketShortFmt.get()){
            shardCountFn = () -> VaultCuriosEnhancements.fmtNum(ItemShardPouch.getShardCount(player));
        } else {
            shardCountFn = () -> String.valueOf(ItemShardPouch.getShardCount(player));
        }
        this.addElement(
            new ShardLabel(Spatials.positionXYZ(37, 20, 200), shardCountFn, LabelTextStyle.border8().center())
                .layout((screen, gui, parent, world) -> world.translateXYZ(gui))
        );
    }
}
