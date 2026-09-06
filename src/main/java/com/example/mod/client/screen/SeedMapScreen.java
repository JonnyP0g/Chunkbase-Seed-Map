package com.example.mod.client.screen;

import com.example.mod.client.config.ModConfig;
import com.example.mod.client.render.MapRenderer;
import com.example.mod.client.ui.MapUIPanel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.lang.reflect.Method;

public class SeedMapScreen extends Screen {
    private static final int PANEL_WIDTH = 170;

    private final MapRenderer mapRenderer = new MapRenderer();
    private double zoom = 1.0D;
    private double panX = 0.0D;
    private double panY = 0.0D;

    public SeedMapScreen() {
        super(Text.translatable("screen.chunkbase-seed-map.title"));
    }

    @Override
    protected void init() {
        super.init();
        int panelX = this.width - PANEL_WIDTH - 10;
        int panelY = 40;
        MapUIPanel.addButtons(this, panelX, panelY, PANEL_WIDTH);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        int overlayColor = 0x88000000;
        context.fill(0, 0, this.width, this.height, overlayColor);

        int mapX = 10;
        int mapY = 40;
        int mapWidth = this.width - PANEL_WIDTH - 30;
        int mapHeight = this.height - 50;

        mapRenderer.render(
            context,
            mapX,
            mapY,
            mapWidth,
            mapHeight,
            resolveSeed(),
            zoom,
            panX,
            panY,
            ModConfig.isShowBiomes(),
            ModConfig.isShowStructures(),
            ModConfig.isShowGrid(),
            ModConfig.isShowCoordinates(),
            mouseX,
            mouseY
        );

        context.drawText(this.textRenderer, this.title, 10, 12, 0xFFFFFFFF, true);
        context.drawText(this.textRenderer, Text.literal("Seed: " + resolveSeed()), 120, 12, 0xFFFFFFFF, true);
        context.drawText(this.textRenderer, Text.literal("Zoom: " + String.format("%.2fx", zoom)), mapX + 8, mapY + 8, 0xFFFFFFFF, false);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (verticalAmount > 0) {
            zoom = Math.min(6.0D, zoom * 1.1D);
        } else if (verticalAmount < 0) {
            zoom = Math.max(0.4D, zoom / 1.1D);
        }
        return true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            panX += deltaX;
            panY += deltaY;
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            close();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void close() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            client.setScreen(null);
        }
    }

    private long resolveSeed() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) {
            return 0L;
        }

        Object server = client.getServer();
        if (server == null) {
            return 0L;
        }

        try {
            Method getSaveProperties = server.getClass().getMethod("getSaveProperties");
            Object saveProperties = getSaveProperties.invoke(server);
            Method getGeneratorOptions = saveProperties.getClass().getMethod("getGeneratorOptions");
            Object generatorOptions = getGeneratorOptions.invoke(saveProperties);
            Method getSeed = generatorOptions.getClass().getMethod("getSeed");
            Object seedValue = getSeed.invoke(generatorOptions);
            if (seedValue instanceof Long seed) {
                return seed;
            }
        } catch (ReflectiveOperationException ignored) {
            return 0L;
        }
        return 0L;
    }
}
