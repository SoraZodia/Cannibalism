package sorazodia.cannibalism.mechanic.events;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import sorazodia.cannibalism.config.ConfigHandler;
import sorazodia.cannibalism.main.Cannibalism;
import sorazodia.cannibalism.mechanic.nbt.CannibalismNBT;
import sorazodia.cannibalism.mob.EntityWendigo;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class EntityNBTEvents
{

	private boolean applyEffect = true;
	private static boolean spawn = true;

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void entityCreateEvent(EntityConstructing create)
	{
		if (ConfigHandler.getMyth() == false)
			return;

		if ((create.entity instanceof EntityPlayer)
				&& CannibalismNBT.getNBT((EntityLivingBase) create.entity) == null)
		{
			CannibalismNBT.register((EntityLivingBase) create.entity);
		}
	}

	@SubscribeEvent()
	public void entityTick(PlayerTickEvent tick)
	{
		if (ConfigHandler.getMyth() == false)
			return;

		if (CannibalismNBT.getNBT(tick.player) == null)
			return;

		float sinLevel = CannibalismNBT.getNBT(tick.player).getWendigoValue();

		if (sinLevel >= 100 && applyEffect && spawn)
		{
			tick.player.addPotionEffect(new PotionEffect(Potion.blindness.id, 100));
			tick.player.addPotionEffect(new PotionEffect(Potion.confusion.id, 100));
			applyEffect = false;
		}

		if (sinLevel >= 150)
		{

			if (CannibalismNBT.getNBT(tick.player).wendigoSpawned() == false
					&& spawn)
			{
				EntityWendigo wendigo = (EntityWendigo) EntityList.createEntityByName(Cannibalism.MODID
						+ ".wendigo", tick.player.worldObj);
				wendigo.setLocationAndAngles(tick.player.posX - 40, tick.player.posY, tick.player.posZ, 0, 0);
				tick.player.worldObj.spawnEntityInWorld(wendigo);

				CannibalismNBT.getNBT(tick.player).setWedigoSpawn(true);
				applyEffect = true;
			}

			CannibalismNBT.getNBT(tick.player).setWendigoValue(0);

		}
	}

	public static void setSpawn(boolean doSpawn)
	{
		spawn = doSpawn;
	}

}