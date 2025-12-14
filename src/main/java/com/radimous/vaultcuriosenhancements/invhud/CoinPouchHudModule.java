package com.radimous.vaultcuriosenhancements.invhud;

import com.mojang.blaze3d.vertex.PoseStack;
import com.radimous.vaultcuriosenhancements.ModOptions;
import com.radimous.vaultcuriosenhancements.VaultCuriosEnhancements;
import iskallia.vault.client.render.HudPosition;
import iskallia.vault.client.render.hud.InventoryHudHelper;
import iskallia.vault.client.render.hud.module.AbstractHudModule;
import iskallia.vault.client.render.hud.module.context.ModuleRenderContext;
import iskallia.vault.client.render.hud.module.settings.ModuleSetting;
import iskallia.vault.client.render.hud.module.settings.SliderSetting;
import iskallia.vault.client.render.hud.module.settings.ToggleButtonSetting;
import iskallia.vault.init.ModItems;
import iskallia.vault.item.CoinPouchItem;
import iskallia.vault.options.VaultOption;
import iskallia.vault.options.types.InventoryHudElementOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;
import java.util.function.Supplier;

public class CoinPouchHudModule extends AbstractHudModule<ModuleRenderContext> {

    private static final int COIN_TEXT_COLOR = 0xFFFFAA00;

    private final Supplier<ItemStack> pouchSupplier;
    private final VaultOption<InventoryHudElementOptions> option;

    public CoinPouchHudModule(
        Supplier<ItemStack> pouchSupplier,
        VaultOption<InventoryHudElementOptions> option
    ) {
        super(
            "coin_pouch",
            "Coin Pouch",
            "Displays the gold count from your Coin Pouch."
        );
        this.pouchSupplier = pouchSupplier;
        this.option = option;
    }

    private static boolean hasCurioSlot(Player player) {
        if (player == null) return false;
        return CuriosApi.getCuriosHelper().getCuriosHandler(player).map(handler -> handler.getCurios().get("coin_pouch").getSlots() > 0).orElse(false);
    }

    @Override
    protected void renderModule(ModuleRenderContext context) {
        PoseStack poseStack = context.getPoseStack();
        LocalPlayer player = Minecraft.getInstance().player;

        if (player == null || !hasCurioSlot(player)) {
            return;
        }

        InventoryHudElementOptions opts = this.option.getValue();
        ItemStack pouchStack = this.pouchSupplier.get();
        int goldCount = CoinPouchItem.getGoldAmount(player.getInventory());
        if (context.isEditing()) {
            pouchStack = new ItemStack(ModItems.COIN_POUCH);
            goldCount = 1337;
        }

        Font font = Minecraft.getInstance().font;
        String text;
        if (ModOptions.SHORT_COIN_VALUE.getValue()) {
            text = VaultCuriosEnhancements.fmtNum(goldCount);
        } else {
            text = String.valueOf(goldCount);
        }

        // -------------------------------------------------
        // Icon
        // -------------------------------------------------

        int iconX = switch (opts.getTextPosition()) {
            case RIGHT -> 0;
            case LEFT -> font.width(text) + 2;
            default -> font.width(text)/2 - 8; // half - half of pouch
        };

        int iconY = switch (opts.getTextPosition()) {
            case ABOVE -> 12;
            default -> 0;
        };

        if (!pouchStack.isEmpty()) {
            InventoryHudHelper.renderScaledGuiItem(
                poseStack,
                pouchStack,
                iconX,
                iconY,
                false
            );
        }

        // -------------------------------------------------
        // Text
        // -------------------------------------------------
        if ((goldCount > 0)
            && !opts.getDisplayMode().equals(InventoryHudElementOptions.DisplayMode.NEVER)) {

            poseStack.pushPose();
            poseStack.translate(0.0, 0.0, 999.0);

            float textX = switch (opts.getTextPosition()) {
                case RIGHT -> iconX + 18;
                case LEFT -> iconX - 2 - font.width(text);
                case BELOW, ABOVE -> iconX + (16 / 2f) - (font.width(text) / 2f);
                case ONTOP -> iconX + 16 - font.width(text);
            };

            float textY = switch (opts.getTextPosition()) {
                case RIGHT, LEFT -> iconY + 4;
                case BELOW -> iconY + 18;
                case ABOVE -> iconY - 10;
                case ONTOP -> 16 - 9;
            };

            font.drawShadow(
                poseStack,
                text,
                textX,
                textY,
                ModOptions.COLORED_COIN_TEXT.getValue() ? COIN_TEXT_COLOR : 0xFFFFFFFF
            );

            poseStack.popPose();
        }
    }

