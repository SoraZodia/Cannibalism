package sorazodia.cannibalism.mechanic.events;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
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

			if (!event.player.world.isRemote)
			{
				addServerEffect(player, wendigoLevel, nbt);
				boolean holdingHeirloom = (player.getHeldItemOffhand().getTagCompound() != null && player.getHeldItemOffhand().getTagCompound().getCompoundTag(Cannibalism.MODID).getString(InteractionEvent.HEIRLOOM_NBT_TAG).length() > 0)
						                  || (player.getHeldItemMainhand().getTagCompound() != null && player.getHeldItemMainhand().getTagCompound().getCompoundTag(Cannibalism.MODID).getString(InteractionEvent.HEIRLOOM_NBT_TAG).length() > 0);
				if (nbt.hasHeart() && holdingHeirloom)
					player.attackEntityFrom(DamageSource.WITHER, 1);
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
	
	private void addServerEffect(EntityPlayer player, float wendigoLevel, CannibalismNBT nbt)
	{
		if (wendigoLevel >= 25 && wendigoLevel < 100)
		{
			player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10));
			player.addExhaustion(0.02F);
		}
		if (wendigoLevel >= 50)
		{
			player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10, 1));
			player.addExhaustion(0.04F);
		}
		if (wendigoLevel >= 100)
		{
			if (nbt.doWarningEffect())
			{
				player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100));
				player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 100));
				nbt.setWarningEffect(false);
			}	
			
			player.getCooldownTracker().setCooldown(player.getHeldItem(player.getActiveHand()).getItem(), 0);
		}
		if (wendigoLevel >= 150)
		{
			player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 10, (int)(wendigoLevel > 300 ? wendigoLevel - 300 + 2 : 2)));
			player.addExhaustion(0.08F);
		}
		if (wendigoLevel >= 240)
		{
			player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 10, 1));
			player.setAbsorptionAmount(10);
		}
		if (wendigoLevel >= WENDIGO_LEVEL_CAP && !nbt.hasHeart())
		{
			if (player.ticksExisted % 600 == 0 && nbt.wendigoSpawned() == false && nbt.getSpawnChance() > Math.random()) {
				EntityNBTEvents.spawnWendigo(player);
			}	
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