package sorazodia.cannibalism.config;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import sorazodia.api.json.JSONArray;
import sorazodia.api.json.JSONWriter;
import sorazodia.cannibalism.api.EntityData;
import sorazodia.cannibalism.main.Cannibalism;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class JSONConfig
{
	private JSONArray json;
	private JSONWriter write;
	private StringBuilder entityName = new StringBuilder();
	private final String dirPath;
	private final String filePath;
	
	private static HashMap<String, EntityData> entityMap = new HashMap<>();	
	private static final String MODID = "modID";
	private static final String ENTITYID = "entityID";
	private static final String DROPS = "drops";
	private static final String MIN = "minDamage";
	private static final String MAX = "maxDamage";
	private static final String MINECRAFT = "\"minecraft\"";
	private static String regEx = "[\\s+[\"+]]";
	

	public JSONConfig(FMLPreInitializationEvent preEvent) throws IOException
	{
		dirPath = preEvent.getModConfigurationDirectory().getAbsolutePath()
				+ "\\" + Cannibalism.MODID;
		filePath = dirPath + "\\" + Cannibalism.MODID + ".json";
		initJSON();
	}

	public void addEntity(String name, String modID, String[] drops, String min, String max)
	{
		write.writeArrayStart();
		write.writeObject(ENTITYID, name);
		write.writeObject(MODID, modID);
		write.writeArray(DROPS, drops);
		write.writeObject(MIN, min);
		write.writeObject(MAX, max);
		write.writeArrayEnd();
	}

	public void initJSON() throws IOException
	{
		if (new File(filePath).exists())
			return;

		File folder = new File(dirPath);

		write = new JSONWriter(filePath);
		try
		{
			folder.mkdir();
			writeDefault();
		} catch (IOException io)
		{
			io.printStackTrace();
		}
	}

	public void read()
	{
		try
		{
			for (File files : new File(dirPath).listFiles())
			{
				json = new JSONArray(files.getAbsolutePath());
				for (int x = 0; x < json.size(); x++) //<__<... O(n^2)... >___>
				{
					if (!(json.getString(x, MODID).equalsIgnoreCase(MINECRAFT)))
						entityName.append(json.getString(x, MODID).replaceAll(regEx, "")).append(".");

					entityName.append(json.getString(x, ENTITYID).replaceAll(regEx, ""));

					String[] drop = json.getString(x, DROPS).replaceAll(regEx, "").split(",+");

					float min = Float.parseFloat(json.getString(x, MIN).replaceAll(regEx, ""));
					float max = Float.parseFloat(json.getString(x, MAX).replaceAll(regEx, ""));

					entityMap.put(entityName.toString(), new EntityData(drop, min, max));

					// Debug System.out.println(entityName.toString());
					entityName.delete(0, entityName.length());
				}
			}
		} catch (IOException io)
		{
			io.printStackTrace();
		}
	}

	private void writeDefault() throws IOException
	{
		write.writeStart();
		addEntity("Cow", "minecraft", new String[] { "minecraft:leather", "minecraft:beef" }, "2.5", "3.0");
		addEntity("Chicken", "minecraft", new String[] { "" }, "10.0", "10.0");
		addEntity("Pig", "minecraft", new String[] { "minecraft:porkchop" }, "2.5", "3.0");
		addEntity("Villager", "minecraft", new String[] { "cannibalism:villagerFlesh" }, "5.0", "6.0");
		addEntity("Zombie", "minecraft", new String[] { "minecraft:rotten_flesh" }, "2.5", "3.0");
		write.write();
	}

	public static EntityData getEntityData(String key)
	{
		return entityMap.get(key);
	}

	public static boolean contains(String key)
	{
		return entityMap.containsKey(key);
	}

}
