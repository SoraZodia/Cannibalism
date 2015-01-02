package sorazodia.cannibalism.events;

import sorazodia.cannibalism.config.ConfigHandler;
import sorazodia.cannibalism.main.Cannibalism;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ConfigEvent 
{
	
	@SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent config) 
	{
        if(config.modID.equals(Cannibalism.MODID)) ConfigHandler.syncConfig();
    }
	
}
