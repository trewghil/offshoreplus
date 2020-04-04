package com.trewghil.offshoreplus.biome;

import com.trewghil.offshoreplus.OffshorePlus;
import net.fabricmc.fabric.api.biomes.v1.OverworldBiomes;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;

public class OffshoreBiomes {

    public static final Biome FRACTURED_SEA = Registry.register(Registry.BIOME, OffshorePlus.identify("fractured_sea"), new FracturedSeaBiome());

    public OffshoreBiomes() {

    }

    public static void init() {
        //OverworldBiomes.addContinentalBiome(FRACTURED_SEA, OverworldClimate.TEMPERATE, 2);
        //OverworldBiomes.addContinentalBiome(FRACTURED_SEA, OverworldClimate.COOL, 2);

        //OverworldBiomes.addBiomeVariant(Biomes.OCEAN, FRACTURED_SEA, 0.1f);
        //OverworldBiomes.addBiomeVariant(Biomes.DEEP_OCEAN, FRACTURED_SEA, 0.1f);

        OverworldBiomes.setRiverBiome(FRACTURED_SEA, null);
        OverworldBiomes.addShoreBiome(FRACTURED_SEA, Biomes.BEACH, 1);
    }

}
