package com.trewghil.offshoreplus.entity;

import com.trewghil.offshoreplus.OffshorePlus;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.network.Packet;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.Random;

public class MessageBottleEntity extends AbstractDecorationEntity {

    public static final Identifier ID = OffshorePlus.identify("message_in_a_bottle_entity");

    public MessageBottleEntity(EntityType<? extends MessageBottleEntity> entityType, World world) {
        super(entityType, world);
    }

    public MessageBottleEntity(World world, double x, double y, double z, float pitch, float yaw) {
        super(OffshoreEntities.MESSAGE_BOTTLE, world);

        Random random = new Random();

        this.setPos(x, y, z);
        //this.setRotation(45*(random.nextFloat() / (random.nextBoolean() ? 1.0f : -1.0f)), 45*(random.nextFloat() / (random.nextBoolean() ? 1.0f : -1.0f)));
        //this.setPos((getBlockPos().getX() + 0.999) * (random.nextDouble() / 1.0f), getBlockPos().getY(), (getBlockPos().getZ() + 0.999) * (random.nextDouble() / 1.0f));

        this.setBoundingBox(new Box(this.getX() - 0.1875D, this.getY() - 0.25D + 0.125D, this.getZ() - 0.1875D, this.getX() + 0.1875D, this.getY() + 0.25D + 0.125D, this.getZ() + 0.1875D));

        if (world.isClient()) {
            this.updateTrackedPosition(this.getX(), this.getY(), this.getZ());
            this.setRotation(this.pitch, this.yaw);
        }
    }

    @Override
    public void updatePosition(double x, double y, double z) {
        this.setPos(x, y, z);
    }

    @Override
    protected void updateAttachmentPosition() {
        this.setPos(this.attachmentPos.getX(), this.attachmentPos.getY(), this.attachmentPos.getZ());
    }

    @Override
    public boolean canStayAttached() {
        return true;
    }

    @Override
    protected float getEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return -0.0625F;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public boolean shouldRender(double distance) {
        return distance < 1024.0D;
    }

    @Override
    public int getWidthPixels() {
        return 4;
    }

    @Override
    public int getHeightPixels() {
        return 12;
    }

    @Override
    public void onBreak(Entity entity) {

    }

    @Override
    public void onPlace() {

    }

    @Override
    public Packet<?> createSpawnPacket() {
        PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());

        packet.writeDouble(getX());
        packet.writeDouble(getY());
        packet.writeDouble(getZ());

        packet.writeFloat(this.pitch);
        packet.writeFloat(this.yaw);

        packet.writeInt(getEntityId());

        return ServerSidePacketRegistry.INSTANCE.toPacket(ID, packet);
    }
}
