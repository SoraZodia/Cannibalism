package sorazodia.cannibalism.events;

import net.minecraft.entity.passive.EntityAnimal;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import sorazodia.cannibalism.mechanic.CannibalismNBT;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityEvents 
{

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void entityCreateEvent(EntityConstructing create)
	{
		if(create.entity instanceof EntityAnimal && CannibalismNBT.getNBT((EntityAnimal)create.entity) == null)
		{
			CannibalismNBT.register((EntityAnimal) create.entity);
		}
	}

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event)
	{

	}
	
	@SubscribeEvent
	public void updateEntityEvent(LivingUpdateEvent update)
	{
		if(update.entityLiving instanceof EntityAnimal && !update.entityLiving.worldObj.isRemote){
			CannibalismNBT nbt = CannibalismNBT.getNBT((EntityAnimal) update.entityLiving);
		}
	     
	}
	
}
