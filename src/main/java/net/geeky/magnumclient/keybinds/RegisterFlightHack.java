package net.geeky.magnumclient.keybinds;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.geeky.magnumclient.hacks.FlightHack;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

import java.net.SocketAddress;

public class RegisterFlightHack {

    public KeyBinding flighthack;
    FlightHack fh = new FlightHack();
    public boolean activate;

    ServerAddress address = ServerAddress.parse("us.hypixel.net");

    public void Register(){

        flighthack = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.magnumclient.enableflight", GLFW.GLFW_KEY_G, "key.category.geeky.parkerhacks"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (flighthack.wasPressed()){
                fh.toggle();
            }
        });


    }
}
