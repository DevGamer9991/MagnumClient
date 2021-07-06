package net.geeky.magnumclient.keybinds;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.geeky.magnumclient.hacks.AutoToolHack;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class RegisterAutoToolHack {

    public KeyBinding autotoolhack;
    AutoToolHack ath = new AutoToolHack();
    public boolean activate;

    public void Register(){

        autotoolhack = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.magnumclient.enableautotool", GLFW.GLFW_KEY_R, "key.category.geeky.parkerhacks"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (autotoolhack.wasPressed()){
                ath.toggle();
            }
        });


    }
}
