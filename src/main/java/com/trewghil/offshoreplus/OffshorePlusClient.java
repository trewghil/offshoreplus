package com.trewghil.offshoreplus;

import com.trewghil.offshoreplus.client.render.entity.OffshoreEntityRenderers;
import com.trewghil.offshoreplus.networking.ClientNetworking;
import net.fabricmc.api.ClientModInitializer;

public class OffshorePlusClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientNetworking.init();

        OffshoreEntityRenderers.init();
    }
}
