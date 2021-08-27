package net.geeky.magnumclient;

import net.fabricmc.api.ModInitializer;
import net.geeky.magnumclient.hacks.FlightHack;
import net.geeky.magnumclient.hacks.HudHack;
import net.geeky.magnumclient.keybinds.*;
import net.geeky.magnumclient.storage.Storage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class MagnumStart implements ModInitializer {

	public static final Logger LOGGER = LogManager.getLogger("geeky");

	@Override
	public void onInitialize() {
		//new RegisterGui().Register();
		new RegisterFlightHack().Register();
		new RegisterFulBrightHack().Register();
		new RegisterJesusHack().Register();
		new RegisterAirJumpHack().Register();
		new RegisterAutoToolHack().Register();
		new RegisterNoFallHack().Register();
		new RegisterChestESPHack().Register();

		//new RegisterTestHack().Register();
		new RegisterHud().Register();
	}
}
