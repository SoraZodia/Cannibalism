package sorazodia.cannibalism.mechanic.events;

import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
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
				ItemStack playerFlesh = new ItemStack(ItemRegistry.playerFlesh);
				setMeatName(playerFlesh, entity.getName() + "'s Flesh");
				entity.entityDropItem(playerFlesh, amount);

				if (event.getSource().getTrueSource() instanceof EntityPlayer)
					possessKiller((EntityPlayer) event.getSource().getTrueSource(), CannibalismNBT.getNBT((EntityPlayer) entity));
			}
			if (entity instanceof EntityVillager)
			{
				entity.dropItem(ItemRegistry.villagerFlesh, amount);
			}
		}
	}

	private void possessKiller(EntityPlayer player, CannibalismNBT stats)
	{
		CannibalismNBT nbt = CannibalismNBT.getNBT(player);

		nbt.changeWendigoValue(stats.getWendigoValue() / 2);
		player.getFoodStats().addStats(5, 5);
	}

	private void setMeatName(ItemStack meat, String owner)
	{
		FleshNBTHelper.addName(meat, owner);
		FleshNBTHelper.setItemNickname(meat);
	}

}
