package com.trewghil.offshoreplus.item;

import com.trewghil.offshoreplus.OffshorePlus;
import com.trewghil.offshoreplus.block.OffshoreBlocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class OffshoreItems {

    public static final StabilizationAmuletItem AMULET_OF_STABILIZATION = new StabilizationAmuletItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.UNCOMMON).maxDamage((60 * 5) * 20));

    public OffshoreItems() {

    }

    public static void init() {
        Registry.register(Registry.ITEM, OffshorePlus.identify("buoy"), new BlockItem(OffshoreBlocks.BUOY, new Item.Settings().group(ItemGroup.MISC)));
        Registry.register(Registry.ITEM, OffshorePlus.identify("amulet_of_stabilization"), AMULET_OF_STABILIZATION);
    }

}
