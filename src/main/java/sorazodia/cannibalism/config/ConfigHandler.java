package sorazodia.cannibalism.config;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler 
{	
	public static Configuration configFile;
	private static boolean scream;
	private static boolean myth;
	private static float screamPinch;
	private static int bloodAmount;
	
	public ConfigHandler(FMLPreInitializationEvent event)
	{
		configFile =  new Configuration(event.getSuggestedConfigurationFile());
		syncConfig();
	}
	
	public static void syncConfig()
	{
		scream = configFile.getBoolean("Use Scream Sound", Configuration.CATEGORY_GENERAL, false, "Set true if you want to hear... PAIN");
		myth = (configFile.getBoolean("Enable Mythological Mode", Configuration.CATEGORY_GENERAL, true, "Set true cause myths about cannibalism to become real. Ex: the Wendigo"));
		screamPinch = configFile.getFloat("Scream Pinch", Configuration.CATEGORY_GENERAL, 0.7F, -10.0F, 10F, "High Pinch or Low Pinch, up to you ;)");
		bloodAmount = configFile.getInt("Blood Spawn Amount", Configuration.CATEGORY_GENERAL, 36, 0, 100, "Higher value = More blood, Lower value = Less blood. A value of 0 will disable it");
		if(configFile.hasChanged()) configFile.save();
	}
	
	public static boolean doScream()
	{
		return scream;
	}
	
	public static float getPinch()
	{
		return screamPinch;
	}
	
	public static int getBloodAmount()
	{
		return bloodAmount;
	}

	public static boolean getMyth()
	{
		return myth;
	}
	
}
