package sorazodia.cannibalism.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import sorazodia.api.potionregistry.PseudoPotion;
import sorazodia.cannibalism.api.ICutable;
import sorazodia.cannibalism.config.ConfigHandler;
import sorazodia.cannibalism.items.manager.ItemList;
import sorazodia.cannibalism.main.Cannibalism;
import sorazodia.cannibalism.mechanic.nbt.MeatOriginNBT;

public class ItemKnife extends ItemSword
{

	private boolean interact = false;
    private float tick;
	
	public ItemKnife(ToolMaterial material) 
	{
		super(material);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		player.setItemInUse(stack, getMaxItemUseDuration(stack));
		if(player.isSneaking() && world.isRemote && !interact){
			spawnBlood(player, world, 1);
			player.swingItem();
		}
		if(!world.isRemote && player.isSneaking() && !interact){
			cutDamage(player, player, getDamage(5.5F,5.5F));
			playSound(world, player);
			ItemStack playerMeat = new ItemStack(ItemList.playerFlesh, 1);
			MeatOriginNBT.addNameToNBT(playerMeat, player.getDisplayName());
			MeatOriginNBT.getNameFromNBT(playerMeat);
			PseudoPotion.applyEffect(player, Cannibalism.MODID,"test", 10, 1);
			player.entityDropItem(playerMeat, 0.0F);
			stack.damageItem(1, player);
		}
		interact = false;

		return stack;
	}	

	private void playSound(World world, EntityPlayer player)
	{
		if(ConfigHandler.doScream())world.playSoundEffect(player.posX, player.posY, player.posZ, "mob.ghast.scream", 1.0F, ConfigHandler.getPinch());
		if(!ConfigHandler.doScream())world.playSoundEffect(player.posX, player.posY, player.posZ, "game.player.hurt", 1.0F, ConfigHandler.getPinch());
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entityLiving)
	{
		
		if(player.ticksExisted - tick > 9)
		{
			tick = player.ticksExisted;
			if(entityLiving instanceof EntityCow)
			{
				interact = true;
				if(!entityLiving.worldObj.isRemote)
				{
					cutDamage(player, entityLiving, getDamage(2.5F,3.0F));
					entityLiving.dropItem(Items.beef, 1);
					entityLiving.dropItem(Items.leather, 1);
				}
			}
			if(entityLiving instanceof EntityChicken)
			{
				interact = true;
				if(!entityLiving.worldObj.isRemote) cutDamage(player, entityLiving, 10.0F);
			}
			if(entityLiving instanceof EntityPig)
			{
				interact = true;
				if(!entityLiving.worldObj.isRemote)
				{
					cutDamage(player, entityLiving, getDamage(2.5F,3.0F));
					entityLiving.dropItem(Items.porkchop, 1);
				}
			}
			if(entityLiving instanceof EntityVillager)
			{
				interact = true;
				if(!entityLiving.worldObj.isRemote)
				{
					cutDamage(player, entityLiving, getDamage(5.0F,6.0F));
					entityLiving.dropItem(ItemList.villagerFlesh, 1);
				}
			} 
			if(entityLiving instanceof EntityZombie)
			{
				interact = true;
				if(!entityLiving.worldObj.isRemote)
				{
					cutDamage(player, entityLiving, getDamage(5.0F,6.0F));
					entityLiving.dropItem(Items.rotten_flesh, 1);
				}
			}
			if(entityLiving instanceof EntityPlayer)
			{
				interact = true;
				if(!entityLiving.worldObj.isRemote)
				{
					cutDamage(player, entityLiving, getDamage(5.0F,6.0F));
					player.dropItem(ItemList.playerFlesh, 1);
				}
			}
			if(entityLiving instanceof ICutable)
			{
				interact = true;
				ICutable target = (ICutable)entityLiving;
				if(!entityLiving.worldObj.isRemote) target.cut(player);
			}
		}
		
		if(interact && !player.worldObj.isRemote)
		{
			stack.damageItem(1, player);
		}
		if(interact && player.worldObj.isRemote)
		{
			player.swingItem();
			spawnBlood(entityLiving, entityLiving.worldObj, 0);
		}
		
		return interact;
	}

	private void cutDamage(EntityPlayer player, EntityLivingBase entity, float damage)
	{
		if(!(entity instanceof EntityPlayer)) 
			entity.attackEntityFrom(DamageSource.causePlayerDamage(player).setDamageBypassesArmor(), damage);

		if(!player.capabilities.isCreativeMode && entity instanceof EntityPlayer)
		{
			player.setHealth(entity.getHealth()-damage);
			entity.attackEntityFrom(DamageSource.causePlayerDamage(player), 0.01F);
			player.limbSwingAmount = 1.5F;
		}

		if(player.getHealth() < 0.01F)
		{
			player.onDeath(DamageSource.causePlayerDamage(player));
		}
	}

	private void spawnBlood(EntityLivingBase entityLiving, World world, float yDeceaseBy)
	{
		for(int repeat = 0; repeat < ConfigHandler.getBloodAmount(); repeat++)
		{
			world.spawnParticle("reddust", entityLiving.posX + Math.random()-Math.random(), entityLiving.posY - yDeceaseBy, entityLiving.posZ + Math.random()-Math.random(), 
					0.0F, 0.0F,0.0F);
		}
	}

	private float getDamage(float min, float max)
	{
		float damage = min + (float)(Math.random()*((max-min) + 1));
		return damage;
	}
}