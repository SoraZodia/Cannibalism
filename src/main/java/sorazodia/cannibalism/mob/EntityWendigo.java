package sorazodia.cannibalism.mob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import sorazodia.cannibalism.main.Cannibalism;
import sorazodia.cannibalism.mechanic.nbt.CannibalismNBT;

public class EntityWendigo extends EntityMob
{
	
	private EntityLivingBase attacker;
	private final SoundEvent hurtSound = new SoundEvent(new ResourceLocation(Cannibalism.MODID + ":mob.wendigo.hurt"));
	private final SoundEvent livingSound = new SoundEvent(new ResourceLocation(Cannibalism.MODID + ":mob.wendigo.grr"));
	
//Change attack target, attack witch + zombine
	public EntityWendigo(World world)
	{
		super(world);
		this.setSize(width, height * 2.0F);
		this.stepHeight = 1.0F;
		this.experienceValue = 50;

		
	}
	
	@Override
    public void initEntityAI()
    {
    	this.tasks.addTask(1, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(2, new EntityAISwimming(this));
		this.tasks.addTask(3, new EntityAILookIdle(this));
		
		this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityVillager.class, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityWitch.class, false));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityZombie.class, false));
    }


	@Override
	public void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1.0D);
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100D);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(42D);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(100D);
	}

	@Override 
	public boolean attackEntityFrom(DamageSource source, float damage)
    {
		if (source.getEntity() instanceof Entity)
		{
			attacker = (EntityLivingBase) source.getEntity();
			this.setRevengeTarget(attacker);
		}
		
		return super.attackEntityFrom(source, damage);
    }
	
//	@Override
//	public void setRevengeTarget(EntityLivingBase target)
//    {
//		super.setRevengeTarget(target);
//    }
	
	@Override
	public void setDead()
	{
		if (attacker instanceof EntityPlayer)
		{
			CannibalismNBT nbt = CannibalismNBT.getNBT((EntityPlayer) attacker);

			if (nbt != null)
			{
				nbt.setWendigoValue(0);
				nbt.setWedigoSpawn(false);
				nbt.setWarningEffect(true);
			}
		}
		
		super.setDead();
	}
	
	@Override
	public void onUpdate()
	{	
		super.onUpdate();
		
		this.stepHeight = 1.0F;
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
	public SoundEvent getAmbientSound()
	{
		return livingSound;
	}

	@Override
	public SoundEvent getHurtSound()
	{
		return hurtSound;
	}

	@Override
	public SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_GHAST_DEATH;
	}

}