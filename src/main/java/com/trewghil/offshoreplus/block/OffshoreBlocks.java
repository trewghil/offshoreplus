package com.trewghil.offshoreplus.block;

import com.trewghil.offshoreplus.OffshorePlus;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.util.registry.Registry;

public class OffshoreBlocks {

    public static final BuoyBlock BUOY = new BuoyBlock(Block.Settings.of(Material.WOOL));

    public OffshoreBlocks() {

    }

    public static void init() {
        Registry.register(Registry.BLOCK, OffshorePlus.identify("buoy"), BUOY);
    }

}
