package net.geeky.magnumclient.hacks;

import net.minecraft.text.LiteralText;

public class HudHack extends HackBase {

    public static boolean Activated = false;

    public static HudHack INSTANCE = new HudHack();

    @Override
    public void onenable() {
        super.onenable();
        mc.player.sendMessage(new LiteralText("Hud: Activated"), true);
        Activated = true;
    }

    @Override
    public void ondisable() {
        super.ondisable();
        mc.player.sendMessage(new LiteralText("Hud: Deactivated"), true);
        Activated = false;
    }
}
