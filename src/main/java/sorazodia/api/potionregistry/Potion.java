package sorazodia.api.potionregistry;

import net.minecraft.entity.EntityLivingBase;

/**
 * Creates new potion effects
 * 
 * @author SoraZodia
 *
 */
public abstract class Potion
{
	
//	/**
//	 * Determine how long the effect will last
//	 * @return effect duration
//	 */
//	float getDuration();
//	
//	/**
//	 * Determine the effect's strength.
//	 * @return the power level
//	 */
//	float getPowerLevel();
	
	/**
	 * Gets the name of the effect.
	 * @return the name of the potion effect
	 */
	public abstract String getName();
	
	/**
	 * Will cause its target, namely an entity, to be affected as dictated by the method body.
	 * Ex: effect() can have algorithms that causes the target to become an potato.
	 * 
	 */
	public abstract void effect(EntityLivingBase target, int powerLevel);

}
