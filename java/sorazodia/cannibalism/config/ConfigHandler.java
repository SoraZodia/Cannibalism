package sorazodia.cannibalism.config;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler {	
	public static Configuration configFile;
	private static boolean scream;
	private static float screamPinch;
	
	public ConfigHandler(FMLPreInitializationEvent event){
		configFile =  new Configuration(event.getSuggestedConfigurationFile());
		syncConfig();
	}
	
	public static void syncConfig(){
		scream = configFile.getBoolean("Use Scream Sound", Configuration.CATEGORY_GENERAL, false, "Set true if you want to hear... PAIN");
		screamPinch = configFile.getFloat("Scream Pinch", Configuration.CATEGORY_GENERAL, 0.7F, -10.0F, 10F, "High Pinch or Low Pinch, up to you ;)");
		if(configFile.hasChanged()) configFile.save();
	}
	
	public static boolean doScream(){
		return scream;
	}
	
	public static float getPinch(){
		return screamPinch;
	}
}
