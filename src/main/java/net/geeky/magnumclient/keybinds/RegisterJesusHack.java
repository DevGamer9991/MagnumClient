package net.geeky.magnumclient.keybinds;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.geeky.magnumclient.hacks.JesusHack;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class RegisterJesusHack {

    public KeyBinding jesushack;
    JesusHack jh = new JesusHack();
    public boolean activate;

    public void Register(){

        jesushack = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.magnumclient.enablejesus", GLFW.GLFW_KEY_J, "key.category.geeky.parkerhacks"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (jesushack.wasPressed()){
                jh.toggle();
            }
        });
    }
}
