package com.trewghil.offshoreplus.mixin;

import com.trewghil.offshoreplus.biome.OffshoreBiomes;
import com.trewghil.offshoreplus.entity.effect.OffshoreStatusEffect;
import com.trewghil.offshoreplus.entity.effect.OffshoreStatusEffects;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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

        if(tickCount == 20) {

            if (((PlayerEntity) (Object) this).getBlockPos().getY() < 40) {

                Biome biome = ((PlayerEntity) (Object) this).getEntityWorld().getBiome(((PlayerEntity) (Object) this).getBlockPos());
                BlockState block = ((PlayerEntity) (Object) this).getBlockState();

                if (biome == OffshoreBiomes.FRACTURED_SEA && (block.getBlock() == Blocks.WATER || block.getBlock() == Blocks.BUBBLE_COLUMN) && !((PlayerEntity) (Object) this).hasStatusEffect(StatusEffects.CONDUIT_POWER)) {

                    ((PlayerEntity) (Object) this).addStatusEffect(new StatusEffectInstance(OffshoreStatusEffects.PRESSURIZED, 420, 0));
                }
            } else {
                ((PlayerEntity) (Object) this).removeStatusEffect(OffshoreStatusEffects.PRESSURIZED);
            }

            tickCount = 0;
        } else {

            ++tickCount;
        }
    }
}
