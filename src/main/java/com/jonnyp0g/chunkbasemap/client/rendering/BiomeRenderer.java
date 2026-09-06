package com.jonnyp0g.chunkbasemap.client.rendering;

import net.minecraft.client.gui.GuiGraphics;

/**
 * Handles rendering of biomes on the map.
 * Will display biome colors based on world seed and position.
 */
public class BiomeRenderer {
    
    private long worldSeed;
    
    public BiomeRenderer(long seed) {
        this.worldSeed = seed;
    }
    
    public void render(GuiGraphics guiGraphics, int x, int y, int width, int height, 
                      int zoomLevel, int panX, int panY) {
        // TODO: Implement actual biome rendering based on seed
        // For now, render a placeholder gradient
        
        int chunkSize = 4 * zoomLevel; // Each pixel represents one chunk at zoom 1
        
        for (int px = 0; px < width; px += chunkSize) {
            for (int py = 0; py < height; py += chunkSize) {
                int biomeColor = this.getBiomeColorAt(
                    (px / chunkSize + panX / 16),
                    (py / chunkSize + panY / 16)
                );
                guiGraphics.fill(x + px, y + py, x + px + chunkSize, y + py + chunkSize, biomeColor);
            }
        }
    }
    
    private int getBiomeColorAt(int chunkX, int chunkZ) {
        // Use world seed to deterministically generate biome colors
        // This is a placeholder - should integrate with Minecraft's biome system
        
        long hash = (this.worldSeed ^ ((long)chunkX * 73428659L) ^ ((long)chunkZ * 1050261813L));
        hash = (hash ^ (hash >> 16)) * 0x7feb352dL;
        hash = (hash ^ (hash >> 15));
        
        int biomeType = (int)(hash % 5); // Simple 5-biome system for demo
        
        return switch(biomeType) {
            case 0 -> 0xFF00AA00; // Forest - Green
            case 1 -> 0xFFFFFF00; // Desert - Yellow
            case 2 -> 0xFF00AAFF; // Ocean - Blue
            case 3 -> 0xFF808080; // Mountain - Gray
            case 4 -> 0xFFFFAAAA; // Snow - Light
            default -> 0xFF00AA00;
        };
    }
}
