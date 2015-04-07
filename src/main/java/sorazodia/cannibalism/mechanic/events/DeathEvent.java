package sorazodia.cannibalism.mechanic.events;

import java.util.Random;

import sorazodia.cannibalism.items.manager.ItemList;
import sorazodia.cannibalism.mechanic.nbt.MeatOriginNBT;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class DeathEvent 
{
	private Random rand = new Random();

	@SubscribeEvent(receiveCanceled = true, priority = EventPriority.NORMAL)
	public void onDeath(LivingDeathEvent livingDeath)
	{
		int amount = rand.nextInt(3)+1;
		EntityLivingBase living = livingDeath.entityLiving;
		if(!living.worldObj.isRemote)
		{
			if(living instanceof EntityPlayer)
			{
				ItemStack playerFlesh = new ItemStack(ItemList.playerFlesh);
				setMeatName(playerFlesh, living.getCommandSenderName()+"'s Flesh");
				living.entityDropItem(playerFlesh, amount);
			}
			if(living instanceof EntityVillager)
			{
				living.dropItem(ItemList.villagerFlesh, amount);
			}
		}
	}

	private void setMeatName(ItemStack meat, String owner)
	{
		MeatOriginNBT.addNameToNBT(meat, owner);
		MeatOriginNBT.getNameFromNBT(meat);
	}
	
}
