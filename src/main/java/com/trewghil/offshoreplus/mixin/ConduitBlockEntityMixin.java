package com.trewghil.offshoreplus.mixin;

import com.trewghil.offshoreplus.entity.effect.OffshoreStatusEffects;
import net.minecraft.block.entity.ConduitBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.List;

@Mixin(ConduitBlockEntity.class)
public class ConduitBlockEntityMixin {

    @Inject(method = "givePlayersEffects", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void removePressure(CallbackInfo info, int i, int j, int k, int l, int m, Box box, List<PlayerEntity> list) {
        for(PlayerEntity player : list) {
            player.removeStatusEffect(OffshoreStatusEffects.PRESSURIZED);
        }
    }
}
