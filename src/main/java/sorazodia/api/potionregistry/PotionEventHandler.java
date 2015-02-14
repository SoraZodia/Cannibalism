package sorazodia.api.potionregistry;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
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
			    	int powerLevel = getNBTInt(list, x, "powerLevel");
			    	float duration = getNBTFloat(list, x, "duration");
			    	String potionName = getNBTString(list, x, "name");
			    	
			    	applyEffect(potionName, target, powerLevel);
			    	if(duration != -1) setNBTFloat(list, x, "duration", duration - 1);
			    	if(duration <= 0 && duration != -1)
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
	
	private void setNBTFloat(PotionNBTList list, int index, String key, float newValue)
	{
		getNBTCompound(list, index).setFloat(key, newValue);
	}
	
	private float getNBTFloat(PotionNBTList list, int index, String key)
	{
		return getNBTCompound(list, index).getFloat(key);
	}
	
	private int getNBTInt(PotionNBTList list, int index, String key)
	{
		return getNBTCompound(list, index).getInteger(key);
	}
	
	private String getNBTString(PotionNBTList list, int index, String key)
	{
		return getNBTCompound(list, index).getString(key);
	}
	
	private NBTTagCompound getNBTCompound(PotionNBTList list, int index)
	{
		return list.getCompoundFromList(index);
	}
	
	private void applyEffect(String potionName, EntityLivingBase target, int powerLevel)
	{
		PotionEffectManager.getPotionList().get(potionName).effect(target, powerLevel);
	}
	
}
