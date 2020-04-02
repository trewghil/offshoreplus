package com.trewghil.offshoreplus.biome.carvers;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.carver.UnderwaterCaveCarver;

import java.util.BitSet;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public class TrenchCaveCarver extends UnderwaterCaveCarver {

    public TrenchCaveCarver(Function<Dynamic<?>, ? extends ProbabilityConfig> configDeserializer) {
        super(configDeserializer);
    }

    @Override
    protected void carveTunnels(Chunk chunk, Function<BlockPos, Biome> postToBiome, long seed, int seaLevel, int mainChunkX, int mainChunkZ, double x, double y, double z, float width, float yaw, float pitch, int branchStartIndex, int branchCount, double yawPitchRatio, BitSet carvingMask) {
        super.carveTunnels(chunk, postToBiome, seed, seaLevel, mainChunkX, mainChunkZ, x, y, z, 4.0f, yaw, pitch, branchStartIndex, branchCount, yawPitchRatio, carvingMask);
    }

    @Override
    protected boolean carveAtPoint(Chunk chunk, Function<BlockPos, Biome> posToBiome, BitSet carvingMask, Random random, BlockPos.Mutable mutable, BlockPos.Mutable mutable2, BlockPos.Mutable mutable3, int seaLevel, int mainChunkX, int mainChunkZ, int x, int z, int relativeX, int y, int relativeZ, AtomicBoolean foundSurface) {
        return carveAtPoint(this, chunk, carvingMask, random, mutable, seaLevel, mainChunkX, mainChunkZ, x, z, relativeX, y, relativeZ);
    }

    protected static boolean carveAtPoint(Carver<?> carver, Chunk chunk, BitSet mask, Random random, BlockPos.Mutable pos, int seaLevel, int mainChunkX, int mainChunkZ, int x, int z, int relativeX, int y, int relativeZ) {
        if (y >= seaLevel) {
            return false;
        } else {
            int i = relativeX | relativeZ << 4 | y << 8;
            if (mask.get(i)) {
                return false;
            } else {
                mask.set(i);
                pos.set(x, y, z);
                //BlockState blockState = chunk.getBlockState(pos);
                if (false) { //!carver.canAlwaysCarveBlock(blockState)
                    return false;
                } else if (y == 4) {
                    float f = random.nextFloat();
                    if ((double)f < 0.25D) {
                        chunk.setBlockState(pos, Blocks.MAGMA_BLOCK.getDefaultState(), false);
                        chunk.getBlockTickScheduler().schedule(pos, Blocks.MAGMA_BLOCK, 0);
                    } else {
                        chunk.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState(), false);
                    }

                    return true;
                } else if (y < 4) {
                    chunk.setBlockState(pos, Blocks.LAVA.getDefaultState(), false);
                    return false;
                } else {
                    boolean bl = false;
                    Iterator var16 = Direction.Type.HORIZONTAL.iterator();

                    while(var16.hasNext()) {
                        Direction direction = (Direction)var16.next();
                        int j = x + direction.getOffsetX();
                        int k = z + direction.getOffsetZ();
                        if (j >> 4 != mainChunkX || k >> 4 != mainChunkZ || chunk.getBlockState(pos.set(j, y, k)).isAir()) {
                            chunk.setBlockState(pos, WATER.getBlockState(), false);
                            chunk.getFluidTickScheduler().schedule(pos, WATER.getFluid(), 0);
                            bl = true;
                            break;
                        }
                    }

                    pos.set(x, y, z);
                    if (!bl) {
                        chunk.setBlockState(pos, WATER.getBlockState(), false);
                        return true;
                    } else {
                        return true;
                    }
                }
            }
        }
    }

    @Override
    protected boolean canAlwaysCarveBlock(BlockState state) {
        return this.alwaysCarvableBlocks.contains(state.getBlock());
    }
}
