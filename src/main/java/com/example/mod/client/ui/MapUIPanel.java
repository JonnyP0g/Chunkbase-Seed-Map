package com.example.mod.client.ui;

import com.example.mod.client.config.ModConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public final class MapUIPanel {
    private MapUIPanel() {
    }

    public static void addButtons(Screen screen, int panelX, int panelY, int panelWidth) {
        int buttonWidth = panelWidth - 16;
        int buttonX = panelX + 8;
        int y = panelY + 8;

        screen.addDrawableChild(ButtonWidget.builder(toggleText("ui.chunkbase-seed-map.toggle.biomes", ModConfig.isShowBiomes()), button -> {
            ModConfig.setShowBiomes(!ModConfig.isShowBiomes());
            button.setMessage(toggleText("ui.chunkbase-seed-map.toggle.biomes", ModConfig.isShowBiomes()));
            ModConfig.save();
        }).dimensions(buttonX, y, buttonWidth, 20).build());

        y += 24;
        screen.addDrawableChild(ButtonWidget.builder(toggleText("ui.chunkbase-seed-map.toggle.structures", ModConfig.isShowStructures()), button -> {
            ModConfig.setShowStructures(!ModConfig.isShowStructures());
            button.setMessage(toggleText("ui.chunkbase-seed-map.toggle.structures", ModConfig.isShowStructures()));
            ModConfig.save();
        }).dimensions(buttonX, y, buttonWidth, 20).build());

        y += 24;
        screen.addDrawableChild(ButtonWidget.builder(toggleText("ui.chunkbase-seed-map.toggle.grid", ModConfig.isShowGrid()), button -> {
            ModConfig.setShowGrid(!ModConfig.isShowGrid());
            button.setMessage(toggleText("ui.chunkbase-seed-map.toggle.grid", ModConfig.isShowGrid()));
            ModConfig.save();
        }).dimensions(buttonX, y, buttonWidth, 20).build());

        y += 24;
        screen.addDrawableChild(ButtonWidget.builder(toggleText("ui.chunkbase-seed-map.toggle.coordinates", ModConfig.isShowCoordinates()), button -> {
            ModConfig.setShowCoordinates(!ModConfig.isShowCoordinates());
            button.setMessage(toggleText("ui.chunkbase-seed-map.toggle.coordinates", ModConfig.isShowCoordinates()));
            ModConfig.save();
        }).dimensions(buttonX, y, buttonWidth, 20).build());
    }

    private static Text toggleText(String key, boolean enabled) {
        return Text.translatable(key).append(": ").append(enabled ? Text.translatable("ui.chunkbase-seed-map.on") : Text.translatable("ui.chunkbase-seed-map.off"));
    }
}
