package sorazodia.cannibalism.mechanic.events;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import sorazodia.cannibalism.config.ConfigHandler;
import sorazodia.cannibalism.main.Cannibalism;
import sorazodia.cannibalism.mechanic.nbt.CannibalismNBT;
import sorazodia.cannibalism.mob.EntityWendigo;

public class EntityNBTEvents
{
	
	public static final float WENDIGO_LEVEL_CAP = 250;
	
	@SubscribeEvent
	public void playerUpdate(PlayerTickEvent event)
	{

		if (ConfigHandler.allowMyth())
		{
			EntityPlayer player = event.player;
			CannibalismNBT nbt = CannibalismNBT.getNBT(player);
			float wendigoLevel = nbt.getWendigoValue();
			if (wendigoLevel < 100)
			{
				nbt.setWarningEffect(true);
				nbt.setWedigoSpawn(false);
			}
			
			if (event.player.world.isRemote) // client side
			{
				addClientEffect(player, wendigoLevel, nbt);
			}
			else // server side
			{
				addServerEffect(player, wendigoLevel, nbt);
				boolean holdingHeirloom = (player.getHeldItemOffhand().getTagCompound() != null && player.getHeldItemOffhand().getTagCompound().getCompoundTag(Cannibalism.MODID).getString(InteractionEvent.HEIRLOOM_NBT_TAG).length() > 0)
						                  || (player.getHeldItemMainhand().getTagCompound() != null && player.getHeldItemMainhand().getTagCompound().getCompoundTag(Cannibalism.MODID).getString(InteractionEvent.HEIRLOOM_NBT_TAG).length() > 0);
				if (nbt.hasHeart() && holdingHeirloom)
					player.attackEntityFrom(DamageSource.WITHER, 1);
				
				if (player.getFoodStats().getFoodLevel() <= 0 && nbt.getWendigoValue() >= 150) {
					player.getFoodStats().setFoodLevel(1);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void playerLevelIncrease(LevelingEvent event) 
	{
		if (event.getPlayer().isPresent()) 
		{
			EntityPlayer player = event.getPlayer().get();
			if (!player.getEntityWorld().isRemote &&
				player.getHeldItemOffhand().getTagCompound() != null &&
				player.getHeldItemOffhand().getTagCompound().getCompoundTag(Cannibalism.MODID).getString(InteractionEvent.HEIRLOOM_NBT_TAG).length() > 0) 
			{
				event.setCanceled(true);
			}
			
		}
	}
	
	private void removeArmor(EntityPlayer player, EntityEquipmentSlot armorSlot) 
	{
		if (player.getItemStackFromSlot(armorSlot) != ItemStack.EMPTY)
		{
			player.inventory.addItemStackToInventory(player.getItemStackFromSlot(armorSlot));
			player.inventory.armorInventory.set(armorSlot.getIndex(), ItemStack.EMPTY);
		}
	}
	
	private void addClientEffect(EntityPlayer player, float wendigoLevel, CannibalismNBT nbt)
	{
		if (wendigoLevel >= 100)
		{
			player.stepHeight = 1;
		}
	}
	
	private void addServerEffect(EntityPlayer player, float wendigoLevel, CannibalismNBT nbt)
	{
		if (wendigoLevel >= 25 && wendigoLevel < 100)
		{
			player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 250));
			player.addExhaustion(0.02F);
		}
		if (wendigoLevel >= 50)
		{
			player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 250, (wendigoLevel >= 100) ? 2 : 1));
			player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 250));
			player.addExhaustion(0.04F);
		}
		if (wendigoLevel >= 100)
		{
			if (nbt.doWarningEffect())
			{
				player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 250));
				player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 250));
				nbt.setWarningEffect(false);
			}	
			
			player.getCooldownTracker().setCooldown(player.getHeldItem(player.getActiveHand()).getItem(), 0);
			removeArmor(player, EntityEquipmentSlot.LEGS);
		}
		if (wendigoLevel >= 150)
		{
			player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 250, (wendigoLevel < 240) ? 1 : 2));
			removeArmor(player, EntityEquipmentSlot.HEAD);
			removeArmor(player, EntityEquipmentSlot.FEET);
			player.addExhaustion(0.08F);
		}
		if (wendigoLevel >= 240)
		{
			player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 250));
			player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 250));
			removeArmor(player, EntityEquipmentSlot.CHEST);
		}
		if (wendigoLevel >= WENDIGO_LEVEL_CAP)
		{
			if (!nbt.hasHeart() && player.ticksExisted % 600 == 0 && nbt.wendigoSpawned() == false && nbt.getSpawnChance() > Math.random()) {
				EntityNBTEvents.spawnWendigo(player);
			}
			
			if (nbt.hasHeart())
				player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0);
		}
	}
	
	public static void spawnWendigo(EntityPlayer player) {
		player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_WOLF_HOWL, SoundCategory.HOSTILE, 1, 0.5F);
		EntityWendigo wendigo = (EntityWendigo) EntityList.createEntityByIDFromName(new ResourceLocation(Cannibalism.MODID + ":wendigo"), player.world);
		wendigo.setLocationAndAngles(player.posX + 25, player.posY, player.posZ + 25, 0, 0);
		player.world.spawnEntity(wendigo);
		CannibalismNBT.getNBT(player).setWedigoSpawn(true);
		CannibalismNBT.getNBT(player).setSpawnChance(CannibalismNBT.WENDIGO_SPAWN_BASE);
	}

}