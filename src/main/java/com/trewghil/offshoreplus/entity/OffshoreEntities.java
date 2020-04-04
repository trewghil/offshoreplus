package com.trewghil.offshoreplus.entity;

import com.trewghil.offshoreplus.OffshorePlus;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;

public class OffshoreEntities {

    public static final EntityType<MessageBottleEntity> MESSAGE_BOTTLE =
            Registry.register(
                    Registry.ENTITY_TYPE,
                    OffshorePlus.identify("message_in_a_bottle"),
                    FabricEntityTypeBuilder.<MessageBottleEntity>create(EntityCategory.MISC, MessageBottleEntity::new).size(EntityDimensions.fixed(0.5f, 0.5f)).build()
            );

    public OffshoreEntities() {

    }

    public static void init() {

    }

}
