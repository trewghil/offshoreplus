package com.trewghil.offshoreplus.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.util.Pair;
import com.trewghil.offshoreplus.OffshorePlus;
import com.trewghil.offshoreplus.feature.generators.OceanVillageFenceGenerator;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.*;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.structure.processor.RuleStructureProcessor;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorRule;
import net.minecraft.structure.rule.AlwaysTrueRuleTest;
import net.minecraft.structure.rule.RandomBlockMatchRuleTest;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.AbstractTempleFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class OceanVillageFeature extends AbstractTempleFeature<DefaultFeatureConfig> {

    public OceanVillageFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configFactory) {
        super(configFactory);
    }

    @Override
    protected int getSeedModifier() {
        return 0;
    }

    @Override
    public boolean shouldStartAt(BiomeAccess biomeAccess, ChunkGenerator<?> chunkGenerator, Random random, int chunkZ, int i, Biome biome) {
        if(biome.getCategory() == Biome.Category.OCEAN) {
            return super.shouldStartAt(biomeAccess, chunkGenerator, random, chunkZ, i, biome);
        }
        return false;
    }

    @Override
    public StructureStartFactory getStructureStartFactory() {
        return OceanVillageStart::new;
    }

    @Override
    public String getName() {
        return "Ocean Village";
    }

    @Override
    public int getRadius() {
        return 2;
    }
}

class OceanVillageStart extends StructureStart {

    private static final Identifier START = OffshorePlus.identify("start");
    private static final Identifier ANY = OffshorePlus.identify("any");
    private static final Identifier ANY_NO_END = OffshorePlus.identify("any_no_end");
    private static final Identifier TERMINATORS = OffshorePlus.identify("terminators");
    private static final Identifier BUILDINGS = OffshorePlus.identify("buildings");
    private static final Identifier GATHERINGS = OffshorePlus.identify("gatherings");
    private static final Identifier BOAT = OffshorePlus.identify("boat");

    private static final Identifier EMPTY = new Identifier("empty");

    public OceanVillageStart(StructureFeature<?> feature, int chunkX, int chunkZ, BlockBox box, int references, long l) {
        super(feature, chunkX, chunkZ, box, references, l);
    }

    @Override
    public void initialize(ChunkGenerator<?> chunkGenerator, StructureManager structureManager, int x, int z, Biome biome) {
        StructurePoolBasedGenerator.addPieces(START, 9, OceanVillagePiece::new, chunkGenerator, structureManager, new BlockPos(x * 16, 150, z * 16), children, random);
        setBoundingBoxFromChildren();

        OceanVillageFenceGenerator fenceGenerator = new OceanVillageFenceGenerator(this.random, this.boundingBox);
        this.children.add(fenceGenerator);
    }

    @Override
    protected void setBoundingBoxFromChildren() {
        super.setBoundingBoxFromChildren();

        BlockBox box = this.boundingBox;
        box.minX -= 12;
        box = this.boundingBox;
        box.minY -= 12;
        box = this.boundingBox;
        box.minZ -= 12;
        box = this.boundingBox;
        box.maxX += 12;
        box = this.boundingBox;
        box.maxY += 12;
        box = this.boundingBox;
        box.maxZ += 12;
    }

    private static final ImmutableList<StructureProcessor> WET_WOOD_PROCESSOR = ImmutableList.of(
            new RuleStructureProcessor(
                    ImmutableList.of(
                            new StructureProcessorRule(
                                    new RandomBlockMatchRuleTest(Blocks.OAK_PLANKS, 0.4f),
                                    AlwaysTrueRuleTest.INSTANCE,
                                    Blocks.SPRUCE_PLANKS.getDefaultState()
                            )
                    )
            )
    );

