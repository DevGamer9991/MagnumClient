package net.geeky.magnumclient.hacks;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.WorldRenderer;

public class HackBase {

    public boolean active;

    MinecraftClient mc = MinecraftClient.getInstance();

    public void onenable(){}
    public void ondisable(){}

    public void toggle(boolean toggler){
        if (!active){
            active = true;
            if (toggler){
                onenable();
            }
        }else {
            active = false;
            if (toggler){
                ondisable();
            }

        }
    }

    public void toggle() {
        toggle(true);
    }

    public boolean isEnabled()
    {
        return active;
    }


}
