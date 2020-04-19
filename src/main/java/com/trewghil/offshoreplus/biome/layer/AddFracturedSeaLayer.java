package com.trewghil.offshoreplus.biome.layer;

import com.trewghil.offshoreplus.biome.OffshoreBiomes;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.layer.type.SouthEastSamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public enum AddFracturedSeaLayer implements SouthEastSamplingLayer {
    INSTANCE;

    private static final int OCEAN_ID = Registry.BIOME.getRawId(Biomes.OCEAN);
    private static final int DEEP_OCEAN_ID = Registry.BIOME.getRawId(Biomes.DEEP_OCEAN);
    private static final int COLD_OCEAN_ID = Registry.BIOME.getRawId(Biomes.COLD_OCEAN);

    @Override
    public int sample(LayerRandomnessSource context, int se) {
        return context.nextInt(10) == 0 && (se == OCEAN_ID || se == COLD_OCEAN_ID || se == DEEP_OCEAN_ID) ? Registry.BIOME.getRawId(OffshoreBiomes.FRACTURED_SEA) : se;
    }
}