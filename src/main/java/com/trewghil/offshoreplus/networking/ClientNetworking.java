package com.trewghil.offshoreplus.networking;

import com.trewghil.offshoreplus.entity.MessageBottleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class ClientNetworking {

    public ClientNetworking() {

    }

    public static void init() {
        ClientSidePacketRegistry.INSTANCE.register(MessageBottleEntity.ID, (((packetContext, packetByteBuf) -> {

            double x = packetByteBuf.readDouble();
            double y = packetByteBuf.readDouble();
            double z = packetByteBuf.readDouble();

            float pitch = packetByteBuf.readFloat();
            float yaw = packetByteBuf.readFloat();

            int id = packetByteBuf.readInt();

            packetContext.getTaskQueue().execute(() -> {
                MessageBottleEntity entity = new MessageBottleEntity(MinecraftClient.getInstance().world, x, y, z, pitch, yaw);

                entity.setEntityId(id);
                entity.updatePosition(x, y, z);

                MinecraftClient.getInstance().world.addEntity(id, entity);
            });
        })));
    }

}
