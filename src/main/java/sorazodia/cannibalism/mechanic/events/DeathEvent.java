package sorazodia.cannibalism.mechanic.events;

import java.util.Random;

import sorazodia.cannibalism.items.manager.ItemList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class DeathEvent 
{
	private Random rand = new Random();
	private int amount = rand.nextInt(3)+1;

	@SubscribeEvent(receiveCanceled = true, priority = EventPriority.NORMAL)
	public void onDeath(LivingDeathEvent livingDeath)
	{
		EntityLivingBase living = livingDeath.entityLiving;
		if(!living.worldObj.isRemote)
		{
			if(living instanceof EntityPlayer)
			{
				living.dropItem(ItemList.playerFlesh, amount);
			}
			if(living instanceof EntityVillager)
			{
				living.dropItem(ItemList.villagerFlesh, amount);
			}
		}
	}

}
