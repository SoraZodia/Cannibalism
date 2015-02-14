package sorazodia.api.potionregistry;

import net.minecraft.entity.EntityLivingBase;

public class TestPotion extends Potion
{
	int spam = 0;

	@Override
	public String getName()
	{
		return "test";
	}

	@Override
	public void effect(EntityLivingBase target, int powerLevel)
	{		
		System.out.println("Spam yo" + spam++);
		target.getEntityData().getTagList("Attributes", 2).getCompoundTagAt(2).setDouble("generic.movementSpeed", 1);
	}

}
