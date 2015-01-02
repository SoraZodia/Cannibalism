package sorazodia.cannibalism.mechanic.AI;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAICannibalize extends EntityAIBase
{

	private EntityLivingBase entity;
	
	public EntityAICannibalize(EntityLivingBase living)
	{
	entity = living;	
	}
	
	@Override
	public boolean shouldExecute() 
	{
		boolean execute = false;
		return execute;
	}

	@Override
	public void updateTask()
	{
	}

}
