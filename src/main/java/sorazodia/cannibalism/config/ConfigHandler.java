package sorazodia.cannibalism.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import sorazodia.cannibalism.main.Cannibalism;

public class ConfigHandler
{
	public static Configuration configFile;
	private static boolean scream;
	private static boolean myth = false;
	private static float screamPinch;
	private static int bloodAmount;

	public ConfigHandler(FMLPreInitializationEvent event, JSONConfig json)
	{
		if (!updateOldConfig(event.getModConfigurationDirectory().getAbsolutePath()))
			Cannibalism.getLogger().info("Failed to remove old config");

		configFile = new Configuration(event.getSuggestedConfigurationFile());
		syncConfig();
	}

	public static void syncConfig()
	{
		scream = configFile.getBoolean("Use Scream Sound", Configuration.CATEGORY_GENERAL, false, "Set true if you want to hear... PAIN");
		myth = configFile.getBoolean("Enable Mythological Mode", Configuration.CATEGORY_GENERAL, false, "Set true cause myths about cannibalism to become real.");
		screamPinch = configFile.getFloat("Scream Pitch", Configuration.CATEGORY_GENERAL, 0.7F, -10.0F, 10F, "High Pinch or Low Pinch, up to you ;)");
		bloodAmount = configFile.getInt("Blood Spawn Amount", Configuration.CATEGORY_GENERAL, 36, 0, 100, "Higher value = More blood, Lower value = Less blood. A value of 0 will disable it");
		if (configFile.hasChanged())
			configFile.save();
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
