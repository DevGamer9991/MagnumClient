package net.geeky.magnumclient.hacks;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.geeky.magnumclient.mixinsinterface.IWorld;
import net.minecraft.block.Block;
import net.minecraft.block.TrappedChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.BlockEntityTickInvoker;
import net.minecraft.world.chunk.Chunk;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;

import javax.swing.*;
import java.util.ArrayList;
import java.util.OptionalDouble;

public class ChestESP extends HackBase{

    public static MinecraftClient mc;

    public static boolean ChestESPActive = false;

    @Override
    public void onenable() {
        super.onenable();
        mc.player.sendMessage(new TranslatableText("Chest ESP: Activated"), true);
        ChestESPActive = true;
    }

    @Override
    public void ondisable() {
        super.ondisable();
        mc.player.sendMessage(new LiteralText("Chest ESP: Deactivated"), true);
        ChestESPActive = false;
    }


    public static synchronized void render(MatrixStack matrices, Camera camera) {

        if(ChestESPActive){
            return;
        }

        ArrayList<BlockEntity> normalChests = new ArrayList<>();
        ArrayList<BlockEntity> enderChests = new ArrayList<>();

        Vec3d cameraPos = camera.getPos();
        VertexConsumerProvider.Immediate entityVertexConsumers =
                MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();

        matrices.push();
        matrices.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

        for(BlockEntityTickInvoker blockEntityTicker : ((IWorld)mc.world).getBlockEntityTickers())
        {
            BlockEntity blockEntity = mc.world.getBlockEntity(blockEntityTicker.getPos());

            if (blockEntity instanceof ChestBlockEntity) {
                normalChests.add(blockEntity);
            }else if (blockEntity instanceof EnderChestBlockEntity) {
                enderChests.add(blockEntity);
            }
        }

        //normalChests.forEach(e -> renderChestBlockBounding(matrices, builder, e));
        //enderChests.forEach(e -> renderEnderChestBlockBounding(matrices, builder, e));

        RenderSystem.disableDepthTest();
        matrices.pop();
    }

    private static void renderChestBlockBounding(
            MatrixStack matrices, VertexConsumer builder, BlockEntity b) {
        if (b == null) {
            return;
        }

        final float size = 1.0f;
        final float x = b.getPos().getX(), y = b.getPos().getY(), z = b.getPos().getZ(), opacity = .5f;

        WorldRenderer.drawBox(
                matrices,
                builder,
                x,
                y,
                z,
                x + size,
                y + size,
                z + size,
                1.00f,
                0.55f,
                0.10f,
                opacity);
    }

    private static void renderEnderChestBlockBounding(
            MatrixStack matrices, VertexConsumer builder, BlockEntity b) {
        if (b == null) {
            return;
        }

        final float size = 1.0f;
        final float x = b.getPos().getX(), y = b.getPos().getY(), z = b.getPos().getZ(), opacity = .5f;

        WorldRenderer.drawBox(
                matrices,
                builder,
                x,
                y,
                z,
                x + size,
                y + size,
                z + size,
                0.00f,
                0.80f,
                0.53f,
                opacity);
    }

    private void renderLines(MatrixStack matrixStack, Vec3d start,
                             ArrayList<net.minecraft.util.math.Box> boxes, int regionX, int regionZ)
    {
        Matrix4f matrix = matrixStack.peek().getModel();
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();

        for(Box box : boxes)
        {
            Vec3d end = box.getCenter().subtract(regionX, 0, regionZ);

            bufferBuilder
                    .vertex(matrix, (float)start.x, (float)start.y, (float)start.z)
                    .next();

            bufferBuilder
                    .vertex(matrix, (float)end.x, (float)end.y, (float)end.z)
                    .next();
        }
    }
}