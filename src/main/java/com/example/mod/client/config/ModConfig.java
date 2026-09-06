package com.example.mod.client.config;

import com.example.mod.ChunkbaseSeedMapMod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("chunkbase-seed-map.json");

    private static int openMapKey = GLFW.GLFW_KEY_M;
    private static boolean showBiomes = true;
    private static boolean showStructures = true;
    private static boolean showGrid = true;
    private static boolean showCoordinates = true;

    private ModConfig() {
    }

    public static void load() {
        if (!Files.exists(CONFIG_PATH)) {
            save();
            return;
        }

        try {
            JsonObject root = GSON.fromJson(Files.readString(CONFIG_PATH), JsonObject.class);
            if (root == null) {
                save();
                return;
            }

            openMapKey = getInt(root, "openMapKey", openMapKey);
            showBiomes = getBoolean(root, "showBiomes", showBiomes);
            showStructures = getBoolean(root, "showStructures", showStructures);
            showGrid = getBoolean(root, "showGrid", showGrid);
            showCoordinates = getBoolean(root, "showCoordinates", showCoordinates);
        } catch (IOException ex) {
            ChunkbaseSeedMapMod.LOGGER.warn("Failed to read config, using defaults", ex);
        }
    }

    public static void save() {
        JsonObject root = new JsonObject();
        root.addProperty("openMapKey", openMapKey);
        root.addProperty("showBiomes", showBiomes);
        root.addProperty("showStructures", showStructures);
        root.addProperty("showGrid", showGrid);
        root.addProperty("showCoordinates", showCoordinates);

        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            Files.writeString(CONFIG_PATH, GSON.toJson(root));
        } catch (IOException ex) {
            ChunkbaseSeedMapMod.LOGGER.warn("Failed to write config", ex);
        }
    }

    private static boolean getBoolean(JsonObject root, String key, boolean fallback) {
        return root.has(key) ? root.get(key).getAsBoolean() : fallback;
    }

    private static int getInt(JsonObject root, String key, int fallback) {
        return root.has(key) ? root.get(key).getAsInt() : fallback;
    }

    public static int getOpenMapKey() {
        return openMapKey;
    }

    public static boolean isShowBiomes() {
        return showBiomes;
    }

    public static void setShowBiomes(boolean enabled) {
        showBiomes = enabled;
    }

    public static boolean isShowStructures() {
        return showStructures;
    }

    public static void setShowStructures(boolean enabled) {
        showStructures = enabled;
    }

    public static boolean isShowGrid() {
        return showGrid;
    }

    public static void setShowGrid(boolean enabled) {
        showGrid = enabled;
    }

    public static boolean isShowCoordinates() {
        return showCoordinates;
    }

    public static void setShowCoordinates(boolean enabled) {
        showCoordinates = enabled;
    }
}
