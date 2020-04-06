package com.trewghil.offshoreplus.entity;

import com.trewghil.offshoreplus.OffshorePlus;
import com.trewghil.offshoreplus.util.ImplementedInventory;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Random;

public class MessageBottleEntity extends MobEntity implements ImplementedInventory {

    public static final Identifier ID = OffshorePlus.identify("message_in_a_bottle_entity");
    private final DefaultedList<ItemStack> itemStacks = DefaultedList.ofSize(3, ItemStack.EMPTY);
    private Random random = new Random();

    public MessageBottleEntity(World world) {
        super(OffshoreEntities.MESSAGE_BOTTLE, world);
    }

    public MessageBottleEntity(EntityType<MessageBottleEntity> entityType, World world) {
        super(entityType, world);
        //this.inanimate = true;
        //randomizePositionAndAngles();
        System.out.println("CONSTRUCTOR 1 entity at: " + this.getPos().toString());
    }

    public MessageBottleEntity(World world, double x, double y, double z, float pitch, float yaw) {
        super(OffshoreEntities.MESSAGE_BOTTLE, world);

        this.setPos(x, y, z);
        this.setRotation(yaw, pitch);

        this.inanimate = true;
        //randomizePositionAndAngles();
        this.setBoundingBox(new Box(this.getX() - 0.1875D, this.getY() - 0.25D + 0.125D, this.getZ() - 0.1875D, this.getX() + 0.1875D, this.getY() + 0.25D + 0.125D, this.getZ() + 0.1875D));

        System.out.println("SPAWNED NEW ENTITY AT: " + this.getPos().toString());
        if (world.isClient()) {
            this.updateTrackedPosition(this.getX(), this.getY(), this.getZ());

            this.setRotation(this.pitch, this.yaw);
        }

    }

    public void randomizePositionAndAngles() {

        this.setRotation(180*(random.nextFloat() / (random.nextBoolean() ? 1.0f : -1.0f)), 180*(random.nextFloat() / (random.nextBoolean() ? 1.0f : -1.0f)));
        //this.setPos((getBlockPos().getX() + 0.999) * (random.nextDouble() / 1.0f), getBlockPos().getY() - 0.075, (getBlockPos().getZ() + 0.999) * (random.nextDouble() / 1.0f));
    }

    @Override
    public void tick() {
        super.tick();

        System.out.println(world.isClient() ? "CLIENT" + this.getPos().toString() : "SERVER: " + this.getPos().toString());
    }

    //@Override
    //public boolean canSpawn(WorldView world) {
    //    return true;
    //}

    @Override
    public boolean isPushable() { return false; }

    @Override
    public boolean interactMob(PlayerEntity player, Hand hand) {

        if(world.isClient()) {
            return true;
        } else {

            ItemScatterer.spawn(world, this.getBlockPos(), this);
            this.dropStack(new ItemStack(Items.GLASS_BOTTLE, 1));

            onBreak(this);
            this.remove();
        }

        return true;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return itemStacks;
    }

    //@Override
    //public void fromTag(CompoundTag tag) {
    //    super.fromTag(tag);
    //    Inventories.fromTag(tag, itemStacks);
    //}

    //@Override
    //public CompoundTag toTag(CompoundTag tag) {
    //    Inventories.toTag(tag, itemStacks);
    //    return tag;
    //    //return super.toTag(tag);
    //}

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        Inventories.fromTag(tag, itemStacks);
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        Inventories.toTag(tag, itemStacks);
    }

    //@Override
    //public void updatePosition(double x, double y, double z) {
    //    this.setPos(x, y, z);
    //}

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
    }

    //@Override
    //protected void updateAttachmentPosition() {
    //    this.setPos(this.attachmentPos.getX(), this.attachmentPos.getY(), this.attachmentPos.getZ());
    //}

    @Override
    public float getEyeHeight(EntityPose pose) {
        return -0.0625F;
    }


    //@Override
    //protected float getEyeHeight(EntityPose pose, EntityDimensions dimensions) {
    //    return -0.0625F;
    //}

    //@Environment(EnvType.CLIENT)
    //@Override
    //public boolean shouldRender(double distance) {
    //    return distance < 1024.0D;
    //}

    //@Override
    //public int getWidthPixels() {
    //    return 4;
    //}

    //@Override
    //public int getHeightPixels() {
    //    return 12;
    //}

    //@Override
    public void onBreak(Entity entity) {
        this.playSound(SoundEvents.BLOCK_GLASS_HIT, 1.0f, 2.0f);
        this.playSound(SoundEvents.BLOCK_SAND_BREAK, 0.8f, 1.0f);
    }

    //@Override
    public void onPlace() {}


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
