package sorazodia.cannibalism.mob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import sorazodia.cannibalism.main.Cannibalism;
import sorazodia.cannibalism.mechanic.nbt.CannibalismNBT;

public class EntityTurnedVillager extends EntityMob
{
	private EntityLivingBase attacker;
	private final SoundEvent hurtSound = SoundEvents.ENTITY_VILLAGER_HURT;
	private final SoundEvent livingSound = new SoundEvent(new ResourceLocation(Cannibalism.MODID + ":mob.wendigo.grr"));
	private int growth = 0;

	public EntityTurnedVillager(World world)
	{
		super(world);
	}
	
	@Override
    public void initEntityAI()
    {
		this.tasks.addTask(1, new EntityAIAttackMelee(this, 0.8D, true));
	    this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.6D, 32F));
    	this.tasks.addTask(1, new EntityAIWander(this, 0.3D));
		this.tasks.addTask(2, new EntityAISwimming(this));
		this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8F));
		
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityVillager.class, false));
    }
	
	@Override
	public void damageEntity(DamageSource source, float damage) {
		super.damageEntity(source, source.isFireDamage() ? damage * 1.5f : damage);
	}

	@Override
	public void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(30D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(10D);
	}

	@Override 
	public boolean attackEntityFrom(DamageSource source, float damage)
    {
		if (source.getTrueSource() instanceof EntityLivingBase)
		{
			attacker = (EntityLivingBase) source.getTrueSource();
			this.setRevengeTarget(attacker);
		}
		
		return super.attackEntityFrom(source, damage);
    }
	
	@Override
	public void setDead()
	{
		if (attacker instanceof EntityPlayer)
		{
			CannibalismNBT nbt = CannibalismNBT.getNBT((EntityPlayer) attacker);

			if (nbt != null)
			{
				nbt.changeWendigoValue(-50);
				nbt.setWedigoSpawn(false);
				nbt.setWarningEffect(true);
			}
		}
		
		super.setDead();
	}
	
	@Override
	public void onEntityUpdate()
	{
		super.onEntityUpdate();
		if (this.growth >= 10)
		{
			this.world.playSound(null, this.getPosition(), SoundEvents.ENTITY_WOLF_HOWL, SoundCategory.HOSTILE, 1, 0.5F);
			EntityWendigo wendigo = (EntityWendigo) EntityList.createEntityByIDFromName(new ResourceLocation(Cannibalism.MODID + ":wendigo"), this.world);
			wendigo.setLocationAndAngles(this.posX, this.posY, this.posZ, 0, 0);
			this.world.spawnEntity(wendigo);
			this.setDead();
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
				target.setHealth(target.getHealth()*0.8f);

				if (target.getHealth() <= 0.01F)
				{
					NBTTagCompound nbt = this.getEntityData();
					
					this.growth++;
					nbt.setInteger(EntityWendigo.nbtKey, growth);
					target.onDeath(DamageSource.causeMobDamage(this).setDamageBypassesArmor());
				}
			}

		}

		return super.attackEntityAsMob(entity);
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		
		nbt.setInteger(EntityWendigo.nbtKey, this.growth);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		
		this.growth = nbt.getInteger(EntityWendigo.nbtKey);
	}

	@Override
	public boolean canDespawn()
	{
		return this.growth >= 5 ?  false : true;
	}

	@Override
	public SoundEvent getAmbientSound()
	{
		return livingSound;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource source)
	{
		return hurtSound;
	}

	@Override
	public SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_GHAST_DEATH;
	}

}
