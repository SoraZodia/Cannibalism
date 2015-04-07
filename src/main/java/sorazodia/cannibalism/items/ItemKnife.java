package sorazodia.cannibalism.items;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import sorazodia.cannibalism.api.ICutable;
import sorazodia.cannibalism.config.ConfigHandler;
import sorazodia.cannibalism.config.JSONConfig;
import sorazodia.cannibalism.items.manager.ItemList;
import sorazodia.cannibalism.mechanic.nbt.CannibalismNBT;
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

		if (!interact && player.isSneaking())
		{			
			spookyEffect(world, player);
			player.swingItem();
			if (!world.isRemote)
			{
				cutEntity(player, player, getDamage(5.0F, 5.5F), player.getCommandSenderName(), ItemList.playerFlesh);
				stack.damageItem(1, player);
			}
		}
		interact = false;

		return stack;
	}

	private void setMeatName(ItemStack meat, String owner)
	{
		MeatOriginNBT.addNameToNBT(meat, owner);
		MeatOriginNBT.getNameFromNBT(meat);
	}
	
	private void spookyEffect(World world, EntityPlayer player)
	{
		if(ConfigHandler.doScream())world.playSoundEffect(player.posX, player.posY, player.posZ, "mob.ghast.scream", 1.0F, ConfigHandler.getPinch());
		if(!ConfigHandler.doScream())world.playSoundEffect(player.posX, player.posY, player.posZ, "game.player.hurt", 1.0F, ConfigHandler.getPinch());
		spawnBlood(player, world, 1);	
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entityLiving)
	{
		
		if(entityLiving.hurtTime < 1 && entityLiving.getHealth() > 0)
		{
			
			if(entityLiving.getEquipmentInSlot(3) != null && entityLiving.getEquipmentInSlot(3).getItem() instanceof ItemArmor)
			{
				ItemArmor armor = (ItemArmor) entityLiving.getEquipmentInSlot(3).getItem();
				player.swingItem();
				player.worldObj.playSoundAtEntity(player, "dig.glass", 1.0f, 1.0f);
				stack.damageItem(15 * armor.damageReduceAmount, player);
				return true;
			}
			//System.out.print(EntityList.getEntityString(entityLiving));
			if (entityLiving instanceof EntityCow) //Would love to use a switch statement here <_<
			{
				cutEntity(player, entityLiving, getDamage(2.5F, 3.0F), Items.beef, Items.leather);				
			}
			if (entityLiving instanceof EntityChicken)
			{
				cutDamage(player, entityLiving, 10.0F);
			}
			if (entityLiving instanceof EntityPig)
			{
				cutEntity(player, entityLiving, getDamage(2.5F, 3.0F), Items.porkchop);
			}
			if (entityLiving instanceof EntityVillager)
			{
				cutEntity(player, entityLiving, getDamage(5.0F, 6.0F), ItemList.villagerFlesh);
			}
			if (entityLiving instanceof EntityZombie)
			{
				cutEntity(player, entityLiving, getDamage(5.0F, 6.0F), Items.rotten_flesh);
			}
			if (entityLiving instanceof EntityPlayerMP)
			{
				cutEntity(player, entityLiving, getDamage(5.0F, 5.5F), entityLiving.getCommandSenderName(), ItemList.playerFlesh);
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
			increaseWendigo(player);
		}

		return interact;
	}
	
	@SuppressWarnings("unused")//TODO son
	private boolean checkEntity(EntityLivingBase entity) {
		String entityName = EntityList.getEntityString(entity);
		
		if (JSONConfig.contains(entityName))
			return true;
		
		return false;
	}
	
	private void cutEntity(EntityPlayer player, EntityLivingBase entity, float damage, String owner, ItemFlesh flesh)
	{
		interact = true;

		if (!entity.worldObj.isRemote)
		{
			ItemStack meat = new ItemStack(flesh);
			setMeatName(meat, owner + "'s Flesh");
			cutDamage(player, entity, damage);
			increaseWendigo(player);
			entity.entityDropItem(meat, 0.0F);
		}
	}

	private void cutEntity(EntityPlayer player, EntityLivingBase entity, float damage, Item... drops)
	{
		interact = true;
		if (!entity.worldObj.isRemote)
		{
			cutDamage(player, entity, damage);
			increaseWendigo(player);
			for (Item item : drops)
				entity.entityDropItem(new ItemStack(item), 0.0F);
		}
	}

	private void increaseWendigo(EntityPlayer player)
	{
		if (CannibalismNBT.getNBT(player) != null)
		{
			CannibalismNBT nbt = CannibalismNBT.getNBT(player);
			nbt.changeWendigoValue(10);
		}
	}

	private void cutDamage(EntityPlayer player, EntityLivingBase entity, float damage)
	{
		if (!(entity instanceof EntityPlayer))
			entity.attackEntityFrom(DamageSource.causePlayerDamage(player).setDamageBypassesArmor(), damage);

		if (!player.capabilities.isCreativeMode)
		{
			if (entity instanceof EntityPlayerMP)
			{
				entity.setHealth(entity.getHealth() - damage);
				entity.attackEntityFrom(DamageSource.causePlayerDamage(player), 0.01F);
				player.limbSwingAmount = 1.5F;
				if (entity.getHealth() < 0.01F)
				{
					entity.onDeath(DamageSource.causePlayerDamage(player));
				}
			} 
			else if (entity instanceof EntityPlayer)
			{
				player.setHealth(entity.getHealth() - damage);
				entity.attackEntityFrom(DamageSource.causePlayerDamage(player), 0.01F);
				player.limbSwingAmount = 1.5F;
				if (player.getHealth() < 0.01F)
				{
					player.onDeath(DamageSource.causePlayerDamage(player));
				}
			}
		}

	}

	private void spawnBlood(EntityLivingBase entityLiving, World world, float ySubtract)
	{
		for(int repeat = 0; repeat < ConfigHandler.getBloodAmount(); repeat++)
		{
			world.spawnParticle("reddust", entityLiving.posX + Math.random()-Math.random(), entityLiving.posY - ySubtract, entityLiving.posZ + Math.random()-Math.random(), 
					0.0F, 0.0F, 0.0F);
		}
	}

	private float getDamage(float min, float max)
	{
		float damage = min + (float)(Math.random()*((max-min) + 1));
		return damage;
	}
}