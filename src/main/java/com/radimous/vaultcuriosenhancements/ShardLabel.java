package com.radimous.vaultcuriosenhancements;

import iskallia.vault.client.gui.framework.element.DynamicLabelElement;
import iskallia.vault.client.gui.framework.spatial.spi.IPosition;
import iskallia.vault.client.gui.framework.text.LabelTextStyle;
import net.minecraft.network.chat.TextComponent;

import java.util.function.Supplier;

public class ShardLabel extends DynamicLabelElement<Integer, ShardLabel> {

    public ShardLabel(IPosition position, Supplier<Integer> valueSupplier, LabelTextStyle.Builder labelTextStyle) {
        super(position, valueSupplier, labelTextStyle);
    }

    @Override protected void onValueChanged(Integer shardCount) {
        this.set(new TextComponent(String.valueOf(shardCount)));
    }
}