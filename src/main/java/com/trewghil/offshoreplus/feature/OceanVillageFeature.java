package com.trewghil.offshoreplus.feature;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import com.mojang.datafixers.util.Pair;
import com.trewghil.offshoreplus.OffshorePlus;
import com.trewghil.offshoreplus.feature.generators.OceanVillageFenceGenerator;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.StructureBlockMode;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.*;
import net.minecraft.structure.pool.*;
import net.minecraft.structure.processor.*;
import net.minecraft.structure.rule.AlwaysTrueRuleTest;
import net.minecraft.structure.rule.RandomBlockMatchRuleTest;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.DynamicDeserializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.AbstractTempleFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.Collections;
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
        this.boundingBox.encompass(fenceGenerator.getBoundingBox());
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
                                Pair.of(new VillagePoolElement("offshoreplus:ocean_village/dock_1", WET_WOOD_PROCESSOR), 6),
                                Pair.of(new VillagePoolElement("offshoreplus:ocean_village/dock_2", WET_WOOD_PROCESSOR), 6),
                                Pair.of(new VillagePoolElement("offshoreplus:ocean_village/dock_3", WET_WOOD_PROCESSOR), 6)
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

//SHOUTOUT TO DRAYLAR! :)
class VillagePoolElement extends StructurePoolElement {

    private final Identifier location;
    private final ImmutableList<StructureProcessor> processors;

    VillagePoolElement(String loc) {
        super(StructurePool.Projection.RIGID);

        this.location = new Identifier(loc);
        this.processors = ImmutableList.of();
    }

    VillagePoolElement(String loc, List<StructureProcessor> processors) {
        super(StructurePool.Projection.RIGID);

        this.location = new Identifier(loc);
        this.processors = ImmutableList.copyOf(processors);
    }

    public VillagePoolElement(Dynamic<?> dynamic) {
        super(dynamic);

        this.location = new Identifier(dynamic.get("location").asString(""));
        this.processors = ImmutableList.copyOf(dynamic.get("processors").asList((dynamicx) -> {
            return (StructureProcessor) DynamicDeserializer.deserialize(dynamicx, Registry.STRUCTURE_PROCESSOR, "processor_type", NopStructureProcessor.INSTANCE);
        }));
    }

    @Override
    public boolean generate(StructureManager structureManager, IWorld world, ChunkGenerator<?> chunkGenerator, BlockPos pos, BlockRotation rotation, BlockBox bounds, Random random) {
        Structure structure = structureManager.getStructureOrBlank(this.location);
        StructurePlacementData structurePlacementData = this.getDefaultPlacementData(rotation, bounds);

        // generate
        if (!structure.method_15172(world, pos, structurePlacementData, 18)) {
            return false;
        } else {
            List<Structure.StructureBlockInfo> list_1 = structure.method_16445(pos, structurePlacementData, Blocks.STRUCTURE_BLOCK);
            Iterator var6 = list_1.iterator();

            while(var6.hasNext()) {
                Structure.StructureBlockInfo blockInfo = (Structure.StructureBlockInfo)var6.next();
                if (blockInfo.tag != null) {
                    StructureBlockMode structureBlockMode_1 = StructureBlockMode.valueOf(blockInfo.tag.getString("mode"));
                    if (structureBlockMode_1 == StructureBlockMode.DATA) {
                        method_16756(world, blockInfo, blockInfo.pos, rotation, random, bounds);
                    }
                }
            }

            return true;
        }
    }

    protected StructurePlacementData getDefaultPlacementData(BlockRotation blockRotation, BlockBox blockBox) {
        StructurePlacementData structurePlacementData = new StructurePlacementData();
        structurePlacementData.setBoundingBox(blockBox);
        structurePlacementData.setRotation(blockRotation);
        structurePlacementData.method_15131(true);
        structurePlacementData.setIgnoreEntities(false);
        structurePlacementData.addProcessor(BlockIgnoreStructureProcessor.IGNORE_AIR_AND_STRUCTURE_BLOCKS);
        structurePlacementData.addProcessor(JigsawReplacementStructureProcessor.INSTANCE);
        this.processors.forEach(structurePlacementData::addProcessor);
        this.getProjection().getProcessors().forEach(structurePlacementData::addProcessor);
        return structurePlacementData;
    }

    @Override
    public StructurePoolElementType getType() {
        return OffshoreFeatures.VILLAGE_ELEMENT;
    }

    @Override
    protected <T> Dynamic<T> method_16625(DynamicOps<T> dynamicOps) {
        return new Dynamic(dynamicOps, dynamicOps.createMap(ImmutableMap.of(dynamicOps.createString("location"), dynamicOps.createString(this.location.toString()), dynamicOps.createString("processors"), dynamicOps.createList(this.processors.stream().map((structureProcessor) -> {
            return structureProcessor.method_16771(dynamicOps).getValue();
        })))));
    }

    @Override
    public List<Structure.StructureBlockInfo> getStructureBlockInfos(StructureManager structureManager, BlockPos pos, BlockRotation rotation, Random random) {
        Structure struct = structureManager.getStructureOrBlank(location);
        List<Structure.StructureBlockInfo> list = struct.method_15165(pos, (new StructurePlacementData()).setRotation(rotation), Blocks.JIGSAW, true);
        Collections.shuffle(list, random);
        return list;
    }

    @Override
    public BlockBox getBoundingBox(StructureManager structureManager, BlockPos pos, BlockRotation rotation) {
        Structure structure = structureManager.getStructureOrBlank(this.location);
        return structure.calculateBoundingBox((new StructurePlacementData()).setRotation(rotation), pos);
    }

    @Override
    public void method_16756(IWorld world, Structure.StructureBlockInfo info, BlockPos pos, BlockRotation rotation, Random random, BlockBox blockBox) {
        String metadata = info.tag.getString("metadata");

        switch(metadata) {
            case "op_fish_barrel": {
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);

                BlockEntity blockEntity = world.getBlockEntity(pos.down());
                if(blockEntity instanceof BarrelBlockEntity) {
                    ((BarrelBlockEntity) blockEntity).setLootTable(LootTables.VILLAGE_FISHER_CHEST, random.nextLong());
                }

                break;
            }

            case "op_food_chest": {
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);

                BlockEntity blockEntity = world.getBlockEntity(pos.down());
                if (blockEntity instanceof ChestBlockEntity) {
                    ((ChestBlockEntity) blockEntity).setLootTable(LootTables.VILLAGE_BUTCHER_CHEST, random.nextLong());
                }

                break;
            }

            case "op_weapon_chest": {
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);

                BlockEntity blockEntity = world.getBlockEntity(pos.down());
                if (blockEntity instanceof ChestBlockEntity) {
                    ((ChestBlockEntity) blockEntity).setLootTable(LootTables.VILLAGE_WEAPONSMITH_CHEST, random.nextLong());
                }

                break;
            }

            case "op_villager_block": {
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                break;
            }

            case "op_misc": {
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);

                BlockEntity blockEntity = world.getBlockEntity(pos.down());
                if(blockEntity instanceof BarrelBlockEntity) {
                    ((BarrelBlockEntity) blockEntity).setLootTable(LootTables.VILLAGE_PLAINS_CHEST, random.nextLong());
                }

                break;
            }
        }

        super.method_16756(world, info, pos, rotation, random, blockBox);
    }
}