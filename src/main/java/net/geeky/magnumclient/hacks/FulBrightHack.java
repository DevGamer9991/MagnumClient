package net.geeky.magnumclient.hacks;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;

public class FulBrightHack extends HackBase{

    MinecraftClient mc = MinecraftClient.getInstance();

    public boolean actived;

    @Override
    public void onenable(){
        mc.options.gamma = 20;
        mc.player.sendMessage(new TranslatableText("Fulbright: Activated"), true);
        actived = true;
    }

    @Override
    public void ondisable(){
        mc.options.gamma = 1;
        mc.player.sendMessage(new TranslatableText("Fulbright: Deactivated"), true);
        actived = false;
    }
}
