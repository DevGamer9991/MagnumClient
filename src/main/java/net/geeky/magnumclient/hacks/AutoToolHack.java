package net.geeky.magnumclient.hacks;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;

public class AutoToolHack extends HackBase {

    MinecraftClient mc = MinecraftClient.getInstance();

    public boolean Active;

    @Override
    public void onenable() {
        mc.player.sendMessage(new LiteralText("Auto Tool Hack: Activated"), true);
        Active = true;
    }

    @Override
    public void ondisable(){
        mc.player.sendMessage(new LiteralText("Auto Tool Hack: Deactivated"), true);
        Active = false;
    }

    public void onStartBreakingBlock(BlockPos pos) {

        if(!Active) {
            return;
        }

        PlayerInventory inventory = mc.player.getInventory();
        ItemStack heldItem = mc.player.getMainHandStack();

        BlockState state = mc.world.getBlockState(pos);
        float bestSpeed = getMiningSpeed(heldItem, state);
        int bestSlot = -1;

        for(int slot = 0; slot < 9; slot++)
        {
            if(slot == inventory.selectedSlot)
                continue;

            ItemStack stack = inventory.getStack(slot);

            float speed = getMiningSpeed(stack, state);
            if(speed <= bestSpeed)
                continue;



            bestSpeed = speed;
            bestSlot = slot;
        }
        switchSlot(bestSlot);
    }

    public void onAttackEntity(){
        int slotS = mc.player.getInventory().selectedSlot;
        int slot = mc.player.getInventory().selectedSlot;
        double damageS = 0;
        double currentDamageS;
        for(int i = 0; i < 9; i++) {
            if (mc.player.getInventory().getStack(i).getItem() instanceof SwordItem || (mc.player.getInventory().getStack(i).getMaxDamage() - mc.player.getInventory().getStack(i).getDamage()) > 10) {
                if (!(mc.player.getInventory().getStack(i).getItem() instanceof SwordItem)) continue;
                currentDamageS = ((SwordItem) mc.player.getInventory().getStack(i).getItem()).getMaterial().getAttackDamage() + EnchantmentHelper.getAttackDamage(mc.player.getInventory().getStack(i), EntityGroup.DEFAULT) + 2;
                if (currentDamageS > damageS) {
                    damageS = currentDamageS;
                    slotS = i;
                }
            }
        }

        mc.player.getInventory().selectedSlot = slotS;
    }

    public void switchSlot(int slot){
        if(slot == -1) return;
        mc.player.getInventory().selectedSlot = slot;
    }

    private float getMiningSpeed(ItemStack stack, BlockState state)
    {
        float speed = stack.getMiningSpeedMultiplier(state);

        if(speed > 1)
        {
            int efficiency =
                    EnchantmentHelper.getLevel(Enchantments.EFFICIENCY, stack);
            if(efficiency > 0 && !stack.isEmpty())
                speed += efficiency * efficiency + 1;
        }

        return speed;
    }

    public boolean Activated(){
        if(this.active){
            return true;
        }
        else{
            return false;
        }
    }
}
