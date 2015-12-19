package sorazodia.cannibalism.mob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import sorazodia.cannibalism.main.Cannibalism;
import sorazodia.cannibalism.mechanic.nbt.CannibalismNBT;

public class EntityWendigo extends EntityMob
{

	public EntityWendigo(World world)
	{
		super(world);
		this.setSize(width, height * 2.0F);
	}

	@Override
	public void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1D);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100D);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(42D);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(100D);
	}

	@Override
	public void onUpdate()
	{
		//super.onUpdate();
		if (!isEntityAlive())
		{
			if (this.entityToAttack instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) this.entityToAttack;
				if (CannibalismNBT.getNBT(player) != null)
					CannibalismNBT.getNBT(player).setWedigoSpawn(false);
			}
		}
		else
		{
			this.stepHeight = 1.0F;
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		boolean attacked = false;
		if (entity instanceof EntityLivingBase)
		{
			EntityLivingBase target = (EntityLivingBase) entity;

			attacked = target.attackEntityFrom(DamageSource.causeMobDamage(this), 1.0F);
			if (attacked)
			{
				target.setHealth(target.getHealth() - 10);
				target.motionY *= 2;
				target.motionX *= 2;
				target.motionZ *= 2;

				if (target.getHealth() <= 0.01F)
					target.onDeath(DamageSource.causeMobDamage(this).setDamageBypassesArmor());
			}

		}

		return attacked;
	}

	@Override
	public boolean canDespawn()
	{
		return false;
	}

	@Override
	public int getExperiencePoints(EntityPlayer player)
	{
		return 15;
	}

	@Override
	public String getLivingSound()
	{
		return Cannibalism.MODID + ":mob.wendigo.grr";
	}

	@Override
	public String getHurtSound()
	{
		return Cannibalism.MODID + ":mob.wendigo.hurt";
	}

	@Override
	public String getDeathSound()
	{
		return "mob.ghast.death";
	}

}
