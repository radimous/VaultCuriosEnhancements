package com.radimous.vaultcuriosenhancements;

import iskallia.vault.client.gui.framework.element.DynamicLabelElement;
import iskallia.vault.client.gui.framework.spatial.spi.IPosition;
import iskallia.vault.client.gui.framework.text.LabelTextStyle;
import net.minecraft.network.chat.TextComponent;

import java.util.function.Supplier;

public class ShardLabel extends DynamicLabelElement<String, ShardLabel> {

    public ShardLabel(IPosition position, Supplier<String> valueSupplier, LabelTextStyle.Builder labelTextStyle) {
        super(position, valueSupplier, labelTextStyle);
    }

    @Override protected void onValueChanged(String shardCount) {
        this.set(new TextComponent(shardCount));
    }
}