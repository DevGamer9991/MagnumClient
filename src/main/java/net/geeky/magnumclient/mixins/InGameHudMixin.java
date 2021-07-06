package net.geeky.magnumclient.mixins;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.geeky.magnumclient.hud.IngameHackHud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.option.AttackIndicator;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {
    MinecraftClient client = MinecraftClient.getInstance();
    Identifier CROSSHAIR_TEXTURE = new Identifier("crosshair.png");

    private int scaledWidth;
    private int scaledHeight;


    private int posY;
    private int textColor;

    public InGameHudMixin(MinecraftClient client) {
        this.client = client;
    }

    @Inject(at = @At("HEAD"), method = "renderHotbar", cancellable = true)
    private void addCustomText(float tickDelta, MatrixStack matrices, CallbackInfo ci) {
        new IngameHackHud().render(matrices);
    }

    @Inject(at = @At("HEAD"), method = "renderCrosshair", cancellable = true)
    private void addCustomCrosshair(MatrixStack matrices, CallbackInfo info){
        client.getTextureManager().bindTexture(CROSSHAIR_TEXTURE);

        Window sr = client.getWindow();
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
        info.cancel();
    }
}