    static {
        StructurePoolBasedGenerator.REGISTRY.add(
                new StructurePool(
                        START,
                        EMPTY,
                        ImmutableList.of(
                                Pair.of(new VillagePoolElement("offshoreplus:ocean_village/center", WET_WOOD_PROCESSOR), 1)
                        ),
                        StructurePool.Projection.RIGID
                )
        );
        StructurePoolBasedGenerator.REGISTRY.add(
                new StructurePool(
                        GATHERINGS,
                        EMPTY,
                        ImmutableList.of(
                                Pair.of(new VillagePoolElement("offshoreplus:ocean_village/tavern"), 1),
                                Pair.of(new VillagePoolElement("offshoreplus:ocean_village/market_square"), 1)
                        ),
                        StructurePool.Projection.RIGID
                )
        );

        StructurePoolBasedGenerator.REGISTRY.add(
                new StructurePool(
                        ANY,
                        TERMINATORS,
                        ImmutableList.of(
                                Pair.of(new VillagePoolElement("offshoreplus:ocean_village/plat_1", WET_WOOD_PROCESSOR), 2),
                                Pair.of(new VillagePoolElement("offshoreplus:ocean_village/plat_2", WET_WOOD_PROCESSOR), 1),
                                Pair.of(new VillagePoolElement("offshoreplus:ocean_village/plat_3", WET_WOOD_PROCESSOR), 1),
                                Pair.of(new VillagePoolElement("offshoreplus:ocean_village/plat_4", WET_WOOD_PROCESSOR), 1)
                        ),
                        StructurePool.Projection.RIGID
                )
        );
        StructurePoolBasedGenerator.REGISTRY.add(
                new StructurePool(
                        ANY_NO_END,
                        TERMINATORS,
                        ImmutableList.of(
                                Pair.of(new VillagePoolElement("offshoreplus:ocean_village/plat_1", WET_WOOD_PROCESSOR), 4),
                                Pair.of(new VillagePoolElement("offshoreplus:ocean_village/plat_3", WET_WOOD_PROCESSOR), 1),
                                Pair.of(new VillagePoolElement("offshoreplus:ocean_village/plat_4", WET_WOOD_PROCESSOR), 1)
                        ),
                        StructurePool.Projection.RIGID
                )
        );

        StructurePoolBasedGenerator.REGISTRY.add(
                new StructurePool(
                        TERMINATORS,
                        EMPTY,
                        ImmutableList.of(
                                Pair.of(new VillagePoolElement("offshoreplus:ocean_village/plat_2", WET_WOOD_PROCESSOR), 1),
                                Pair.of(new VillagePoolElement("offshoreplus:ocean_village/dock_1", WET_WOOD_PROCESSOR), 2),
                                Pair.of(new VillagePoolElement("offshoreplus:ocean_village/dock_2", WET_WOOD_PROCESSOR), 4)
                        ),
                        StructurePool.Projection.RIGID
                )
        );

        StructurePoolBasedGenerator.REGISTRY.add(
                new StructurePool(
                        BOAT,
                        EMPTY,
                        ImmutableList.of(
                                Pair.of(new VillagePoolElement("offshoreplus:ocean_village/spruce_boat"), 1),
                                Pair.of(new VillagePoolElement("offshoreplus:ocean_village/building_none"), 1)

                        ),
                        StructurePool.Projection.RIGID
                )
        );

        StructurePoolBasedGenerator.REGISTRY.add(
                new StructurePool(
                        BUILDINGS,
                        EMPTY,
                        ImmutableList.of(
                                Pair.of(new VillagePoolElement("offshoreplus:ocean_village/building_1"), 1),
                                Pair.of(new VillagePoolElement("offshoreplus:ocean_village/building_2"), 1),
                                Pair.of(new VillagePoolElement("offshoreplus:ocean_village/building_3"), 1),
                                //Pair.of(new VillagePoolElement("offshoreplus:ocean_village/building_4"), 1),
                                Pair.of(new VillagePoolElement("offshoreplus:ocean_village/weaponmaker"), 1),
                                Pair.of(new VillagePoolElement("offshoreplus:ocean_village/building_none"), 7)
                        ),
                        StructurePool.Projection.RIGID
                )
        );
    }
}

class OceanVillagePiece extends PoolStructurePiece {

    public OceanVillagePiece(StructureManager manager, CompoundTag tag) {
        super(manager, tag, OffshoreFeatures.OCEAN_VILLAGE_PIECE);
    }

    public OceanVillagePiece(StructureManager structureManager, StructurePoolElement structurePoolElement, BlockPos blockPos, int i, BlockRotation blockRotation, BlockBox blockBox) {
        super(OffshoreFeatures.OCEAN_VILLAGE_PIECE, structureManager, structurePoolElement, blockPos, i, blockRotation, blockBox);
    }
}

class VillagePoolElement extends SinglePoolElement {

    public VillagePoolElement(String location, List<StructureProcessor> processors) {
        super(location, processors);
    }
    public VillagePoolElement(String location) {
        super(location);
    }

    @Override
    public boolean generate(StructureManager structureManager, IWorld world, ChunkGenerator<?> chunkGenerator, BlockPos blockPos, BlockRotation blockRotation, BlockBox blockBox, Random random) {
        Structure structure = structureManager.getStructureOrBlank(this.location);
        StructurePlacementData structurePlacementData = this.method_16616(blockRotation, blockBox);
        if (!structure.method_15172(world, blockPos, structurePlacementData, 18)) {
            return false;
        } else {
            List<Structure.StructureBlockInfo> list = Structure.process(world, blockPos, structurePlacementData, this.method_16614(structureManager, blockPos, blockRotation, false));
            Iterator var11 = list.iterator();

            while(var11.hasNext()) {
                Structure.StructureBlockInfo structureBlockInfo = (Structure.StructureBlockInfo)var11.next();
                this.method_16756(world, structureBlockInfo, blockPos, blockRotation, random, blockBox);
            }

            return true;
        }
    }

    @Override
    public void method_16756(IWorld world, Structure.StructureBlockInfo info, BlockPos pos, BlockRotation rotation, Random random, BlockBox blockBox) {
        String metadata = info.tag.getString("metadata");
        System.out.println(metadata);
        switch(metadata) {
            case "fish_barrel": {
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);

                BlockEntity blockEntity = world.getBlockEntity(pos.down());
                System.out.println(blockEntity.getType());
                if(blockEntity instanceof BarrelBlockEntity) {
                    ((BarrelBlockEntity) blockEntity).setLootTable(LootTables.SIMPLE_DUNGEON_CHEST, random.nextLong());
                }

                break;
            }

            case "end_chest": {
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);

                BlockEntity blockEntity = world.getBlockEntity(pos.down());
                System.out.println(blockEntity.getType());
                if (blockEntity instanceof ChestBlockEntity) {
                    ((ChestBlockEntity) blockEntity).setLootTable(LootTables.SIMPLE_DUNGEON_CHEST, random.nextLong());
                }

                break;
            }
        }

        super.method_16756(world, info, pos, rotation, random, blockBox);
    }
}