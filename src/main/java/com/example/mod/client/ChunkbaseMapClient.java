package com.example.mod.client;

import com.example.mod.ChunkbaseSeedMapMod;
import com.example.mod.client.config.ModConfig;
import com.example.mod.client.input.KeyInputHandler;
import net.fabricmc.api.ClientModInitializer;

public class ChunkbaseMapClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModConfig.load();
        KeyInputHandler.register();
        ChunkbaseSeedMapMod.LOGGER.info("Chunkbase Seed Map client initialized");
    }
}
