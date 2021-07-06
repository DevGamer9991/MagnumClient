package net.geeky.magnumclient.mixinsinterface;

import java.util.List;

import net.minecraft.world.chunk.BlockEntityTickInvoker;

public interface IWorld
{
	List<BlockEntityTickInvoker> getBlockEntityTickers();
}
