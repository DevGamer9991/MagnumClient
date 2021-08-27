package net.geeky.magnumclient.hud;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.geeky.magnumclient.hacks.ChestESP;
import net.geeky.magnumclient.hacks.HudHack;
import net.geeky.magnumclient.mixins.ClientAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.option.AttackIndicator;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.dimension.DimensionType;

import java.awt.*;

public class IngameHackHud extends DrawableHelper {

    private int scaledWidth;
    private int scaledHeight;

    Identifier CROSSHAIR_TEXTURE = new Identifier("geeky:crosshair.png");
    Identifier HIT_TEXTURE = new Identifier("minecraft:textures/gui/icons.png");

    String airjump;
    String autotool;
    String flight;
    String fullbright;
    String jesus;


    DimensionType overworld;

    MinecraftClient mc = MinecraftClient.getInstance();
    MinecraftClient client = MinecraftClient.getInstance();

    public boolean active;

    public void render(MatrixStack matrixStack){
        if (!HudHack.Activated){
            return;
        }

        drawstrings(matrixStack, "Magnum Client");
        drawcords(matrixStack);
        renderOnScreenPlayer();
        //renderActiveItems(matrixStack);
    }

    public void drawstrings(MatrixStack matrixStack, String string){

        TextRenderer tr = mc.textRenderer;

        tr.drawWithShadow(matrixStack, string, this.mc.getWindow().getScaledWidth() - this.mc.getWindow().getScaledWidth() + 1, 1f, 0xffFFFFFF);
        tr.drawWithShadow(matrixStack, mc.player.getName().getString(), MinecraftClient.getInstance().getWindow().getScaledWidth() - tr.getWidth(mc.player.getName().getString()) - 1, 1f, 0xff0081FF, false);


        tr.drawWithShadow(matrixStack, Integer.toString(((ClientAccessor) mc).getFps()) + " fps", this.mc.getWindow().getScaledWidth() - this.mc.getWindow().getScaledWidth() + 1, 30f, 0xffFFFFFF);
        tr.drawWithShadow(matrixStack, getDuribility(), this.mc.getWindow().getScaledWidth() - this.mc.getWindow().getScaledWidth() + 1, 40f, 0xffFFFFFF);

        tr.drawWithShadow(matrixStack, getHeadDuribility(), this.mc.getWindow().getScaledWidth() - this.mc.getWindow().getScaledWidth() + 1, 50f, 0xffFFFFFF);
        tr.drawWithShadow(matrixStack, getChestDuribility(), this.mc.getWindow().getScaledWidth() - this.mc.getWindow().getScaledWidth() + 1, 60f, 0xffFFFFFF);
        tr.drawWithShadow(matrixStack, getLegDuribility(), this.mc.getWindow().getScaledWidth() - this.mc.getWindow().getScaledWidth() + 1, 70f, 0xffFFFFFF);
        tr.drawWithShadow(matrixStack, getFeetDuribility(), this.mc.getWindow().getScaledWidth() - this.mc.getWindow().getScaledWidth() + 1, 80f, 0xffFFFFFF);
    }

    public void renderOnScreenPlayer(){
        PlayerEntity player = mc.player;

        InventoryScreen.drawEntity(this.mc.getWindow().getScaledWidth() - 20, this.mc.getWindow().getScaledHeight() - 5, 30, 0, 0, player);
    }

    public void drawcords(MatrixStack matrixStack) {

        TextRenderer tr = mc.textRenderer;

        double x1 = mc.gameRenderer.getCamera().getPos().x;
        double y1 = mc.gameRenderer.getCamera().getPos().y - mc.player.getEyeHeight(mc.player.getPose());
        double z1 = mc.gameRenderer.getCamera().getPos().z;

        String top;
        String bottom;

        switch (getDimension()) {
            case "overworld":
                top = String.format("%.0f %.0f %.0f", x1 / 8, y1, z1 / 8);
                bottom = String.format("%.0f %.0f %.0f", x1, y1, z1);

                tr.drawWithShadow(matrixStack, "Nether Cords: " + top, this.mc.getWindow().getScaledWidth() - this.mc.getWindow().getScaledWidth() + 1, 10f, 0xffD10000, false);
                tr.drawWithShadow(matrixStack, "Overworld Cords: " + bottom, this.mc.getWindow().getScaledWidth() - this.mc.getWindow().getScaledWidth() + 1, 20f, 0xffFFFFFF, false);

                return;
            case "nether":
                top = String.format("%.0f %.0f %.0f", x1, y1, z1);
                bottom = String.format("%.0f %.0f %.0f", x1 * 8, y1, z1 * 8);

                tr.drawWithShadow(matrixStack, "Nether Cords: " + top, this.mc.getWindow().getScaledWidth() - this.mc.getWindow().getScaledWidth() + 1, 10f, 0xffD10000, false);
                tr.drawWithShadow(matrixStack, "Overworld Cords: " + bottom, this.mc.getWindow().getScaledWidth() - this.mc.getWindow().getScaledWidth() + 1, 20f, 0xffFFFFFF, false);

                return;
        }
    }

