package sorazodia.cannibalism.mob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import sorazodia.cannibalism.main.Cannibalism;
import sorazodia.cannibalism.mechanic.nbt.CannibalismNBT;

public class EntityWendigo extends EntityMob
{
//Change attack target, attack witch + zombine
	public EntityWendigo(World world)
	{
		super(world);
		this.setSize(width, height * 2.0F);
		this.stepHeight = 1.0F;
		this.experienceValue = 50;
			
		this.tasks.addTask(0, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.5D, false));
		this.tasks.addTask(0, new EntityAIAttackOnCollide(this, EntityVillager.class, 1.5D, false));
		this.tasks.addTask(1, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(2, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAILookIdle(this));
		
		this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityVillager.class, false));
	}

	@Override
	public void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1.0D);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100D);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(42D);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(100D);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
		if (!isEntityAlive())
		{
			if (this.getAttackTarget() instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) this.getAttackTarget();
				if (CannibalismNBT.getNBT(player) != null)
				{
					CannibalismNBT nbt = CannibalismNBT.getNBT(player);
					
					nbt.setWendigoValue(0);
					nbt.setWedigoSpawn(false);
					nbt.setWarningEffect(true);
				}
			}
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