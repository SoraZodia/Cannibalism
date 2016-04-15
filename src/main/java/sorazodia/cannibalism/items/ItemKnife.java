package sorazodia.cannibalism.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sorazodia.cannibalism.api.EntityData;
import sorazodia.cannibalism.api.ICutable;
import sorazodia.cannibalism.config.ConfigHandler;
import sorazodia.cannibalism.config.JSONConfig;
import sorazodia.cannibalism.items.manager.ItemList;
import sorazodia.cannibalism.main.Cannibalism;
import sorazodia.cannibalism.mechanic.nbt.FleshNBTHelper;

public class ItemKnife extends ItemSword
{

	private boolean interact = false;
	private JSONConfig json = Cannibalism.getJson();

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
				cutPlayer(player, getDamage(5.0F, 5.5F), player.getName(), ItemList.playerFlesh);
				stack.damageItem(1, player);
			}
		}
		interact = false;

		return stack;
	}

	private void setMeatName(ItemStack meat, String owner)
	{
		FleshNBTHelper.addName(meat, owner);
		FleshNBTHelper.setItemNickname(meat);
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

			if (!player.isSneaking() && target.getEquipmentInSlot(3) != null && target.getEquipmentInSlot(3).getItem() instanceof ItemArmor)
			{
				ItemArmor armor = (ItemArmor) target.getEquipmentInSlot(3).getItem();
				player.swingItem();
				player.worldObj.playSoundAtEntity(player, "dig.glass", 1.0f, 1.0f);
				stack.damageItem(15 * armor.damageReduceAmount, player);
				return true;
			}

			if (json.checkEntity(target))
			{
				EntityData data = json.getData(target);
				cutEntity(player, target, getDamage(data.getMinDamage(), data.getMaxDamage()), data.getDrops());
			}

			else if (json.getWildCardIndex(target, player.worldObj) >= 0)
			{
				EntityData data = json.getData(target, player.worldObj);
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
				cutPlayer(player, getDamage(5.0F, 5.5F), target.getName(), ItemList.playerFlesh);
			}

		}

		if (interact == true)
		{
			player.swingItem();
			stack.damageItem(1, player);
			spawnBlood(target, target.worldObj, 0);
		}

		return interact;
	}

	private void cutPlayer(EntityPlayer player, float damage, String owner, ItemFlesh flesh)
	{
		interact = true;
		String name = StatCollector.translateToLocalFormatted("item.playerFleshOwner.name", owner);

		if (!player.worldObj.isRemote)
		{
			ItemStack meat = new ItemStack(flesh);
			setMeatName(meat, name);
			cutDamage(player, player, damage);
			player.entityDropItem(meat, 0.0F);
		}
	}

	private void cutEntity(EntityPlayer player, EntityLivingBase entity, float damage, ItemStack[] drops)
	{
		interact = true;
		if (!entity.worldObj.isRemote)
		{
			cutDamage(player, entity, damage);
			for (ItemStack item : drops)
			{
				if (item.getItem() == ItemList.villagerFlesh)
				{
		            if (entity instanceof EntityWitch)
					    FleshNBTHelper.addProfession(item, "witch");
				    if (entity instanceof EntityVillager && ((EntityVillager)entity).getProfession() == 2)
				    	FleshNBTHelper.addProfession(item, "priest");			    	
				}
				
				entity.entityDropItem(item.copy(), 0.0F);
			}
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
		for (int repeat = 0; repeat < ConfigHandler.getBloodAmount(); repeat++)
		{
			world.spawnParticle(EnumParticleTypes.REDSTONE, entityLiving.posX + Math.random() - Math.random(), entityLiving.posY - ySubtract, entityLiving.posZ + Math.random() - Math.random(), 0.0F, 0.0F, 0.0F);
		}
	}

	private float getDamage(float min, float max)
	{
		float damage = min + (float) (Math.random() * ((max - min) + 1));
		return damage;
	}
}