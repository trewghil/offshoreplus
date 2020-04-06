package com.trewghil.offshoreplus.item;

import dev.emi.trinkets.api.ITrinket;
import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.Slots;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Random;

public class StabilizationAmuletItem extends Item implements ITrinket {

    public StabilizationAmuletItem(Settings settings) {
        super(settings);

        DispenserBlock.registerBehavior(this, TRINKET_DISPENSER_BEHAVIOR);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        return ITrinket.equipTrinket(player, hand);
    }

    @Override
    public boolean canWearInSlot(String group, String slot) {
        return group.equals(SlotGroups.CHEST) && slot.equals(Slots.NECKLACE);
    }

    @Override
    public void tick(PlayerEntity player, ItemStack stack) {
        if (!player.getEntityWorld().isClient) {
            stack.damage(1, new Random(), (ServerPlayerEntity)player);

            if(stack.getDamage() >= stack.getMaxDamage()) {
                stack.decrement(1);
            }
        }
    }
}
