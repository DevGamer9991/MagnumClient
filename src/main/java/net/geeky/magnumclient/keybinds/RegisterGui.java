package net.geeky.magnumclient.keybinds;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.geeky.magnumclient.screens.HacksScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class RegisterGui {
    public KeyBinding opengui = null;
    private static KeyBinding keyBinding;

    public void Register(){
        opengui = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.magnumclient.openhackmenu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "key.category.geeky.parkerhacks"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (opengui.wasPressed()) {
                MinecraftClient.getInstance().openScreen(new HacksScreen());
            }
        });
    }
}
