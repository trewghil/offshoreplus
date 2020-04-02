package com.trewghil.offshoreplus.biome;

import com.trewghil.offshoreplus.OffshorePlus;
import net.fabricmc.fabric.api.biomes.v1.OverworldBiomes;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;

public class OffshoreBiomes {

    public static final Biome DEEP_SEA_TRENCH = Registry.register(Registry.BIOME, OffshorePlus.identify("deep_sea_trench"), new DeepSeaTrenchBiome());

    public OffshoreBiomes() {

    }

    public static void init() {
        OverworldBiomes.addBiomeVariant(Biomes.OCEAN, OffshoreBiomes.DEEP_SEA_TRENCH, 0.33);
    }

}
