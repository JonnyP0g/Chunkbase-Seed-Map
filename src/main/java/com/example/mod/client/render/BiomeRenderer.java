package com.example.mod.client.render;

public final class BiomeRenderer {
    private static final int[] COLORS = {
        0x556B2F,
        0x228B22,
        0x66CDAA,
        0x2E8B57,
        0x8FBC8F,
        0x9ACD32,
        0xBDB76B,
        0xCD853F
    };

    public int getBiomeColor(long seed, int x, int z) {
        long mixed = seed;
        mixed ^= (long) x * 341873128712L;
        mixed ^= (long) z * 132897987541L;
        int index = (int) Math.floorMod(mixed, COLORS.length);
        return COLORS[index];
    }
}
