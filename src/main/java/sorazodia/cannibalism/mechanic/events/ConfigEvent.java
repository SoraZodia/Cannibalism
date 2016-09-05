package sorazodia.cannibalism.mechanic.events;

import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import sorazodia.cannibalism.config.ConfigHandler;
import sorazodia.cannibalism.main.Cannibalism;

public class ConfigEvent
{

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent config)
	{
		if (config.getModID().equals(Cannibalism.MODID))
			ConfigHandler.syncConfig();
	}

}
