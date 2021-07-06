package net.geeky.magnumclient.hacks;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;

public class FlightHack extends HackBase{

    MinecraftClient mc = MinecraftClient.getInstance();

    public static FlightHack INSTANCE = new FlightHack();

    @Override
    public void onenable(){
        if (mc.player == null) return;
        if (mc.player.getAbilities().creativeMode) return;
        mc.player.sendMessage(new TranslatableText("Flight Hack: Activated"), true);
        mc.player.getAbilities().allowFlying = true;
        mc.player.getAbilities().flying = true;
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (this.active == false) return;
            if (mc.player == null) return;
            if (this.mc.player.getAbilities().creativeMode == true) return;
            mc.player.getAbilities().setFlySpeed(.5f);
        });
    }

    @Override
    public void ondisable(){
        if (mc.player == null) return;
        if (mc.player.getAbilities().creativeMode) return;
        mc.player.sendMessage(new TranslatableText("Flight Hack: Deactivated"), true);
        mc.player.getAbilities().allowFlying = false;
        mc.player.getAbilities().flying = false;
        mc.player.getAbilities().setFlySpeed(.05f);
    }
}
