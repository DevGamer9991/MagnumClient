package net.geeky.magnumclient.screens;

import net.geeky.magnumclient.hacks.FlightHack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.List;

public class HacksScreen extends Screen {
    private List<Text> tooltipText;

    FlightHack fh = new FlightHack();

    public HacksScreen(){
        super(new TranslatableText("Parker's Essential Hacks"));
    }

    protected void init(){
        super.init();

        this.addDrawableChild(new ButtonWidget(MinecraftClient.getInstance().getWindow().getScaledWidth() / 2 - 250, MinecraftClient.getInstance().getWindow().getScaledHeight() / 2 - 120, 100, 20, new TranslatableText("Flight"), (buttonWidget) -> {
            if (client.player == null) return;
            fh.toggle();
            client.openScreen(null);
        }));
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.tooltipText = null;
        if (MinecraftClient.getInstance().player == null){
            this.renderBackground(matrices);
        }
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 20, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
        if (this.tooltipText != null) {
            this.renderTooltip(matrices, this.tooltipText, mouseX, mouseY);
        }

    }

    private void toggle(){
        fh.toggle();
    }
}
