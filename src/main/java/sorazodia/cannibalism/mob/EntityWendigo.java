package sorazodia.cannibalism.mob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import sorazodia.cannibalism.mechanic.nbt.CannibalismNBT;

public class EntityWendigo extends EntityMob
{

	public EntityWendigo(World world)
	{
		super(world);
		this.yOffset *= 8.5F;
		this.setSize(width * 4F, height * 8.5F);
	}

	@Override
	public void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(10D);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100D);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40D);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1000D);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
		if (!isEntityAlive())
		{
			if (this.entityToAttack instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) this.entityToAttack;
				if (CannibalismNBT.getNBT(player) != null)
					CannibalismNBT.getNBT(player).setWedigoSpawn(false);
			}
		}
	}

	@Override
	public void attackEntity(Entity entity, float time)
	{
		if (attackTime <= 0 && time < 2.0F)
		{
			attackTime = 20;
			attackEntityAsMob(entity);
		}
	}

	public boolean attackEntityAsMob(Entity target)
	{
		boolean attacked = super.attackEntityAsMob(target);
		if (attacked)
		{
			target.motionX += 2.0;
			target.motionY += 1.0;
			target.motionZ += 2.0;
		}

		return attacked;
	}

	public int getExperiencePoints(EntityPlayer player)
	{
		return 15;
	}

}
