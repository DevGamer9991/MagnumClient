package net.geeky.magnumclient.keybinds;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.geeky.magnumclient.hacks.HudHack;
import net.geeky.magnumclient.hud.IngameHackHud;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class RegisterHud {

    public KeyBinding hud;
    HudHack hh = new HudHack();

    public void Register(){

        hud = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.magnumclient.enablehud", GLFW.GLFW_KEY_F6, "key.category.geeky.parkerhacks"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (hud.wasPressed()){
                hh.toggle();
            }
        });
    }
}
