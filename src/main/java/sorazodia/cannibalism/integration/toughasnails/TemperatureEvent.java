package sorazodia.cannibalism.integration.toughasnails;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import sorazodia.cannibalism.config.ConfigHandler;
import sorazodia.cannibalism.mechanic.nbt.CannibalismNBT;
import toughasnails.api.stat.capability.ITemperature;
import toughasnails.api.temperature.Temperature;
import toughasnails.api.temperature.TemperatureHelper;
import toughasnails.api.temperature.TemperatureScale.TemperatureRange;

public class TemperatureEvent
{
	@SubscribeEvent
	public void playerUpdate(PlayerTickEvent event)
	{
		if (ConfigHandler.allowMyth())
		{
			EntityPlayer player = event.player;
			float wendigoLevel = CannibalismNBT.getNBT(player).getWendigoValue();
			ITemperature playerTemperature = TemperatureHelper.getTemperatureData(player);
			
			if (wendigoLevel >= 100 && playerTemperature.getTemperature().getRange() == TemperatureRange.ICY )
			{
				playerTemperature.setTemperature(new Temperature(TemperatureRange.COOL.getRangeSize()));
			}
		}
	}

}
