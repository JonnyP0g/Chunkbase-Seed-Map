package com.jonnyp0g.chunkbasemap.config;

/**
 * Configuration class for storing mod settings.
 * This will handle loading and saving user preferences.
 */
public class ModConfig {
    
    // Display toggles
    public static boolean showBiomes = true;
    public static boolean showStructures = true;
    public static boolean showGridLines = false;
    public static boolean showCoordinates = true;
    
    // Map settings
    public static int mapZoom = 1;
    public static int mapPanX = 0;
    public static int mapPanY = 0;
    
    // Overlay appearance
    public static float overlayOpacity = 0.85f;
    public static int mapWidth = 512;
    public static int mapHeight = 512;
    
    public static void init() {
        // TODO: Load config from file
        // For now, using default values
    }
    
    public static void save() {
        // TODO: Save config to file
    }
    
    public static void reset() {
        showBiomes = true;
        showStructures = true;
        showGridLines = false;
        showCoordinates = true;
        mapZoom = 1;
        mapPanX = 0;
        mapPanY = 0;
        overlayOpacity = 0.85f;
    }
}
