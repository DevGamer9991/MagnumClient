package net.geeky.magnumclient.hacks;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;

public class AirJumpHack extends HackBase{

    MinecraftClient mc = MinecraftClient.getInstance();


    public static AirJumpHack INSTANCE = new AirJumpHack();

    @Override
    public void onenable(){
        mc.player.sendMessage(new LiteralText("Air Jump Hack: Activated"), true);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!active) return;
            if (mc.player == null) return;
            mc.player.setOnGround(true);
        });

    }

    @Override
    public void ondisable(){
        mc.player.sendMessage(new LiteralText("Air Jump Hack: Deactivated"), true);
    }

}
