package com.example.mod;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChunkbaseSeedMapMod implements ModInitializer {
    public static final String MOD_ID = "chunkbase-seed-map";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Chunkbase Seed Map initialized");
    }
}
