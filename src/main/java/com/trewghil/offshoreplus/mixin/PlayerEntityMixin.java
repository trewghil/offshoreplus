package com.trewghil.offshoreplus.mixin;

import com.trewghil.offshoreplus.biome.OffshoreBiomes;
import com.trewghil.offshoreplus.entity.effect.OffshoreStatusEffects;
import com.trewghil.offshoreplus.item.OffshoreItems;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Unique
    private int tickCount = 0;

    @Inject(method = "tick", at = @At("HEAD"))
    private void statusEffectListener(CallbackInfo info) {

        PlayerEntity player = ((PlayerEntity) (Object) this);

        if(tickCount == 20) {

            if (player.getBlockPos().getY() < 40) {

                Biome biome = player.getEntityWorld().getBiome(player.getBlockPos());

                if ((biome == OffshoreBiomes.FRACTURED_SEA || biome.getCategory() == Biome.Category.OCEAN || biome.getCategory() == Biome.Category.BEACH) && player.isSubmergedInWater() && !player.hasStatusEffect(StatusEffects.CONDUIT_POWER)) {

                    TrinketComponent trinket = TrinketsApi.getTrinketComponent(player);

                    if(!trinket.getStack("chest:necklace").getItem().equals(OffshoreItems.AMULET_OF_STABILIZATION)) {
                        player.addStatusEffect(new StatusEffectInstance(OffshoreStatusEffects.PRESSURIZED, 420, 0));
                    } else {
                        player.removeStatusEffect(OffshoreStatusEffects.PRESSURIZED);
                    }
                }
            } else {
                player.removeStatusEffect(OffshoreStatusEffects.PRESSURIZED);
            }

            tickCount = 0;
        } else {

            ++tickCount;
        }
    }
}
