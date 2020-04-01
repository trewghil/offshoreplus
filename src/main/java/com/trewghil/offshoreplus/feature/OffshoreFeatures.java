package com.trewghil.offshoreplus.feature;

import com.trewghil.offshoreplus.OffshorePlus;
import com.trewghil.offshoreplus.feature.generators.OceanVillageFenceGenerator;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class OffshoreFeatures {

    public static final StructureFeature<DefaultFeatureConfig> OCEAN_VILLAGE_FEATURE = Registry.register(
            Registry.FEATURE,
            OffshorePlus.identify("ocean_village_feature"),
            new OceanVillageFeature(DefaultFeatureConfig::deserialize)
    );

    public static final StructureFeature<DefaultFeatureConfig> OCEAN_VILLAGE_STRUCTURE_FEATURE = Registry.register(
            Registry.STRUCTURE_FEATURE,
            OffshorePlus.identify("ocean_village_structure_feature"),
            OCEAN_VILLAGE_FEATURE
    );


    public static final StructurePieceType OCEAN_VILLAGE_PIECE = Registry.register(
            Registry.STRUCTURE_PIECE,
            OffshorePlus.identify("ocean_village_piece"),
            OceanVillagePiece::new
    );
    public static final StructurePieceType OCEAN_VILLAGE_FENCE = Registry.register(
            Registry.STRUCTURE_PIECE,
            OffshorePlus.identify("ocean_village_fence"),
            OceanVillageFenceGenerator::new
    );

    public OffshoreFeatures() {

    }

    public static void init() {

    }

}
