package sorazodia.api.potionregistry;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PotionEventHandler
{

	@SubscribeEvent
	public void entityUpdate(LivingUpdateEvent event)
	{
		EntityLivingBase target = event.entityLiving;
		if(PotionNBTList.getNBT(target) != null)
		{
			PotionNBTList list = PotionNBTList.getNBT(target);
			NBTTagList potions = list.getNBTList();
			if(target.ticksExisted % 20 == 0 && list.getNBTList().tagCount() > 0)
			{
			    for(int x = 0; x < potions.tagCount(); x++)
			    {
			    	int powerLevel = list.getCompoundFromList(x).getInteger("powerLevel");
			    	float duration = list.getCompoundFromList(x).getFloat("duration");
			    	list.getCompoundFromList(x).setFloat("duration", duration - 1);
			    	PotionEffectManager.getPotionList().get(list.getCompoundFromList(x).getString("name")).effect(target, powerLevel);
			    	if(duration <= 0)
			    		potions.removeTag(x);
			    }
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void entityCreate(EntityConstructing create)
	{
		
		if(create.entity instanceof EntityLivingBase && PotionNBTList.getNBT((EntityLivingBase)create.entity) == null)
		{
			PotionNBTList.register((EntityLivingBase)create.entity);
		}
		
	}
	
}
