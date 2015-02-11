package sorazodia.api.potionregistry;

import java.util.HashMap;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagList;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;

public class PotionEffectManager
{
	private static HashMap<String, Potion> potionList= new HashMap<String, Potion>();
	
	protected static void addNewPotion(String modID, Potion newPotion)
	{
		String unlocalizedPotionName = modID + ":" + newPotion.getName();
		
		if(!potionList.containsKey(unlocalizedPotionName))
		{
			potionList.put(unlocalizedPotionName, newPotion);
		}
		else if(potionList.containsKey(unlocalizedPotionName))
		{
			FMLLog.log(Level.ERROR,"[PotionEffectManager] Somehow, %s is already registered. It was not added.", newPotion.getName());
			FMLLog.log(Level.DEBUG, "[PotionEffectManager] $s's unlocalized name: %s", newPotion.getName(), unlocalizedPotionName);
		}
		else
		{
			FMLLog.log(Level.ERROR,"[PotionEffectManager] Unexpected Error in Registration");
		}
	}
	
	protected static void applyEffect(EntityLivingBase target, String modID, String potionName, int duration, int powerLevel)
	{
		String unlocalizatedName = modID + ":" + potionName;
		if(potionList.containsKey(unlocalizatedName))
		{
			PotionNBTList list = PotionNBTList.getNBT(target);
			checkEffectOverwrite(list.getNBTList(), unlocalizatedName);
	        list.addPotions(unlocalizatedName, duration, powerLevel);
		}
		else
		{
			FMLLog.log(Level.ERROR, "%s in %s does not exist!", potionName, modID);
		}
	}
	
	private static void checkEffectOverwrite(NBTTagList list, String potionName)
	{
		for(int x = 0; x < list.tagCount(); x++)
			if(list.getCompoundTagAt(x).getString("name").equals(potionName))
				list.removeTag(x);
	}
	
	protected static HashMap<String, Potion> getPotionList()
	{
		return potionList;
	}
}
