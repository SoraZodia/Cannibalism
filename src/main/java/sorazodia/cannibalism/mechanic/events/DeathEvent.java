package sorazodia.cannibalism.mechanic.events;

import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import sorazodia.cannibalism.main.ItemRegistry;
import sorazodia.cannibalism.mechanic.nbt.CannibalismNBT;
import sorazodia.cannibalism.mechanic.nbt.FleshNBTHelper;

public class DeathEvent
{
	private Random rand = new Random();

	@SubscribeEvent(receiveCanceled = true, priority = EventPriority.NORMAL)
	public void onDeath(LivingDeathEvent event)
	{
		int amount = rand.nextInt(3) + 1;
		EntityLivingBase entity = event.getEntityLiving();
		if (!entity.world.isRemote)
		{
			if (entity instanceof EntityPlayer)
			{
				CannibalismNBT stats = CannibalismNBT.getNBT((EntityPlayer) entity);
				ItemStack playerFlesh = new ItemStack(ItemRegistry.playerFlesh, amount);
				setMeatName(playerFlesh, entity.getName() + "'s Flesh");
				
				EntityItem drops = new EntityItem(entity.world, entity.posX, entity.posY + 2, entity.posZ, playerFlesh);	
				
				entity.world.spawnEntity(drops);
				
				if (event.getSource().getTrueSource() instanceof EntityPlayer)
					possessKiller((EntityPlayer) event.getSource().getTrueSource(), stats);
				else if (stats.getWendigoValue() >= 500)
					EntityNBTEvents.spawnWendigo((EntityPlayer) entity);
			}
			if (entity instanceof EntityVillager)
			{
				EntityItem drops = new EntityItem(entity.world, entity.posX, entity.posY + 2, entity.posZ, new ItemStack(ItemRegistry.villagerFlesh, amount));
				
				entity.world.spawnEntity(drops);
			}
		}
	}

	private void possessKiller(EntityPlayer player, CannibalismNBT stats)
	{
		CannibalismNBT nbt = CannibalismNBT.getNBT(player);

		nbt.changeWendigoValue(stats.getWendigoValue() / 2);
		player.getFoodStats().addStats(10, 10);
	}

	private void setMeatName(ItemStack meat, String owner)
	{
		FleshNBTHelper.addName(meat, owner);
		FleshNBTHelper.setItemNickname(meat);
	}

}
