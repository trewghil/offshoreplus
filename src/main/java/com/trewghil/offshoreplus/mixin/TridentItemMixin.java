package com.trewghil.offshoreplus.mixin;

import com.trewghil.offshoreplus.item.OffshoreItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TridentItem.class)
public class TridentItemMixin extends Item {

    public TridentItemMixin(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.getItem().equals(OffshoreItems.VORAGITE_INGOT);
    }

}
