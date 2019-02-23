package sorazodia.cannibalism.mob;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
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
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
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
	public static final String nbtKey = "wendigoStrength";
	private int addictionalDamage = 0;
	
	public EntityWendigo(World world)
	{
		super(world);
		this.setSize(width, height * 2.0F);
		this.stepHeight = 1.0F;
		this.experienceValue = 100;

		
	}
	
	@Override
    public void initEntityAI()
    {
		this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, true));
	    this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 1.0D, 32F));
    	this.tasks.addTask(1, new EntityAIWander(this, 0.7D));
		this.tasks.addTask(2, new EntityAISwimming(this));
		this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8F));
		
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityVillager.class, false));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityWitch.class, true));
		this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<>(this, EntityZombie.class, true));
    }
	


	@Override
	public void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.7D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(42D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(100D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0D);
	}

	@Override 
	public boolean attackEntityFrom(DamageSource source, float damage)
    {
		if (source.getTrueSource() instanceof Entity)
		{
			this.attacker = (EntityLivingBase) source.getTrueSource();
			this.setRevengeTarget(this.attacker);
		}
		
		return super.attackEntityFrom(source, damage);
    }
	
	@Override
	public void setDead()
	{
		if (this.attacker instanceof EntityPlayer)
		{
			CannibalismNBT nbt = CannibalismNBT.getNBT((EntityPlayer) this.attacker);

			if (nbt != null)
			{
				nbt.setWendigoValue(0);
				nbt.setWedigoSpawn(false);
				nbt.setWarningEffect(true);
				nbt.setSpawnChance(CannibalismNBT.WENDIGO_SPAWN_BASE);
			}
		}
		
		super.setDead();
	}

	@Override
	public boolean attackEntityAsMob(Entity entity)
	{	
		
		if (entity instanceof EntityLivingBase)
		{
			EntityLivingBase target = (EntityLivingBase) entity;
			NBTTagCompound nbt = this.getEntityData();
			float postDamageHealth = target.getHealth() - (4 + this.addictionalDamage);

			if (postDamageHealth <= 0) {
				this.addictionalDamage++;
				this.setHealth(this.getHealth() + 2);
				nbt.setInteger(nbtKey, addictionalDamage);
				target.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor(), Float.MAX_VALUE);
			}
			else {
				target.attackEntityFrom(DamageSource.causeMobDamage(this), 0);
				target.setHealth(postDamageHealth);
			}

		}

		return super.attackEntityAsMob(entity);
	}
	
	@Nullable
	@Override
	public ResourceLocation getLootTable() {
		return Cannibalism.wendigoLootTable;
	}

	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		
		nbt.setInteger(nbtKey, addictionalDamage);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		
		this.addictionalDamage = nbt.getInteger(nbtKey);
	}

	@Override
	public boolean canDespawn()
	{
		return false;
	}

	@Override
	public SoundEvent getAmbientSound()
	{
		return this.livingSound;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource source)
	{
		return this.hurtSound;
	}

	@Override
	public SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_GHAST_DEATH;
	}

}