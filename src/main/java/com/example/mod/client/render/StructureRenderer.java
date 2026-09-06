package com.example.mod.client.render;

import net.minecraft.client.gui.DrawContext;

public final class StructureRenderer {
    public void renderPlaceholders(DrawContext context, int mapX, int mapY, int mapWidth, int mapHeight, double zoom, double panX, double panY) {
        int centerX = mapX + mapWidth / 2;
        int centerY = mapY + mapHeight / 2;
        int markerSpacing = Math.max(40, (int) (120 / zoom));

        for (int offsetX = -markerSpacing * 2; offsetX <= markerSpacing * 2; offsetX += markerSpacing) {
            for (int offsetY = -markerSpacing * 2; offsetY <= markerSpacing * 2; offsetY += markerSpacing) {
                int x = (int) (centerX + offsetX + panX);
                int y = (int) (centerY + offsetY + panY);
                if (x <= mapX || x >= mapX + mapWidth || y <= mapY || y >= mapY + mapHeight) {
                    continue;
                }
                context.fill(x - 2, y - 2, x + 2, y + 2, 0xFFE74C3C);
            }
        }
    }
}
