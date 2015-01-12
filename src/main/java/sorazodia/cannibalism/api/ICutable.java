package sorazodia.cannibalism.api;

import net.minecraft.entity.player.EntityPlayer;

public interface ICutable 
{
	
	/**
	 * Determines what happens when the knife is used on a entity.
	 */
	public void cut(EntityPlayer player);

}
