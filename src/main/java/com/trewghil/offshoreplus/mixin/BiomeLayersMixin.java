package com.trewghil.offshoreplus.mixin;

import com.trewghil.offshoreplus.biome.layer.AddFracturedSeaLayer;
import net.minecraft.world.biome.layer.BiomeLayers;
import net.minecraft.world.biome.layer.util.LayerFactory;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;
import net.minecraft.world.gen.chunk.OverworldChunkGeneratorConfig;
import net.minecraft.world.level.LevelGeneratorType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.function.LongFunction;

@Mixin(BiomeLayers.class)
public class BiomeLayersMixin {

    @ModifyVariable(
            method = "build(Lnet/minecraft/world/level/LevelGeneratorType;Lnet/minecraft/world/gen/chunk/OverworldChunkGeneratorConfig;Ljava/util/function/LongFunction;)Lnet/minecraft/world/biome/layer/util/LayerFactory;",
            name = "layerFactory4",
            at = @At(
                    value = "INVOKE_ASSIGN",
                    slice = "Lnet/minecraft/world/biome/layer/SetBaseBiomesLayer;<init>(Lnet/minecraft/world/level/LevelGeneratorType;I)V",
                    target = "Lnet/minecraft/world/biome/layer/SetBaseBiomesLayer;create(Lnet/minecraft/world/biome/layer/util/LayerSampleContext;Lnet/minecraft/world/biome/layer/util/LayerFactory;)Lnet/minecraft/world/biome/layer/util/LayerFactory;"
            )
    )
    private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> addBiomeLayer(LayerFactory layerFactory4, LevelGeneratorType generatorType, OverworldChunkGeneratorConfig settings, LongFunction<C> contextProvider) {
        layerFactory4 = AddFracturedSeaLayer.INSTANCE.create(contextProvider.apply(1001L), layerFactory4);

        System.out.println("Fractured Sea biome layer added");

        return layerFactory4;
    }
}