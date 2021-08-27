package net.geeky.magnumclient.keybinds;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.geeky.magnumclient.hacks.TestHack;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

import javax.swing.text.JTextComponent;

public class RegisterTestHack {

    public KeyBinding testhack;
    TestHack th = new TestHack();
    public boolean activate;

    public void Register(){

        testhack = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.magnumclient.enabletest", GLFW.GLFW_KEY_Y, "key.category.geeky.parkerhacks"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (testhack.wasPressed()){
                th.toggle();
            }
        });


    }
}
