package com.trewghil.offshoreplus;

import com.trewghil.offshoreplus.biome.OffshoreBiomes;
import com.trewghil.offshoreplus.biome.carvers.OffshoreCarvers;
import com.trewghil.offshoreplus.block.OffshoreBlocks;
import com.trewghil.offshoreplus.entity.effect.OffshoreStatusEffects;
import com.trewghil.offshoreplus.feature.OffshoreFeatures;
import com.trewghil.offshoreplus.item.OffshoreItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class OffshorePlus implements ModInitializer {

	public static final String MOD_ID = "offshoreplus";

	public static Identifier identify(String id) {
		return new Identifier(MOD_ID, id);
	}

	@Override
	public void onInitialize() {
		OffshoreBlocks.init();
		OffshoreItems.init();

		OffshoreFeatures.init();
		OffshoreBiomes.init();
		OffshoreCarvers.init();

		OffshoreStatusEffects.init();

		updateBiomes();
	}

	private void updateBiomes() {
		Feature.STRUCTURES.put("ocean_village", OffshoreFeatures.OCEAN_VILLAGE_FEATURE);

		Registry.BIOME.stream().forEach(biome -> {
			biome.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, OffshoreFeatures.OCEAN_VILLAGE_FEATURE.configure(new DefaultFeatureConfig()));

			biome.addStructureFeature(OffshoreFeatures.OCEAN_VILLAGE_FEATURE.configure(new DefaultFeatureConfig()));
		});

		Registry.BIOME.stream().filter(biome ->
				biome != OffshoreBiomes.FRACTURED_SEA
		).forEach(biome -> {
			biome.addCarver(GenerationStep.Carver.LIQUID, Biome.configureCarver(OffshoreCarvers.TRENCH_CAVE, new ProbabilityConfig(0.0f)));
			biome.addCarver(GenerationStep.Carver.LIQUID, Biome.configureCarver(OffshoreCarvers.TRENCH, new ProbabilityConfig(0.0f)));
		});
	}
}
