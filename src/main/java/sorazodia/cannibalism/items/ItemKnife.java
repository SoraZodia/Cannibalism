package sorazodia.cannibalism.items;

import java.util.ArrayList;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sorazodia.cannibalism.api.EntityData;
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
		if (ConfigHandler.doScream())
			world.playSoundEffect(player.posX, player.posY, player.posZ, "mob.ghast.scream", 1.0F, ConfigHandler.getPinch());
		if (!ConfigHandler.doScream())
			world.playSoundEffect(player.posX, player.posY, player.posZ, "game.player.hurt", 1.0F, ConfigHandler.getPinch());
		spawnBlood(player, world, 1);
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target)
	{

		if (target.hurtTime < 1 && target.getHealth() > 0)
		{

			if (!player.isSneaking()
					&& target.getEquipmentInSlot(3) != null
					&& target.getEquipmentInSlot(3).getItem() instanceof ItemArmor)
			{
				ItemArmor armor = (ItemArmor) target.getEquipmentInSlot(3).getItem();
				player.swingItem();
				player.worldObj.playSoundAtEntity(player, "dig.glass", 1.0f, 1.0f);
				stack.damageItem(15 * armor.damageReduceAmount, player);
				return true;
			}

			if (checkEntity(target))
			{
				EntityData data = getData(target);
				cutEntity(player, target, getDamage(data.getMinDamage(), data.getMaxDamage()), data.getDrops());
			}
			
			else if(getWildCardIndex(target, player.worldObj) >= 0) {
				EntityData data = JSONConfig.getWildcardMap().get(getWildCardIndex(target, player.worldObj));
				cutEntity(player, target, getDamage(data.getMinDamage(), data.getMaxDamage()), data.getDrops());
			}

			else if (target instanceof ICutable)
			{
				interact = true;
				ICutable cutable = (ICutable) target;
				cutable.cut(player);
			}
			
			if (target instanceof EntityPlayerMP)
			{
				cutEntity(player, target, getDamage(5.0F, 5.5F), target.getCommandSenderName(), ItemList.playerFlesh);
			} 			
			

		}

		if (interact == true)
		{
			player.swingItem();
			stack.damageItem(1, player);
			spawnBlood(target, target.worldObj, 0);
			increaseWendigo(player);
		}

		return interact;
	}

	private boolean checkEntity(EntityLivingBase entity)
	{
		return JSONConfig.contains(EntityList.getEntityString(entity));
	}

	private EntityData getData(EntityLivingBase entity)
	{
		return JSONConfig.getEntityData(EntityList.getEntityString(entity));
	}

	private int getWildCardIndex(EntityLivingBase entity, World world)
	{
		ArrayList<EntityData> wildcardList = JSONConfig.getWildcardMap();
		int index = -1;
		
		for (int x = 0; x < wildcardList.size(); x++)
		{
			if(wildcardList.get(x).getEntity(world).getClass().isInstance(entity))
				index = x;
		}
		
		return index;
	}

	private void cutEntity(EntityPlayer player, EntityLivingBase entity, float damage, String owner, ItemFlesh flesh)
	{
		interact = true;
		String name = StatCollector.translateToLocalFormatted("item.playerFleshOwner.name", owner);
        System.out.println(name);
		if (!entity.worldObj.isRemote)
		{
			ItemStack meat = new ItemStack(flesh);
			//setMeatName(meat, owner + "'s Flesh");
			setMeatName(meat, name);
			cutDamage(player, entity, damage);
			increaseWendigo(player);
			entity.entityDropItem(meat, 0.0F);
		}
	}

	private void cutEntity(EntityPlayer player, EntityLivingBase entity, float damage, Item[] drops)
	{
		interact = true;
		if (!entity.worldObj.isRemote)
		{
			cutDamage(player, entity, damage);
			increaseWendigo(player);
			for (Item item : drops)
			{
				entity.entityDropItem(new ItemStack(item), 0.0F);
			}
		}
	}

	private void increaseWendigo(EntityPlayer player)
	{
		if (CannibalismNBT.getNBT(player) != null
				&& ConfigHandler.getMyth() == true)
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
			} else if (entity instanceof EntityPlayer)
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
		for (int repeat = 0; repeat < ConfigHandler.getBloodAmount(); repeat++)
		{
			world.spawnParticle("reddust", entityLiving.posX + Math.random()
					- Math.random(), entityLiving.posY - ySubtract, entityLiving.posZ
					+ Math.random() - Math.random(), 0.0F, 0.0F, 0.0F);
		}
	}

	private float getDamage(float min, float max)
	{
		float damage = min + (float) (Math.random() * ((max - min) + 1));
		return damage;
	}
}