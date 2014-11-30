package sorazodia.cannibalism.items;

import sorazodia.cannibalism.api.ICutable;
import sorazodia.cannibalism.config.ConfigHandler;
import sorazodia.cannibalism.items.manager.ItemList;
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

public class ItemKnife extends ItemSword{

	private boolean interact = false;

	public ItemKnife(ToolMaterial material) {
		super(material);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		player.setItemInUse(stack, getMaxItemUseDuration(stack));
		if(!world.isRemote){
			int damage = 1;
			if(player.isSneaking() && !interact){
				player.swingItem();
				cutDamage(player, player, getDamage(8.0F,8.0F));
				spawnBlood(player, world);
				playSound(world, player);
				player.dropItem(ItemList.playerFlesh, 1);
				stack.damageItem(damage++, player);
			}
			interact = false;
		}
		return stack;
	}	

	private void playSound(World world, EntityPlayer player){
		if(ConfigHandler.doScream())world.playSoundEffect(player.posX, player.posY, player.posZ, "mob.ghast.scream", 1.0F, ConfigHandler.getPinch());
		if(!ConfigHandler.doScream())world.playSoundEffect(player.posX, player.posY, player.posZ, "game.player.hurt", 1.0F, ConfigHandler.getPinch());
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entityLiving)
	{
		int damage = 1;

		if(!entityLiving.worldObj.isRemote){
			if(entityLiving instanceof EntityCow){
				cutDamage(player, entityLiving, getDamage(3.0F,4.0F));
				entityLiving.dropItem(Items.beef, 1);
				entityLiving.dropItem(Items.leather, 1);
				interact = true;
			}
			if(entityLiving instanceof EntityChicken){
				cutDamage(player, entityLiving, 4.0F);
				interact = true;
			}
			if(entityLiving instanceof EntityPig){
				cutDamage(player, entityLiving, getDamage(3.0F,4.0F));
				entityLiving.dropItem(Items.porkchop, 1);
				interact = true;
			}
			if(entityLiving instanceof EntityVillager){
				cutDamage(player, entityLiving, getDamage(5.0F,6.0F));
				entityLiving.dropItem(ItemList.villagerFlesh, 1);
				interact = true;
			} 
			if(entityLiving instanceof EntityZombie){
				cutDamage(player, entityLiving, getDamage(5.0F,6.0F));
				entityLiving.dropItem(Items.rotten_flesh, 1);
				interact = true;
			}
			if(entityLiving instanceof EntityPlayer){
				cutDamage(player, entityLiving, getDamage(5.0F,6.0F));
				player.dropItem(ItemList.playerFlesh, 1);
				interact = true;
			}
			if(entityLiving instanceof ICutable){
				ICutable target = (ICutable)entityLiving;
				target.cut();
				interact = true;
			}
			if(interact){
				stack.damageItem(damage++, player);
				spawnBlood(entityLiving, entityLiving.worldObj);
			}
		}

		return interact;
	}

	private void cutDamage(EntityPlayer player, EntityLivingBase entity, float damage){
		if(!(entity instanceof EntityPlayer)) 
			entity.attackEntityFrom(DamageSource.causePlayerDamage(player).setDamageBypassesArmor(), damage);
		
		if(!player.capabilities.isCreativeMode && entity instanceof EntityPlayer){
			player.setHealth(entity.getHealth()-damage);
			player.limbSwingAmount = 1.5F;
		}
		
		if(player.getHealth() < 0.1F) player.onDeath(DamageSource.causePlayerDamage(player));
	}

	private void spawnBlood(EntityLivingBase entityLiving, World world){
		for(int repeat = 0; repeat < 8; repeat++){
			world.spawnParticle("reddust", entityLiving.posX+Math.random(), entityLiving.posY-Math.random(), entityLiving.posZ+Math.random(), 
					0.0F, 0.0F,0.0F);
		}
	}

	private float getDamage(float min, float max){
		float damage = min + (float)(Math.random()*((max-min) + 1));
		return damage;
	}
}
