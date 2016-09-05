package sorazodia.cannibalism.mechanic.events;

import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import sorazodia.cannibalism.items.manager.ItemList;
import sorazodia.cannibalism.mechanic.nbt.FleshNBTHelper;

public class DeathEvent
{
	private Random rand = new Random();

	@SubscribeEvent(receiveCanceled = true, priority = EventPriority.NORMAL)
	public void onDeath(LivingDeathEvent livingDeath)
	{
		int amount = rand.nextInt(3) + 1;
		EntityLivingBase living = livingDeath.getEntityLiving();
		if (!living.worldObj.isRemote)
		{
			if (living instanceof EntityPlayer)
			{
				ItemStack playerFlesh = new ItemStack(ItemList.playerFlesh);
				setMeatName(playerFlesh, living.getName() + "'s Flesh");
				living.entityDropItem(playerFlesh, amount);
			}
			if (living instanceof EntityVillager)
			{
				living.dropItem(ItemList.villagerFlesh, amount);
			}
		}
	}

	private void setMeatName(ItemStack meat, String owner)
	{
		FleshNBTHelper.addName(meat, owner);
		FleshNBTHelper.setItemNickname(meat);
	}

}
