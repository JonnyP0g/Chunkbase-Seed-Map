package com.jonnyp0g.chunkbasemap.client.rendering;

import net.minecraft.client.gui.GuiGraphics;
import com.jonnyp0g.chunkbasemap.config.ModConfig;

/**
 * Handles rendering of the seed map including biomes, structures, and grid.
 */
public class MapRenderer {
    
    private int mapWidth;
    private int mapHeight;
    private long worldSeed;
    
    private int zoomLevel = 1;
    private int panX = 0;
    private int panY = 0;
    
    private BiomeRenderer biomeRenderer;
    private StructureRenderer structureRenderer;
    
    public MapRenderer(int width, int height, long seed) {
        this.mapWidth = width;
        this.mapHeight = height;
        this.worldSeed = seed;
        
        this.biomeRenderer = new BiomeRenderer(seed);
        this.structureRenderer = new StructureRenderer(seed);
    }
    
    public void render(GuiGraphics guiGraphics, int x, int y, int mouseX, int mouseY) {
        // Draw map background
        guiGraphics.fill(x, y, x + this.mapWidth, y + this.mapHeight, 0xFF000000);
        guiGraphics.drawBorder(x, y, this.mapWidth, this.mapHeight, 0xFFAAAAAA);
        
        // Enable scissor test to clip rendering to map area
        // TODO: Implement proper scissor test for clipping
        
        // Draw biomes
        if (ModConfig.showBiomes) {
            this.biomeRenderer.render(guiGraphics, x, y, this.mapWidth, this.mapHeight, 
                this.zoomLevel, this.panX, this.panY);
        }
        
        // Draw structures
        if (ModConfig.showStructures) {
            this.structureRenderer.render(guiGraphics, x, y, this.mapWidth, this.mapHeight, 
                this.zoomLevel, this.panX, this.panY);
        }
        
        // Draw grid
        if (ModConfig.showGridLines) {
            this.renderGrid(guiGraphics, x, y);
        }
        
        // Draw coordinates if enabled
        if (ModConfig.showCoordinates) {
            this.renderCoordinates(guiGraphics, x, y, mouseX, mouseY);
        }
        
        // Draw border
        guiGraphics.drawBorder(x, y, this.mapWidth, this.mapHeight, 0xFFFFFFFF);
    }
    
    private void renderGrid(GuiGraphics guiGraphics, int x, int y) {
        int gridSize = 64 * this.zoomLevel;
        
        // Draw vertical lines
        for (int i = 0; i < this.mapWidth; i += gridSize) {
            guiGraphics.fill(x + i, y, x + i + 1, y + this.mapHeight, 0x33FFFFFF);
        }
        
        // Draw horizontal lines
        for (int i = 0; i < this.mapHeight; i += gridSize) {
            guiGraphics.fill(x, y + i, x + this.mapWidth, y + i + 1, 0x33FFFFFF);
        }
    }
    
    private void renderCoordinates(GuiGraphics guiGraphics, int x, int y, int mouseX, int mouseY) {
        // Calculate world coordinates from mouse position
        if (mouseX >= x && mouseX <= x + this.mapWidth && 
            mouseY >= y && mouseY <= y + this.mapHeight) {
            
            int relativeX = mouseX - x;
            int relativeY = mouseY - y;
            
            // Simple coordinate calculation (will be improved with proper chunk system)
            int worldX = (relativeX - this.mapWidth / 2) * 16 / this.zoomLevel + this.panX;
            int worldZ = (relativeY - this.mapHeight / 2) * 16 / this.zoomLevel + this.panY;
            
            String coords = String.format("X: %d, Z: %d", worldX, worldZ);
            guiGraphics.drawString(null, coords, x, y - 25, 0xFFFFFF);
        }
    }
    
    public void handleZoom(int direction) {
        int oldZoom = this.zoomLevel;
        this.zoomLevel += direction;
        this.zoomLevel = Math.max(1, Math.min(this.zoomLevel, 16));
    }
    
    public void handlePan(int dx, int dy) {
        this.panX -= dx * 16 / this.zoomLevel;
        this.panY -= dy * 16 / this.zoomLevel;
    }
}
