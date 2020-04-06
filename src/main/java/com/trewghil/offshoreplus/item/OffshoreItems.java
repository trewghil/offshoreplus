package com.trewghil.offshoreplus.item;

import com.trewghil.offshoreplus.OffshorePlus;
import com.trewghil.offshoreplus.block.OffshoreBlocks;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class OffshoreItems {

    public static final ItemGroup GENERAL = FabricItemGroupBuilder.create(
            OffshorePlus.identify("offshore_plus_general"))
            .icon(() -> new ItemStack(OffshoreItems.VORAGITE_INGOT))
            .build();

    public static final StabilizationAmuletItem AMULET_OF_STABILIZATION = new StabilizationAmuletItem(new Item.Settings().group(OffshoreItems.GENERAL).maxCount(1).rarity(Rarity.UNCOMMON).maxDamage((60 * 5) * 20));
    public static final Item VORAGITE_INGOT = new Item(new Item.Settings().group(OffshoreItems.GENERAL));

    public OffshoreItems() {

    }

    public static void init() {
        Registry.register(Registry.ITEM, OffshorePlus.identify("buoy"), new BlockItem(OffshoreBlocks.BUOY, new Item.Settings().group(OffshoreItems.GENERAL)));
        Registry.register(Registry.ITEM, OffshorePlus.identify("voragite_ore"), new BlockItem(OffshoreBlocks.VORAGITE_ORE, new Item.Settings().group(OffshoreItems.GENERAL)));

        Registry.register(Registry.ITEM, OffshorePlus.identify("amulet_of_stabilization"), AMULET_OF_STABILIZATION);
        Registry.register(Registry.ITEM, OffshorePlus.identify("voragite_ingot"), VORAGITE_INGOT);
    }

}
