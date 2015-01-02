package sorazodia.cannibalism.mechanic.AI;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIEat extends EntityAIBase
{
	// May not be needed
	private EntityLivingBase entity;

	public EntityAIEat(EntityLivingBase living)
	{
		entity = living;
	}

	@Override
	public boolean shouldExecute()
	{
		boolean execute = false;
		if (entity.ticksExisted % 20 == 0)
			execute = true;
		return execute;
	}

	@Override
	public void updateTask()
	{
	}

}
