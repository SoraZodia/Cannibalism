package sorazodia.cannibalism.integration.toughasnails;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import sorazodia.cannibalism.config.ConfigHandler;
import sorazodia.cannibalism.mechanic.nbt.CannibalismNBT;
import toughasnails.api.TANPotions;

public class TemperatureEvent
{
	@SubscribeEvent
	public void playerUpdate(PlayerTickEvent event)
	{
		if (ConfigHandler.allowMyth())
		{
			EntityPlayer player = event.player;
			float wendigoLevel = CannibalismNBT.getNBT(player).getWendigoValue();
			if (wendigoLevel >= 100 && player.isPotionActive(TANPotions.hypothermia))
			{
				player.addPotionEffect(new PotionEffect(TANPotions.cold_resistance, 250));
			}
		}
	}

}
