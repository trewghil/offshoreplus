package com.trewghil.offshoreplus.client.render.entity;

import com.trewghil.offshoreplus.entity.OffshoreEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class OffshoreEntityRenderers {

    public OffshoreEntityRenderers() {

    }

    public static void init() {
        EntityRendererRegistry.INSTANCE.register(OffshoreEntities.MESSAGE_BOTTLE, (entityRenderDispatcher, context) -> new MessageBottleEntityRenderer(entityRenderDispatcher));
    }
}
