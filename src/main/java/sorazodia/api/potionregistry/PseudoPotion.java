package sorazodia.api.potionregistry;

import net.minecraft.entity.EntityLivingBase;

public class PseudoPotion 
{

	public static void registerPotion(String modID, Potion potion)
	{
		PotionEffectManager.addNewPotion(modID, potion);
	}

	public static void applyEffect(EntityLivingBase target, String modID, String potionName, int duration, int powerLevel)
	{
		PotionEffectManager.applyEffect(target, modID, potionName, duration, powerLevel);
	}
	
}