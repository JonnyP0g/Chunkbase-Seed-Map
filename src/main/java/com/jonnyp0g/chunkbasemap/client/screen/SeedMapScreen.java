package com.jonnyp0g.chunkbasemap.client.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.Text;
import com.jonnyp0g.chunkbasemap.config.ModConfig;
import com.jonnyp0g.chunkbasemap.client.rendering.MapRenderer;
import com.jonnyp0g.chunkbasemap.client.ui.MapUIPanel;

/**
 * Main screen for displaying the Chunkbase-style seed map overlay.
 */
public class SeedMapScreen extends Screen {
    
    private final MinecraftClient client;
    private MapRenderer mapRenderer;
    private MapUIPanel uiPanel;
    
    private int mapX;
    private int mapY;
    private int mapWidth;
    private int mapHeight;
    
    private long worldSeed;
    
    public SeedMapScreen() {
        super(Text.literal("Seed Map"));
        this.client = MinecraftClient.getInstance();
    }
    
    @Override
    protected void init() {
        // Get world seed
        ClientWorld world = this.client.world;
        if (world != null) {
            this.worldSeed = world.getSeed();
        }
        
        // Calculate map dimensions
        this.mapWidth = Math.min(ModConfig.mapWidth, this.width - 40);
        this.mapHeight = Math.min(ModConfig.mapHeight, this.height - 40);
        
        // Center the map
        this.mapX = (this.width - this.mapWidth) / 2;
        this.mapY = (this.height - this.mapHeight) / 2;
        
        // Initialize map renderer
        this.mapRenderer = new MapRenderer(this.mapWidth, this.mapHeight, this.worldSeed);
        
        // Initialize UI panel
        this.uiPanel = new MapUIPanel(
            this.mapX + this.mapWidth + 10,
            this.mapY,
            150,
            this.mapHeight
        );
        
        this.addDrawableChild(this.uiPanel);
    }
    
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // Draw semi-transparent background
        this.fillGradient(guiGraphics, 0, 0, this.width, this.height, 
            0x00000000, 0x80000000);
        
        // Draw map
        this.mapRenderer.render(guiGraphics, this.mapX, this.mapY, mouseX, mouseY);
        
        // Draw UI panel
        this.uiPanel.render(guiGraphics, mouseX, mouseY, partialTick);
        
        // Draw title
        guiGraphics.drawCenteredString(this.textRenderer, this.title, 
            this.width / 2, 10, 0xFFFFFF);
        
        // Draw seed info
        String seedText = "Seed: " + this.worldSeed;
        guiGraphics.drawString(this.textRenderer, seedText, 
            this.mapX, this.mapY - 15, 0xAAAAAA);
        
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }
    
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        // Handle zoom with mouse wheel
        if (this.isMouseOverMap(mouseX, mouseY)) {
            this.mapRenderer.handleZoom((int)verticalAmount);
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }
    
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        // Handle panning with mouse drag
        if (button == 0 && this.isMouseOverMap(mouseX, mouseY)) {
            this.mapRenderer.handlePan((int)dragX, (int)dragY);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }
    
    private boolean isMouseOverMap(double mouseX, double mouseY) {
        return mouseX >= this.mapX && mouseX <= this.mapX + this.mapWidth &&
               mouseY >= this.mapY && mouseY <= this.mapY + this.mapHeight;
    }
    
    @Override
    public void close() {
        this.client.setScreen(null);
        ModConfig.save();
    }
    
    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
    
    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
