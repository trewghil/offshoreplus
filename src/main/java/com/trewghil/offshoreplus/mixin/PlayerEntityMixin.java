package com.trewghil.offshoreplus.mixin;

import com.trewghil.offshoreplus.biome.OffshoreBiomes;
import com.trewghil.offshoreplus.entity.effect.OffshoreStatusEffects;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void statusEffectListener(CallbackInfo info) {

        if(((PlayerEntity) (Object) this).getBlockPos().getY() < 40) {
            Biome biome = ((PlayerEntity) (Object) this).getEntityWorld().getBiome(((PlayerEntity) (Object) this).getBlockPos());
            BlockState block = ((PlayerEntity) (Object) this).getBlockState();

            if(biome == OffshoreBiomes.FRACTURED_SEA && block.getBlock() == Blocks.WATER) {
                if(!((PlayerEntity) (Object) this).hasStatusEffect(OffshoreStatusEffects.PRESSURIZED)) {
                    ((PlayerEntity) (Object) this).addStatusEffect(new StatusEffectInstance(OffshoreStatusEffects.PRESSURIZED, 7 * 20, 0));
                }
            }
        }
    }
}
