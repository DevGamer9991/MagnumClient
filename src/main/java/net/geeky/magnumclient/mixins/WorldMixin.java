package net.geeky.magnumclient.mixins;

import java.util.List;

import net.geeky.magnumclient.mixinsinterface.IWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.chunk.BlockEntityTickInvoker;

@Mixin(World.class)
public abstract class WorldMixin implements WorldAccess, AutoCloseable, IWorld
{
    @Shadow
    @Final
    protected List<BlockEntityTickInvoker> blockEntityTickers;

    @Override
    public List<BlockEntityTickInvoker> getBlockEntityTickers()
    {
        return blockEntityTickers;
    }
}
