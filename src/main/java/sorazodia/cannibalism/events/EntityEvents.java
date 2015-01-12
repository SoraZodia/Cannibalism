package sorazodia.cannibalism.events;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.INpc;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import sorazodia.cannibalism.mechanic.CannibalismNBT;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityEvents 
{

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void entityCreateEvent(EntityConstructing create)
	{
		if((create.entity instanceof EntityAnimal || create.entity instanceof EntityVillager) && CannibalismNBT.getNBT((EntityLivingBase)create.entity) == null)
		{
			CannibalismNBT.register((EntityLivingBase) create.entity);
		}
	}
	
	@SubscribeEvent
	public void updateEntityEvent(LivingUpdateEvent update)
	{
		if(update.entity instanceof EntityAnimal || update.entity instanceof EntityVillager)
		{
			CannibalismNBT nbtLiving = CannibalismNBT.getNBT((EntityLivingBase) update.entityLiving);

			if(update.entityLiving.ticksExisted % 1080 == 0 && nbtLiving.getNBTValue("hunger") > 0)
				nbtLiving.changeNBTValue("hunger", -0.5F);

			if(nbtLiving.getNBTValue("hunger") <= 3)
			{
				//EAT ALL THE THINGS
			}

			if(nbtLiving.getNBTValue("hunger") <= 0)
			{
				update.entityLiving.attackEntityFrom(DamageSource.generic, 0.5F);
			}

			if(update.entityLiving instanceof EntityAnimal && !update.entityLiving.worldObj.isRemote)
			{
				CannibalismNBT nbtAnimal = CannibalismNBT.getNBT((EntityAnimal) update.entityLiving);
				EntityAnimal animal = (EntityAnimal)update.entityLiving;
				if(nbtAnimal.getNBTValue("hunger") <= 50)
				{
					//Eat grass, wheat, seed, etc.
				}
			}
			
			if(update.entityLiving instanceof EntityVillager && !update.entityLiving.worldObj.isRemote)
			{
				CannibalismNBT nbtVillager = CannibalismNBT.getNBT((EntityLivingBase) update.entityLiving);
				EntityLivingBase villager = update.entityLiving;
				if(nbtVillager.getNBTValue("hunger") == nbtVillager.getNBTValue("hunger")/2)
				{
				
				}
			}
			
		}

	}
	
	@SubscribeEvent
	public void interactionEvent(EntityInteractEvent interact)
	{
		if(interact.target instanceof EntityLivingBase && !interact.entityPlayer.worldObj.isRemote && CannibalismNBT.getNBT((EntityLivingBase)interact.target) != null)
		{
			CannibalismNBT nbt = CannibalismNBT.getNBT((EntityLivingBase)interact.target);
			interact.entityPlayer.addChatMessage(new ChatComponentText(interact.target.getClass().getName()));
			interact.entityPlayer.addChatMessage(new ChatComponentText("Sanity: " + nbt.getNBTValue("sanity")));
			interact.entityPlayer.addChatMessage(new ChatComponentText("Hunger: "+ nbt.getNBTValue("hunger")));
			interact.entityPlayer.addChatMessage(new ChatComponentText("Risk: "+ nbt.getNBTValue("risk")));
			
		}
	}
	
}