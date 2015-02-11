package sorazodia.cannibalism.mechanic.AI;

import sorazodia.cannibalism.mechanic.nbt.CannibalismNBT;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIEat extends EntityAIBase
{
	
	private EntityLivingBase entity;
	private CannibalismNBT nbt;

	public EntityAIEat(EntityLivingBase living)
	{
		entity = living;
		nbt = CannibalismNBT.getNBT(living);
	}

	@Override
	public boolean shouldExecute()
	{
		boolean execute = false;
		if(nbt.getNBTValue("hunger") <= 50)
		{
			
		}

		return execute;
	}

	@Override
	public void updateTask()
	{
	}

}
