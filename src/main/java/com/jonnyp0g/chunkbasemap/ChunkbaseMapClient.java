package com.jonnyp0g.chunkbasemap;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import com.jonnyp0g.chunkbasemap.client.KeyInputHandler;
import com.jonnyp0g.chunkbasemap.config.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChunkbaseMapClient implements ClientModInitializer {
    public static final String MOD_ID = "chunkbase-seed-map";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing Chunkbase Seed Map mod");
        
        // Initialize config
        ModConfig.init();
        
        // Register keybinding
        KeyBindingHelper.registerKeyBinding(KeyInputHandler.OPEN_MAP_KEY);
        
        // Register key input handler
        ClientTickEvents.END_CLIENT_TICK.register(client -> KeyInputHandler.handleInput(client));
        
        LOGGER.info("Chunkbase Seed Map mod initialized successfully");
    }
}
