package net.geeky.magnumclient.hacks;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.Vec3d;

public class JesusHack extends HackBase {

    public boolean actived;

    MinecraftClient mc = MinecraftClient.getInstance();

    @Override
    public void onenable(){
        if(this.mc.player == null) return;
        if(this.mc == null) return;
        mc.player.sendMessage(new LiteralText("Jesus Hack: Activated"), true);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(this.mc.player == null) return;
            assert mc.player != null;
            if (mc.options.keySneak.isPressed()) return;
            if (mc.player.isTouchingWater() || mc.player.isInLava()){
                if (!active) return;
                Vec3d velo = mc.player.getVelocity();
                mc.player.setVelocity(velo.x, 0.11, velo.z);
            }
        });

        actived = true;

    }

    @Override
    public void ondisable(){
        mc.player.sendMessage(new LiteralText("Jesus Hack: Deactivated"), true);
        actived = false;
    }

}
