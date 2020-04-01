package com.trewghil.offshoreplus.item;

import com.trewghil.offshoreplus.OffshorePlus;
import com.trewghil.offshoreplus.block.OffshoreBlocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class OffshoreItems {

    public OffshoreItems() {

    }

    public static void init() {
        Registry.register(Registry.ITEM, OffshorePlus.identify("buoy"), new BlockItem(OffshoreBlocks.BUOY, new Item.Settings().group(ItemGroup.MISC)));
    }

}
