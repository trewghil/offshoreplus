package com.trewghil.offshoreplus;

import com.trewghil.offshoreplus.biome.OffshoreBiomes;
import com.trewghil.offshoreplus.biome.carvers.OffshoreCarvers;
import com.trewghil.offshoreplus.block.OffshoreBlocks;
import com.trewghil.offshoreplus.entity.OffshoreEntities;
import com.trewghil.offshoreplus.entity.effect.OffshoreStatusEffects;
import com.trewghil.offshoreplus.feature.OffshoreFeatures;
import com.trewghil.offshoreplus.item.OffshoreItems;
import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.Slots;
import dev.emi.trinkets.api.TrinketSlots;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

public class OffshorePlus implements ModInitializer {

	public static final String MOD_ID = "offshoreplus";

	public static Identifier identify(String id) {
		return new Identifier(MOD_ID, id);
	}

	@Override
	public void onInitialize() {
		OffshoreBlocks.init();
		OffshoreItems.init();
		OffshoreEntities.init();

		OffshoreFeatures.init();
		OffshoreBiomes.init();
		OffshoreCarvers.init();

		OffshoreStatusEffects.init();

		TrinketSlots.addSlot(SlotGroups.CHEST, Slots.NECKLACE, new Identifier("trinkets", "textures/item/empty_trinket_slot_necklace.png"));

		updateBiomes();
	}

	private void updateBiomes() {
		//Biomes.BEACH.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, OffshoreFeatures.MESSAGE_BOTTLE_FEATURE.configure(new DefaultFeatureConfig()));

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
