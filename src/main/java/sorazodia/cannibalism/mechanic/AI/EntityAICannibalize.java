package sorazodia.cannibalism.mechanic.AI;

import java.util.List;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.AxisAlignedBB;
import sorazodia.cannibalism.mechanic.nbt.CannibalismNBT;

public class EntityAICannibalize extends EntityAIBase
{

	private EntityCreature entity;
	private CannibalismNBT nbt;
	private float hunger;
	private float sanity;
	
	public EntityAICannibalize(EntityCreature living)
	{
		entity = living;	
		nbt = CannibalismNBT.getNBT(entity);
		hunger = nbt.getNBTValue("hunger");
		sanity = nbt.getNBTValue("sanity");
	}
	
	@Override
	public boolean shouldExecute() 
	{
		boolean execute = false;
		return execute;
	}

	@Override
	public boolean continueExecuting()
	{
		boolean shouldContinue = false;
		if(entity instanceof EntityVillager) shouldContinue = villagerCanniCheck();
		if(entity instanceof EntityAnimal) shouldContinue = animalCanniCheck();
		return shouldContinue;
	}
	
	@Override
	public void updateTask()
	{
		if(entity instanceof EntityVillager) tryToGetFood(entity, EntityLivingBase.class);
		if(entity instanceof EntityAnimal) tryToGetFood(entity, entity.getClass());
	}
	
	private void tryToGetFood(EntityCreature entity, Class<?> targetEntityClass)
	{
		List<EntityCreature> targets = getEntityWithinRange(entity, targetEntityClass);
		entity.setAttackTarget(targets.get(0));
		
	}
	
	private boolean villagerCanniCheck()
	{
		boolean doDesperateEating = false;
		
		if(hunger < 3 && sanity < 10)
			doDesperateEating = true;
		
		return doDesperateEating;
	}
	
	private boolean animalCanniCheck()
	{
		boolean doDesperateEating = false;
		
		if(hunger < 3)
			doDesperateEating = true;
		
		return doDesperateEating;
	}
	
	@SuppressWarnings("unchecked")
	private List<EntityCreature> getEntityWithinRange(EntityCreature entity, Class<?> targets)
	{
		return entity.worldObj.getEntitiesWithinAABB(targets, AxisAlignedBB.getBoundingBox(entity.posX - 5, entity.posY, entity.posZ - 5, entity.posX + 5, entity.posY, entity.posZ + 5));
	}

}
