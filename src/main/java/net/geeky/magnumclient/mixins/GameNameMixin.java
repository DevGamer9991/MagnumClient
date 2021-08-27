package net.geeky.magnumclient.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.server.integrated.IntegratedServer;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class GameNameMixin{

    @Nullable
    private IntegratedServer server;

    MinecraftClient client = MinecraftClient.getInstance();

    @Inject(at = @At("RETURN"), method = "getWindowTitle", cancellable = true)
    private String addCustomTitle(CallbackInfoReturnable ci) {
        StringBuilder stringBuilder = new StringBuilder("Magnum Client");

        return stringBuilder.toString();
    }

}