    // ------------------------------------------------------------------------
    // HUD plumbing
    // ------------------------------------------------------------------------

    @Override
    public HudPosition getPosition() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (!hasCurioSlot(player)) {
            return null;
        }
        return this.option.getValue().getHudPosition();
    }

    @Override
    public void setPosition(HudPosition pos) {
        InventoryHudElementOptions opts = this.option.getValue();
        opts.setHudPosition(pos);
        this.option.setValue(opts);
    }

    @Override
    public float getScale() {
        return this.option.getValue().getSize();
    }

    @Override
    public void resetToDefaultPosition() {
        InventoryHudElementOptions def = this.option.getDefaultValue();
        InventoryHudElementOptions current = this.option.getValue();
        current.setHudPosition(def.getHudPosition());
        this.option.setValue(current);
    }

    @Override
    public List<ModuleSetting> getSettings() {
        InventoryHudElementOptions opts = this.option.getValue();
        return List.of(
            new SliderSetting(
                "size",
                new TextComponent("Size"),
                new TextComponent("Controls the size of the coin pouch in the inventory HUD."),
                () -> new TextComponent("Size"), () -> opts.getSize() - 0.5F, (value) -> {
                opts.setSize(value + 0.5F);
                this.option.setValue(opts);
            }),
            new ToggleButtonSetting(
                "text_position",
                new TextComponent("Text Position"),
                new TextComponent("Controls the position of the text relative to the item."),
                () -> opts.getTextPosition().name(),
                () -> this.option.setValue(opts.setTextPosition(opts.getTextPosition().next()))
            ),
            new ToggleButtonSetting(
                "short_coin_value",
                new TextComponent("Format"),
                new TextComponent("1.23M instead of 1234567"),
                () -> ModOptions.SHORT_COIN_VALUE.getValue() ? "Short (1.23M)" : "Full (1234567)",
                () -> ModOptions.SHORT_COIN_VALUE.setValue(!ModOptions.SHORT_COIN_VALUE.getValue())
            ),
            new ToggleButtonSetting(
                "colored_coin_text",
                new TextComponent("Colored Text"),
                new TextComponent("Colored Text"),
                () -> ModOptions.COLORED_COIN_TEXT.getValue() ? "On" : "Off",
                () -> ModOptions.COLORED_COIN_TEXT.setValue(!ModOptions.COLORED_COIN_TEXT.getValue())
            )
        );
    }

    // ------------------------------------------------------------------------
    // Editor bounds
    // ------------------------------------------------------------------------

    @Override
    protected int baseWidth(ModuleRenderContext context) {
        InventoryHudElementOptions opts = this.option.getValue();
        Font font = Minecraft.getInstance().font;
        int textWidth;

        var player = Minecraft.getInstance().player;
        if (player != null) {
            int goldCount = CoinPouchItem.getGoldAmount(player.getInventory());
            if (context.isEditing()) {
                goldCount = 1337;
            }

            String text;
            if (ModOptions.SHORT_COIN_VALUE.getValue()) {
                text = VaultCuriosEnhancements.fmtNum(goldCount);
            } else {
                text = String.valueOf(goldCount);
            }
            textWidth = font.width(text);
        } else {
            textWidth = font.width("9999");
        }

        return switch (opts.getTextPosition()) {
            case LEFT, RIGHT -> 16 + 2 + textWidth;
            default -> Math.max(16, textWidth);
        };
    }

    @Override
    protected int baseHeight(ModuleRenderContext context) {
        InventoryHudElementOptions opts = this.option.getValue();
        Font font = Minecraft.getInstance().font;

        return switch (opts.getTextPosition()) {
            case ABOVE, BELOW -> 16 + font.lineHeight + 2;
            default -> 16;
        };
    }

    @Override
    public boolean isHidden(ModuleRenderContext context) {
        return pouchSupplier.get().isEmpty() || this.option.getValue()
            .getDisplayMode()
            .equals(InventoryHudElementOptions.DisplayMode.NEVER);
    }
}