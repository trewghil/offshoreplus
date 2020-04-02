package com.trewghil.offshoreplus.entity.effect;

import com.trewghil.offshoreplus.OffshorePlus;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class OffshoreStatusEffects {

    public static OffshoreStatusEffect PRESSURIZED;

    private static StatusEffect register(Identifier id, OffshoreStatusEffect entry) {
        return Registry.register(Registry.STATUS_EFFECT, id, entry);
    }

    public OffshoreStatusEffects() {

    }

    public static void init() {
        PRESSURIZED = (OffshoreStatusEffect) register(OffshorePlus.identify("pressurized"), (new OffshoreStatusEffect(StatusEffectType.HARMFUL, 1180534)));
    }

}
