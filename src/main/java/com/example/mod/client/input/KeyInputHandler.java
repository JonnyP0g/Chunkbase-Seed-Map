package com.example.mod.client.input;

import com.example.mod.client.config.ModConfig;
import com.example.mod.client.screen.SeedMapScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public final class KeyInputHandler {
    private static KeyBinding openMapKeyBinding;

    private KeyInputHandler() {
    }

    public static void register() {
        openMapKeyBinding = KeyBindingHelper.registerKeyBinding(
            new KeyBinding(
                "key.chunkbase-seed-map.open_map",
                InputUtil.Type.KEYSYM,
                ModConfig.getOpenMapKey(),
                "category.chunkbase-seed-map"
            )
        );

        ClientTickEvents.END_CLIENT_TICK.register(KeyInputHandler::onClientTick);
    }

    private static void onClientTick(MinecraftClient client) {
        while (openMapKeyBinding.wasPressed()) {
            if (client.player == null || client.world == null) {
                continue;
            }
            client.setScreen(new SeedMapScreen());
        }
    }
}
