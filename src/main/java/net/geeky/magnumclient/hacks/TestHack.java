package net.geeky.magnumclient.hacks;

import net.minecraft.client.MinecraftClient;
import net.minecraft.world.explosion.Explosion;

public class TestHack extends HackBase{
    MinecraftClient mc = MinecraftClient.getInstance();

    Explosion.DestructionType type;

    @Override
    public void onenable() {
        mc.player.world.createExplosion(mc.player, mc.player.getPos().x, mc.player.getPos().y, mc.player.getEyeHeight(mc.player.getPose()), 5f, type);
    }
}
