package com.jonnyp0g.chunkbasemap.client;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import com.jonnyp0g.chunkbasemap.client.screen.SeedMapScreen;

public class KeyInputHandler {
    
    public static KeyBinding OPEN_MAP_KEY;
    
    static {
        // Create keybinding with default key M, category "Chunkbase Map"
        OPEN_MAP_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.chunkbase-seed-map.open_map",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_M,
            "category.chunkbase-seed-map"
        ));
    }
    
    public static void handleInput(MinecraftClient client) {
        if (OPEN_MAP_KEY.wasPressed()) {
            openSeedMapOverlay(client);
        }
    }
    
    private static void openSeedMapOverlay(MinecraftClient client) {
        if (client.world != null && client.player != null) {
            client.setScreen(new SeedMapScreen());
        }
    }
}