    public String getDimension(){
        switch (MinecraftClient.getInstance().world.getRegistryKey().getValue().getPath()) {
            case "the_nether": return "nether";
            case "the_end":    return "end";
            default:           return "overworld";
        }
    }

    public void drawCrosshair(MatrixStack matrices){

        mc.getTextureManager().bindTexture(CROSSHAIR_TEXTURE);
        Window sr = mc.getWindow();
        int x = sr.getScaledWidth() / 2 - 8 / 2;
        int y = sr.getScaledHeight() / 2 - 8 / 2;
        int w = 8;
        int h = 8;
        DrawableHelper.drawTexture(matrices, x, y, 0, 0, w, h, w, h);

        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.ONE_MINUS_DST_COLOR, GlStateManager.DstFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
        this.drawTexture(matrices, (this.scaledWidth - 15) / 2, (this.scaledHeight - 15) / 2, 0, 0, 15, 15);
        if (this.client.options.attackIndicator == AttackIndicator.CROSSHAIR) {
            float f = this.client.player.getAttackCooldownProgress(0.0F);
            boolean bl = false;
            if (this.client.targetedEntity != null && this.client.targetedEntity instanceof LivingEntity && f >= 1.0F) {
                bl = this.client.player.getAttackCooldownProgressPerTick() > 5.0F;
                bl &= this.client.targetedEntity.isAlive();
            }

            int j = this.scaledHeight / 2 - 7 + 16;
            int k = this.scaledWidth / 2 - 8;
            if (bl) {
                this.drawTexture(matrices, k, j, 68, 94, 16, 16);
            } else if (f < 1.0F) {
                int l = (int)(f * 17.0F);
                this.drawTexture(matrices, k, j, 36, 94, 16, 4);
                this.drawTexture(matrices, k, j, 52, 94, l, 4);
            }
        }
    }

    public void renderActiveItems(MatrixStack matrixStack){
        TextRenderer tr = mc.textRenderer;

        String chestesp;

        if(ChestESP.ChestESPActive){
            chestesp = "Chest ESP: " + "true";
        }else {
            chestesp = "Chest ESP: false";
        }

        tr.drawWithShadow(matrixStack, chestesp, MinecraftClient.getInstance().getWindow().getScaledWidth() - tr.getWidth(chestesp) - 1, 10f, 0xffFFFFFF, false);
    }

    protected String getDuribility() {
        Integer amount = null;
        if (!mc.player.getMainHandStack().isEmpty() && mc.player.getMainHandStack().isDamageable()) amount = mc.player.getMainHandStack().getMaxDamage() - mc.player.getMainHandStack().getDamage();

        return amount == null ? "" : "Hand Durability: " + amount.toString() + " / " + mc.player.getMainHandStack().getMaxDamage();
    }

    protected String getHeadDuribility() {
        String string;
        Integer amount = null;
        PlayerInventory i = mc.player.getInventory();
        ItemStack stack = i.getArmorStack(3);
        if (stack.getItem() instanceof ArmorItem){
            amount = stack.getMaxDamage() - stack.getDamage();
            return "Head Durability: " + amount.toString() + " / " + stack.getMaxDamage();
        }else {
            return "";
        }
    }

    protected String getChestDuribility() {
        String string;
        Integer amount = null;
        PlayerInventory i = mc.player.getInventory();
        ItemStack stack = i.getArmorStack(2);
        if (stack.getItem() instanceof ArmorItem || stack.getItem() instanceof ElytraItem){
            amount = stack.getMaxDamage() - stack.getDamage();
            return "Chest Durability: " + amount.toString() + " / " + stack.getMaxDamage();
        }else {
            return "";
        }
    }

    protected String getLegDuribility() {
        String string;
        Integer amount = null;
        PlayerInventory i = mc.player.getInventory();
        ItemStack stack = i.getArmorStack(1);
        if (stack.getItem() instanceof ArmorItem) {
            amount = stack.getMaxDamage() - stack.getDamage();
            return "Leg Durability: " + amount.toString() + " / " + stack.getMaxDamage();
        } else {
            return "";
        }
    }

    protected String getFeetDuribility() {
        String string;
        Integer amount = null;
        PlayerInventory i = mc.player.getInventory();
        ItemStack stack = i.getArmorStack(0);
        if (stack.getItem() instanceof ArmorItem) {
            amount = stack.getMaxDamage() - stack.getDamage();
            return "Feet Durability: " + amount.toString() + " / " + stack.getMaxDamage();
        } else {
            return "";
        }
    }
}
