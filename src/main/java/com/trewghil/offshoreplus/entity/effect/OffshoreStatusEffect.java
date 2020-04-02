package com.trewghil.offshoreplus.entity.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class OffshoreStatusEffect extends StatusEffect {

    public OffshoreStatusEffect(StatusEffectType type, int color) {
        super(type, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (this == OffshoreStatusEffects.PRESSURIZED) {
            entity.damage(DamageSource.MAGIC, 1.0F);
        } else {
            System.out.println(this.toString());
        }

    }
}
