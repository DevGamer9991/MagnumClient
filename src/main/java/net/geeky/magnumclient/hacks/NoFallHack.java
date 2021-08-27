package net.geeky.magnumclient.hacks;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.text.LiteralText;

public class NoFallHack extends HackBase{
    public void onenable() {
        if (mc.player == null) return;

        mc.player.sendMessage(new LiteralText("No Fall: Activated"), true);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!active) return;

            if (FlightHack.INSTANCE.isEnabled()) return;

            ClientPlayerEntity player = mc.player;
            if(player.fallDistance <= (player.isFallFlying() ? 1 : 2))
                return;

            if(player.isFallFlying() && player.isSneaking()
                    && !isFallingFastEnoughToCauseDamage(player))
                return;

            player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
        });

    }

    private boolean isFallingFastEnoughToCauseDamage(ClientPlayerEntity player)
    {
        return player.getVelocity().y < -0.1;
    }

    public void ondisable() {
        if (mc.player == null) return;
        mc.player.sendMessage(new LiteralText("No Fall: Deactivated"), true);
    }
}
