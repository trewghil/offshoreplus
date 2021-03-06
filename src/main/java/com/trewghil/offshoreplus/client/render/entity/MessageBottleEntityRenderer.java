package com.trewghil.offshoreplus.client.render.entity;

import com.trewghil.offshoreplus.OffshorePlus;
import com.trewghil.offshoreplus.client.render.entity.model.MessageBottleModel;
import com.trewghil.offshoreplus.entity.MessageBottleEntity;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;

public class MessageBottleEntityRenderer extends EntityRenderer<MessageBottleEntity> {

    private static final Identifier SKIN = OffshorePlus.identify("textures/entity/message_bottle.png");
    private final MessageBottleModel model = new MessageBottleModel();

    public MessageBottleEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    @Override
    protected int getBlockLight(MessageBottleEntity entity, float tickDelta) {
        BlockPos pos = new BlockPos(entity.getCameraPosVec(tickDelta));

        return LightmapTextureManager.pack(super.getBlockLight(entity, tickDelta), entity.getEntityWorld().getLightLevel(LightType.SKY, pos.up()));
    }

    public void render(MessageBottleEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.scale(-1.0F, -1.0F, 1.0F);
        this.model.setAngles(entity, 0.0F, 0.0F, 0.0F, entity.yaw, entity.pitch);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.getLayer(SKIN));
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.pop();
        super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(MessageBottleEntity entity) {
        return SKIN;
    }

}
