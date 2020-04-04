package com.trewghil.offshoreplus.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.entity.Entity;

public class MessageBottleModel<T extends Entity> extends CompositeEntityModel<T> {
	private final ModelPart bottle;

	public MessageBottleModel() {
		this.textureWidth = 32;
		this.textureHeight = 32;

		bottle = new ModelPart(this, 0, 0);

		bottle.setPivot(0.0F, -3F, 0.0F);

		bottle.addCuboid("body", -2.0F, -5F, -2.0F, 4, 8, 4, 0.0f, 0, 0);
		bottle.addCuboid("neck", -1.0F, -8F, -1.0F, 2, 3, 2, 0.0f, 0, 12);
		bottle.addCuboid("cork", -0.5F, -8.25F, -0.5F, 1, 1, 1, 0.0f, 8, 12);
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch) {
		this.bottle.yaw = headYaw;
		this.bottle.pitch = headPitch;
	}

	@Override
	public Iterable<ModelPart> getParts() {
		return ImmutableList.of(bottle);
	}
}