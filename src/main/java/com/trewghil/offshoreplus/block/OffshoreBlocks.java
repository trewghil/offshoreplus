package com.trewghil.offshoreplus.block;

import com.trewghil.offshoreplus.OffshorePlus;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.util.registry.Registry;

public class OffshoreBlocks {

    public static final BuoyBlock BUOY = new BuoyBlock(Block.Settings.of(Material.WOOL).strength(1.0f, 1.0f));
    public static final VoragiteOreBlock VORAGITE_ORE = new VoragiteOreBlock(FabricBlockSettings.of(Material.STONE).strength(3.0f, 3.0f).breakByTool(FabricToolTags.PICKAXES, 3).build());

    public OffshoreBlocks() {

    }

    public static void init() {
        Registry.register(Registry.BLOCK, OffshorePlus.identify("buoy"), BUOY);
        Registry.register(Registry.BLOCK, OffshorePlus.identify("voragite_ore"), VORAGITE_ORE);
    }

}
