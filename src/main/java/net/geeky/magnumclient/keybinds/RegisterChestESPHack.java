package net.geeky.magnumclient.keybinds;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.geeky.magnumclient.hacks.ChestESP;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class RegisterChestESPHack {

    public KeyBinding chestesphack;
    ChestESP chesp = new ChestESP();
    public boolean activate;

    public void Register(){

        chestesphack = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.magnumclient.enablechestesp", GLFW.GLFW_KEY_X, "key.category.geeky.parkerhacks"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (chestesphack.wasPressed()){
                chesp.toggle();
            }
        });
    }
}
