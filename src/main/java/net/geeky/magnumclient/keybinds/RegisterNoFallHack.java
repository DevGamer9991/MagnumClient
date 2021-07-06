package net.geeky.magnumclient.keybinds;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.geeky.magnumclient.hacks.NoFallHack;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class RegisterNoFallHack {

    public KeyBinding nofallhack;
    NoFallHack nfh = new NoFallHack();
    public boolean activate;

    public void Register(){

        nofallhack = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.magnumclient.enablefnofall", GLFW.GLFW_KEY_N, "key.category.geeky.parkerhacks"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (nofallhack.wasPressed()){
                nfh.toggle();
            }
        });


    }
}
