package com.trewghil.offshoreplus.biome.carvers;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.carver.CarverConfig;

public class OffshoreCarvers {

    public static final Carver<ProbabilityConfig> TRENCH = register("trench", new TrenchCarver(ProbabilityConfig::deserialize));
    public static final Carver<ProbabilityConfig> TRENCH_CAVE = register("trench_cave", new TrenchCaveCarver(ProbabilityConfig::deserialize));

    public OffshoreCarvers() {

    }

    public static void init() {

    }

    private static <C extends CarverConfig, F extends Carver<C>> F register(String string, F carver) {
        return Registry.register(Registry.CARVER, (String)string, carver);
    }

}
