package net.geeky.magnumclient.keybinds;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.geeky.magnumclient.hacks.AirJumpHack;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class RegisterAirJumpHack {

    public KeyBinding airjumphack;
    AirJumpHack ajh = new AirJumpHack();
    public boolean activate;

    public void Register(){

        airjumphack = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.magnumclient.enableairjump", GLFW.GLFW_KEY_Z, "key.category.geeky.parkerhacks"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (airjumphack.wasPressed()){

            }
        });


    }
}
