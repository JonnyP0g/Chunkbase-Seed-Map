package com.jonnyp0g.chunkbasemap.client.ui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import com.jonnyp0g.chunkbasemap.config.ModConfig;

/**
 * UI panel for toggling map display options.
 */
public class MapUIPanel extends ClickableWidget {
    
    private int panelX;
    private int panelY;
    private int panelWidth;
    private int panelHeight;
    
    public MapUIPanel(int x, int y, int width, int height) {
        super(x, y, width, height, Text.literal("Map Options"));
        this.panelX = x;
        this.panelY = y;
        this.panelWidth = width;
        this.panelHeight = height;
    }
    
    @Override
    protected void renderWidget(GuiGraphics guiGraphics) {
        // Draw panel background
        guiGraphics.fill(this.panelX, this.panelY, this.panelX + this.panelWidth, 
            this.panelY + this.panelHeight, 0xCC1F1F1F);
        guiGraphics.drawBorder(this.panelX, this.panelY, this.panelWidth, 
            this.panelHeight, 0xFFAAAAAA);
        
        // Draw title
        guiGraphics.drawString(null, "Options", this.panelX + 5, this.panelY + 5, 0xFFFFFF);
        
        // Draw toggle labels (checkboxes would be added as child widgets)
        int yOffset = this.panelY + 25;
        int lineHeight = 20;
        
        guiGraphics.drawString(null, "Biomes", this.panelX + 5, yOffset, 
            ModConfig.showBiomes ? 0xFF00FF00 : 0xFF808080);
        yOffset += lineHeight;
        
        guiGraphics.drawString(null, "Structures", this.panelX + 5, yOffset, 
            ModConfig.showStructures ? 0xFF00FF00 : 0xFF808080);
        yOffset += lineHeight;
        
        guiGraphics.drawString(null, "Grid", this.panelX + 5, yOffset, 
            ModConfig.showGridLines ? 0xFF00FF00 : 0xFF808080);
        yOffset += lineHeight;
        
        guiGraphics.drawString(null, "Coordinates", this.panelX + 5, yOffset, 
            ModConfig.showCoordinates ? 0xFF00FF00 : 0xFF808080);
    }
    
    @Override
    public void onClick(double mouseX, double mouseY) {
        // Handle clicks on the panel
        int yOffset = this.panelY + 25;
        int lineHeight = 20;
        int clickY = (int)mouseY;
        
        if (clickY >= yOffset && clickY < yOffset + lineHeight) {
            ModConfig.showBiomes = !ModConfig.showBiomes;
        } else if (clickY >= yOffset + lineHeight && clickY < yOffset + lineHeight * 2) {
            ModConfig.showStructures = !ModConfig.showStructures;
        } else if (clickY >= yOffset + lineHeight * 2 && clickY < yOffset + lineHeight * 3) {
            ModConfig.showGridLines = !ModConfig.showGridLines;
        } else if (clickY >= yOffset + lineHeight * 3 && clickY < yOffset + lineHeight * 4) {
            ModConfig.showCoordinates = !ModConfig.showCoordinates;
        }
    }
    
    @Override
    public void onRelease(double mouseX, double mouseY) {
        // No-op
    }
}
