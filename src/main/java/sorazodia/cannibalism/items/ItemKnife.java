package sorazodia.cannibalism.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import sorazodia.cannibalism.api.ICutable;
import sorazodia.cannibalism.config.ConfigHandler;
import sorazodia.cannibalism.items.manager.ItemList;
import sorazodia.cannibalism.mechanic.nbt.MeatOriginNBT;

public class ItemKnife extends ItemSword
{

	private boolean interact = false;
	
	public ItemKnife(ToolMaterial material) 
	{
		super(material);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		player.setItemInUse(stack, getMaxItemUseDuration(stack));
		if(player.isSneaking() && !interact){
			spawnBlood(player, world, 1);
			playSound(world, player);
			player.swingItem();
		}
		if(!world.isRemote && player.isSneaking() && !interact){		
			ItemStack playerMeat = new ItemStack(ItemList.playerFlesh, 1);
			setMeatName(playerMeat, player.getDisplayName() + "'s Flesh");			
			dropItems(player, player, getDamage(5.0F, 5.5F), playerMeat);			
			stack.damageItem(1, player);
		}
		interact = false;

		return stack;
	}	

	private void setMeatName(ItemStack meat, String name)
	{
		MeatOriginNBT.addNameToNBT(meat, name);
		MeatOriginNBT.getNameFromNBT(meat);
	}
	
	private void playSound(World world, EntityPlayer player)
	{
		if(ConfigHandler.doScream())world.playSoundEffect(player.posX, player.posY, player.posZ, "mob.ghast.scream", 1.0F, ConfigHandler.getPinch());
		if(!ConfigHandler.doScream())world.playSoundEffect(player.posX, player.posY, player.posZ, "game.player.hurt", 1.0F, ConfigHandler.getPinch());
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entityLiving)
	{
		
		if(entityLiving.hurtTime < 1 && entityLiving.getHealth() > 0)
		{
			if (entityLiving instanceof EntityCow)
			{
				dropItems(player, entityLiving, getDamage(2.5F, 3.0F), Items.beef, Items.leather);				
			}
			if (entityLiving instanceof EntityChicken)
			{
				cutDamage(player, entityLiving, 10.0F);
			}
			if (entityLiving instanceof EntityPig)
			{
				dropItems(player, entityLiving, getDamage(2.5F, 3.0F), Items.porkchop);
			}
			if (entityLiving instanceof EntityVillager)
			{
				dropItems(player, entityLiving, getDamage(5.0F, 6.0F), ItemList.villagerFlesh);
			}
			if (entityLiving instanceof EntityZombie)
			{
				dropItems(player, entityLiving, getDamage(5.0F, 6.0F), Items.rotten_flesh);
			}
			if (entityLiving instanceof EntityPlayer)
			{
				dropItems(player, entityLiving, getDamage(5.0F, 5.5F), ItemList.playerFlesh);
			}
			if (entityLiving instanceof ICutable)
			{
				interact = true;
				ICutable target = (ICutable) entityLiving;
				target.cut(player);
			}
	
	  }

		if (interact)
		{
			player.swingItem();
			stack.damageItem(1, player);
			spawnBlood(entityLiving, entityLiving.worldObj, 0);
		}

		return interact;
	}
	
	private void dropItems(EntityPlayer player, EntityLivingBase entity, float damage, ItemStack... drops)
	{
		interact = true;
		if (!entity.worldObj.isRemote)
		{
		cutDamage(player, entity, damage);
		for(ItemStack item: drops)
			entity.entityDropItem(item, 0.0F);
		}
	}
	
	private void dropItems(EntityPlayer player, EntityLivingBase entity, float damage, Item... drops)
	{
		interact = true;
		if(!entity.worldObj.isRemote)
		{
		cutDamage(player, entity, damage);
		for(Item item: drops)
			entity.entityDropItem(new ItemStack(item), 0.0F);
		}
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