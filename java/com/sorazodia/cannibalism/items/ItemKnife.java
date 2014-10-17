package com.sorazodia.cannibalism.items;

import net.minecraft.entity.EntityLiving;
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

import com.sorazodia.cannibalism.config.ConfigHandler;
import com.sorazodia.cannibalism.items.manager.ItemList;
import com.sorazodia.cannibalism.main.Cannibalism;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemKnife extends ItemSword{

	private float screamPinch = (float)0.7;
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
				cutDamage(player, player, 6.0F);
				//spawnBlood(player, world);
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
		World world = entityLiving.worldObj;
		
		if(!entityLiving.worldObj.isRemote){
			if(entityLiving instanceof EntityCow){
				cutDamage(player, entityLiving, 6.0F);
				entityLiving.dropItem(Items.beef, 1);
				entityLiving.dropItem(Items.leather, 1);
				stack.damageItem(damage++, player);
				interact = true;
			}
			if(entityLiving instanceof EntityChicken){
				cutDamage(player, entityLiving, 4.0F);
				stack.damageItem(damage++, player);
				interact = true;
			}
			if(entityLiving instanceof EntityPig){
				cutDamage(player, entityLiving, 6.0F);
				entityLiving.dropItem(Items.porkchop, 1);
				stack.damageItem(damage++, player);
				interact = true;
			}
			if(entityLiving instanceof EntityVillager){
				cutDamage(player, entityLiving, 6.0F);
				entityLiving.dropItem(ItemList.villagerFlesh, 1);
				stack.damageItem(damage++, player);
				interact = true;
			} 
			if(entityLiving instanceof EntityZombie){
				cutDamage(player, entityLiving, 6.0F);
				entityLiving.dropItem(Items.rotten_flesh, 1);
				stack.damageItem(damage++, player);
				interact = true;
			}
			if(entityLiving instanceof EntityPlayer){
				cutDamage(player, entityLiving, 6.0F);
				player.dropItem(ItemList.playerFlesh, 1);
				stack.damageItem(damage++, player);
				interact = true;
			}
		}
		
		return interact;
    }
	
	private void cutDamage(EntityPlayer player, EntityLivingBase entity, float damage){
		entity.attackEntityFrom(DamageSource.causePlayerDamage(player).setDamageBypassesArmor(), damage);
	}
	
	private void spawnBlood(EntityLivingBase entityLiving, World world){
		for(int x = 0 ; x < 10 ; x++){
			entityLiving.worldObj.spawnParticle("largesmoke", entityLiving.posX + Math.random(), 
					entityLiving.posY + Math.random(), entityLiving.posZ + Math.random(), 0.0D, 0.0D, 0.0D);
		}
	}
}
