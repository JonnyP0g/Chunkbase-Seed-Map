package com.example.mod.client.render;

import net.minecraft.client.gui.DrawContext;

public final class MapRenderer {
    private final BiomeRenderer biomeRenderer = new BiomeRenderer();
    private final StructureRenderer structureRenderer = new StructureRenderer();

    public void render(DrawContext context, int mapX, int mapY, int mapWidth, int mapHeight, long seed, double zoom, double panX, double panY, boolean showBiomes, boolean showStructures, boolean showGrid, boolean showCoordinates, int mouseX, int mouseY) {
        context.fill(mapX, mapY, mapX + mapWidth, mapY + mapHeight, 0xCC121212);

        if (showBiomes) {
            renderBiomes(context, mapX, mapY, mapWidth, mapHeight, seed, zoom, panX, panY);
        }

        if (showGrid) {
            renderGrid(context, mapX, mapY, mapWidth, mapHeight, zoom, panX, panY);
        }

        if (showStructures) {
            structureRenderer.renderPlaceholders(context, mapX, mapY, mapWidth, mapHeight, zoom, panX, panY);
        }

        if (showCoordinates && mouseX >= mapX && mouseX <= mapX + mapWidth && mouseY >= mapY && mouseY <= mapY + mapHeight) {
            int worldX = (int) (((mouseX - (mapX + mapWidth / 2.0) - panX) / zoom) * 16);
            int worldZ = (int) (((mouseY - (mapY + mapHeight / 2.0) - panY) / zoom) * 16);
            context.drawText(net.minecraft.client.MinecraftClient.getInstance().textRenderer, "X: " + worldX + "  Z: " + worldZ, mapX + 8, mapY + mapHeight - 14, 0xFFFFFFFF, false);
        }
    }

    private void renderBiomes(DrawContext context, int mapX, int mapY, int mapWidth, int mapHeight, long seed, double zoom, double panX, double panY) {
        int cellSize = Math.max(2, (int) Math.round(8 * zoom));
        int startX = mapX + (int) panX % cellSize - cellSize;
        int startY = mapY + (int) panY % cellSize - cellSize;

        for (int x = startX; x < mapX + mapWidth; x += cellSize) {
            for (int y = startY; y < mapY + mapHeight; y += cellSize) {
                int sampleX = (int) ((x - mapX - panX) / zoom);
                int sampleZ = (int) ((y - mapY - panY) / zoom);
                int color = biomeRenderer.getBiomeColor(seed, sampleX, sampleZ);
                context.fill(x, y, Math.min(x + cellSize, mapX + mapWidth), Math.min(y + cellSize, mapY + mapHeight), 0xFF000000 | color);
            }
        }
    }

    private void renderGrid(DrawContext context, int mapX, int mapY, int mapWidth, int mapHeight, double zoom, double panX, double panY) {
        int spacing = Math.max(16, (int) (64 * zoom));
        int xStart = mapX + (int) panX % spacing;
        int yStart = mapY + (int) panY % spacing;

        for (int x = xStart; x <= mapX + mapWidth; x += spacing) {
            context.fill(x, mapY, x + 1, mapY + mapHeight, 0x66FFFFFF);
        }
        for (int y = yStart; y <= mapY + mapHeight; y += spacing) {
            context.fill(mapX, y, mapX + mapWidth, y + 1, 0x66FFFFFF);
        }
    }
}
