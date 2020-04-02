package com.trewghil.offshoreplus.biome.carvers;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.UnderwaterCaveCarver;
import net.minecraft.world.gen.carver.UnderwaterRavineCarver;

import java.util.BitSet;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public class TrenchCarver extends UnderwaterRavineCarver {

    private final float[] heightToHorizontalStretchFactor = new float[1024];

    public TrenchCarver(Function<Dynamic<?>, ? extends ProbabilityConfig> function) {
        super(function);
    }

    @Override
    public boolean carve(Chunk chunk, Function<BlockPos, Biome> function, Random random, int i, int j, int k, int l, int m, BitSet bitSet, ProbabilityConfig probabilityConfig) {
        int n = (this.getBranchFactor() * 2 - 1) * 16;
        double d = (double) (j * 16 + random.nextInt(16));
        double e = (double) (random.nextInt(random.nextInt(40) + 8) + 20);
        double f = (double) (k * 16 + random.nextInt(16));
        float g = random.nextFloat() * 6.2831855F;
        float h = (random.nextFloat() - 0.5F) * 2.0F / 8.0F;
        double o = 3.0D;
        float p = (random.nextFloat() * 2.5F + random.nextFloat()) * 2.5F; //Width (2.0f)
        int q = n - random.nextInt(n / 4);

        this.carveRavine(chunk, function, random.nextLong(), i, l, m, d, e, f, p, g, h, 0, q, 3.0D, bitSet);
        return true;
    }

    private void carveRavine(Chunk chunk, Function<BlockPos, Biome> posToBiome, long seed, int seaLevel, int mainChunkX, int mainChunkZ, double x, double y, double z, float width, float yaw, float pitch, int branchStartIndex, int branchCount, double yawPitchRatio, BitSet carvingMask) {
        Random random = new Random(seed);
        float f = 1.0F;

        for (int i = 0; i < 256; ++i) {
            if (i == 0 || random.nextInt(3) == 0) {
                f = 1.0F + random.nextFloat() * random.nextFloat();
            }

            this.heightToHorizontalStretchFactor[i] = f * f;
        }

        float g = 0.0F;
        float h = 0.0F;

        for (int j = branchStartIndex; j < branchCount; ++j) {
            double d = 1.5D + (double) (MathHelper.sin((float) j * 3.1415927F / (float) branchCount) * width);
            double e = d * yawPitchRatio;
            d *= (double) random.nextFloat() * 0.25D + 0.75D;
            e *= (double) random.nextFloat() * 0.25D + 0.75D;
            float k = MathHelper.cos(pitch);
            float l = MathHelper.sin(pitch);
            x += (double) (MathHelper.cos(yaw) * k);
            y += (double) l;
            z += (double) (MathHelper.sin(yaw) * k);
            pitch *= 0.7F;
            pitch += h * 0.05F;
            yaw += g * 0.05F;
            h *= 0.8F;
            g *= 0.5F;
            h += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
            g += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
            if (random.nextInt(4) != 0) {
                if (!this.canCarveBranch(mainChunkX, mainChunkZ, x, z, j, branchCount, width)) {
                    return;
                }

                this.carveRegion(chunk, posToBiome, seed, seaLevel, mainChunkX, mainChunkZ, x, y, z, d, e, carvingMask);
            }
        }
    }

    @Override
    protected boolean carveAtPoint(Chunk chunk, Function<BlockPos, Biome> posToBiome, BitSet carvingMask, Random random, BlockPos.Mutable mutable, BlockPos.Mutable mutable2, BlockPos.Mutable mutable3, int seaLevel, int mainChunkX, int mainChunkZ, int x, int z, int relativeX, int y, int relativeZ, AtomicBoolean foundSurface) {
        return TrenchCaveCarver.carveAtPoint(this, chunk, carvingMask, random, mutable, seaLevel, mainChunkX, mainChunkZ, x, z, relativeX, y, relativeZ);
    }

    protected boolean isPositionExcluded(double scaledRelativeX, double scaledRelativeY, double scaledRelativeZ, int y) {
        return (scaledRelativeX * scaledRelativeX + scaledRelativeZ * scaledRelativeZ) * (double)this.heightToHorizontalStretchFactor[y - 1] + scaledRelativeY * scaledRelativeY / 6.0D >= 1.0D;
    }
}
