package com.trewghil.offshoreplus.feature.generators;

import com.trewghil.offshoreplus.feature.OffshoreFeatures;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePieceWithDimensions;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.IWorld;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class OceanVillageFenceGenerator extends StructurePieceWithDimensions {

    private List<BlockPos> knownAirBlocks = new ArrayList<>();
    private List<BlockPos> knownEdgeBlocks = new ArrayList<>();

    public OceanVillageFenceGenerator(Random random, BlockBox structureBox) {
        super(OffshoreFeatures.OCEAN_VILLAGE_FENCE, random, structureBox.minX, 66, structureBox.minZ, structureBox.getBlockCountX(), 0, structureBox.getBlockCountZ());
    }

    public OceanVillageFenceGenerator(StructureManager structureManager, CompoundTag compoundTag) {
        super(OffshoreFeatures.OCEAN_VILLAGE_FENCE, compoundTag);
    }

    @Override
    public boolean generate(IWorld world, ChunkGenerator<?> generator, Random random, BlockBox box, ChunkPos pos) {

        int minX = pos.getStartX(), minY = 0, minZ = pos.getStartZ();
        Chunk currentChunk = world.getChunk(pos.getCenterBlockPos());

        for(int o = minY; o <= this.height; ++o) {
            for(int x = minX; x <= pos.getEndX(); ++x) {
                for(int z = minZ; z <= pos.getEndZ(); ++z) {

                    BlockPos currentPos = new BlockPos(x, this.applyYTransform(o), z);

                    if(!world.getBlockState(currentPos).isAir()) {
                        if(isEdgeBlock(currentPos, world, false)) {
                            knownEdgeBlocks.add(currentPos);

                            world.setBlockState(new BlockPos(currentPos.up()), Blocks.OAK_FENCE.getDefaultState(), 2);
                        }
                    }
                }
            }
        }

        Iterator<BlockPos> i = knownEdgeBlocks.iterator();
        while(i.hasNext()) {
            BlockPos posToCheck = i.next();
            currentChunk.markBlockForPostProcessing(posToCheck.up());

            if(!isEdgeBlock(posToCheck, world, true)) {
                world.setBlockState(posToCheck.up(), Blocks.AIR.getDefaultState(), 2);

                i.remove();
            }
        }

        return true;
    }

    private boolean isEdgeBlock(BlockPos pos, IWorld world, boolean check) {

        BlockState block = world.getBlockState(pos);
        BlockState above = world.getBlockState(pos.up());

        if(block.getBlock() != Blocks.OAK_PLANKS && block.getBlock() != Blocks.SPRUCE_PLANKS) return false;

        if(!check) {
            if (block.isAir()) {
                knownAirBlocks.add(pos);
                return false;
            }
        }

        boolean isTouchingAir = false;

        found:
        for(int i = pos.getX() - 1; i <= pos.getX() + 1; ++i) {
            for(int j = pos.getZ() - 1; j <= pos.getZ() + 1; ++j) {

                BlockPos cur = new BlockPos(i, pos.getY(), j);
                if(cur.equals(pos)) continue;

                if(knownAirBlocks.contains(cur)) {
                    isTouchingAir = true;
                    break found;
                }

                if(world.getBlockState(cur).isAir()) {
                    isTouchingAir = true;
                    break found;
                }
            }
        }

        if(check) return isTouchingAir;
        return above.isAir() && isTouchingAir;
    }
}