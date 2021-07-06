package net.geeky.magnumclient.keybinds;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.geeky.magnumclient.hacks.FulBrightHack;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class RegisterFulBrightHack {

    public KeyBinding fulbrighthack;
    FulBrightHack fbh = new FulBrightHack();
    public boolean activate;

    public void Register(){

        fulbrighthack = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.magnumclient.enablefulbright", GLFW.GLFW_KEY_C, "key.category.geeky.parkerhacks"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (fulbrighthack.wasPressed()){
                fbh.toggle();
            }
        });
    }
}
