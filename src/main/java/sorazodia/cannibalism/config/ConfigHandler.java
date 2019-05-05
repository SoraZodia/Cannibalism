package sorazodia.cannibalism.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import sorazodia.cannibalism.items.KnifeType;
import sorazodia.cannibalism.main.Cannibalism;
import sorazodia.cannibalism.server.CommandWendigoLevel;

public class ConfigHandler
{
	public static Configuration configFile;
	private static final String CATEGORY_STARTUP = "startup";
	private static HashMap<String, Float> externalFleshMappings = new HashMap<>();
	private static boolean scream;
	private static boolean myth = false;
	private static float screamPinch;
	private static int bloodAmount;
	private static String[] defaultFleshMappings = {"cannibalism:wendigoheart,10"};
	private static String[] knifes = {"wood", "flint", "stone", "gold", "iron", "diamond"};
	private static ArrayList<KnifeType> activeKnifes = new ArrayList<>();
	

	public ConfigHandler(FMLPreInitializationEvent event, JSONConfig json)
	{
		if (!updateOldConfig(event.getModConfigurationDirectory().getAbsolutePath()))
			Cannibalism.getLogger().info("Failed to remove old config");

		configFile = new Configuration(event.getSuggestedConfigurationFile());
		configFile.setCategoryRequiresMcRestart(CATEGORY_STARTUP, true);
		syncConfig();
	}

	public static void syncConfig()
	{
		processStringList(configFile.getStringList("Valid Flesh Items", Configuration.CATEGORY_GENERAL, defaultFleshMappings, "Listed items will be considered as human flesh. Format: [<unlocatizated name>, <wendigo level increase by>]"));		
		scream = configFile.getBoolean("Use Scream Sound", Configuration.CATEGORY_GENERAL, false, "Set true if you want to hear... PAIN");
		myth = configFile.getBoolean("Enable Mythological Mode", Configuration.CATEGORY_GENERAL, false, "Set true cause myths about cannibalism to become real.");
		screamPinch = configFile.getFloat("Scream Pitch", Configuration.CATEGORY_GENERAL, 0.7F, -10.0F, 10F, "High Pinch or Low Pinch, up to you ;)");
		bloodAmount = configFile.getInt("Blood Spawn Amount", Configuration.CATEGORY_GENERAL, 36, 0, 100, "Higher value = More blood, Lower value = Less blood. A value of 0 will disable it");
		
		processKnifeList(configFile.getStringList("Active Knife List", CATEGORY_STARTUP, knifes, "Knife items that will be loaded on startup. Remove them from this list to disable them. Requires MC to restart."));
		if (configFile.hasChanged())
			configFile.save();
	}
	
	private static void processKnifeList(String list[]) 
	{
		activeKnifes.clear();
		try 
		{
			for (String knife: list) 
			{
				activeKnifes.add(KnifeType.valueOf(knife.trim().toUpperCase()));
			}
		}
		catch (NullPointerException | IllegalArgumentException e)
		{
			Cannibalism.getLogger().error("Invalid knife type");
		}
	}
	
	private static void processStringList(String list[]) 
	{
		for (String entry: list)
		{
			String[] mapping = entry.split(",");
			if (mapping.length > 1 && CommandWendigoLevel.isFloat(mapping[1], true))
				externalFleshMappings.put(mapping[0], Float.valueOf(mapping[1]));
			else
				externalFleshMappings.put(mapping[0], 10.0F);
		}
	}
	
	public static ArrayList<KnifeType> getEnabledKnifeList()
	{
		return activeKnifes;
	}
	
	public static Optional<Float> processAsFlesh(String itemName)
	{
		return Optional.ofNullable(externalFleshMappings.get(itemName));
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

	public static boolean allowMyth()
	{
		return myth;
	}
	
	public static List<IConfigElement> getConfigElements() 
	{
		List<IConfigElement> configElements= new ConfigElement(ConfigHandler.configFile.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements();
		
		configElements.addAll(new ConfigElement(ConfigHandler.configFile.getCategory(ConfigHandler.CATEGORY_STARTUP)).getChildElements());
		
		return configElements;
	}

	public static boolean updateOldConfig(String dirPath)
	{
		boolean success = false;
		boolean removed = false;
		final String key = "# Old Config Removed (v2.2.4)";

		try
		{
			File oldFile = Paths.get(dirPath, "cannibalism.cfg").toFile();

			if (oldFile.exists())
			{
				BufferedReader reader = new BufferedReader(new FileReader(oldFile));
				String str = reader.readLine();

				if (str.equals(key))
				{
					reader.close();
					return true;
				}

				Cannibalism.getLogger().info("[Cannibalism] Updating config settings");
				File tempFile = Paths.get(dirPath, "cannibalism.temp").toFile();
				BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

				while (str != null)
				{
					if (removed == false)
					{
						writer.write(key);
						writer.write('\n');
						removed = true;
					}

					if (str.contains("[Alpha]Enable Mythological Mode") || str.contains("# Old Config Removed"))
					{
						str = reader.readLine();
						continue;
					}

					if (str.contains("Scream Pinch"))
					{
						str = str.replace("Pinch", "Pitch");
					}

					writer.write(str);
					writer.write('\n');
					str = reader.readLine();

				}

				writer.close();
				reader.close();

				oldFile.delete();
				success = tempFile.renameTo(oldFile);
				Cannibalism.getLogger().info("[Cannibalism] Config updated");
			}
		}
		catch (FileNotFoundException e)
		{
			Cannibalism.getLogger().info("Failed to find file");
		}
		catch (IOException e)
		{
			Cannibalism.getLogger().info("[Cannibalism] Failed to open file");
			e.printStackTrace();
		}

		return success;
	}

}
