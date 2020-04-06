package com.trewghil.offshoreplus.client.render.entity;

import com.trewghil.offshoreplus.OffshorePlus;
import com.trewghil.offshoreplus.client.render.entity.model.MessageBottleModel;
import com.trewghil.offshoreplus.entity.MessageBottleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BatEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.BatEntityModel;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class MessageBottleEntityRenderer extends MobEntityRenderer<MessageBottleEntity, MessageBottleModel> {

    private static final Identifier SKIN = OffshorePlus.identify("textures/entity/message_bottle.png");

    public MessageBottleEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new MessageBottleModel(), 0.25F);
    }

    @Override
    public Identifier getTexture(MessageBottleEntity messageBottleEntity) {
        return SKIN;
    }

    @Override
    protected void scale(MessageBottleEntity entity, MatrixStack matrices, float tickDelta) {
        matrices.scale(1.0f, 1.0f, 1.0f);
    }

    @Override
    protected void setupTransforms(MessageBottleEntity entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        super.setupTransforms(entity, matrices, animationProgress, bodyYaw, tickDelta);
    }

    /*
    private static final Identifier SKIN = OffshorePlus.identify("textures/entity/message_bottle.png");
    private final MessageBottleModel<MessageBottleEntity> model = new MessageBottleModel<>();

    public MessageBottleEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
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
    */
}
