package sorazodia.cannibalism.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import sorazodia.api.json.JSONArray;
import sorazodia.api.json.JSONWriter;
import sorazodia.cannibalism.api.EntityData;
import sorazodia.cannibalism.main.Cannibalism;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class JSONConfig
{
	private JSONArray json;
	private JSONWriter write;
	private StringBuilder entityName = new StringBuilder();
	private final String dirPath;
	private final String filePath;

	private static HashMap<String, EntityData> entityMap = new HashMap<>();
	private static ArrayList<EntityData> wildcardMap = new ArrayList<>();
	private static boolean isWildCard = false;
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
		if (new File(dirPath).exists() && new File(filePath).exists())
			return;

		FMLLog.info("[Cannibalism] Default JSON not found! Creating new file");

		File folder = new File(dirPath);

		folder.mkdir();

		write = new JSONWriter(filePath);
		writeDefault();

		FMLLog.info("[Cannibalism] Default JSON created");
	}

	public void read() throws IOException
	{
		for (File files : new File(dirPath).listFiles())
		{
			if(files.getName().equals(".crash.txt"))
				continue;
			
			json = new JSONArray(files.getAbsolutePath());
			for (int x = 0; x < json.size(); x++)
			{
				parseEntity(x);
				entityName.delete(0, entityName.length());
			}
		}

	}

	private void parseEntity(int index)
	{
		/*
		 * Get all the information from the JSON and process the data into
		 * strings that the class can uses
		 */
		String entityID = json.getString(index, ENTITYID).replaceAll(regEx, "");
		String[] drop = json.getString(index, DROPS).replaceAll(regEx, "").split(",+");
		float min = Float.parseFloat(json.getString(index, MIN).replaceAll(regEx, ""));
		float max = Float.parseFloat(json.getString(index, MAX).replaceAll(regEx, ""));

		if (!json.getString(index, MODID).equalsIgnoreCase(MINECRAFT))
			entityName.append(json.getString(index, MODID).replaceAll(regEx, "")).append(".");

		if (entityID.endsWith("*"))
		{
			isWildCard = true;
			entityID = entityID.substring(0, entityID.lastIndexOf("*"));
		}

		entityName.append(entityID);

		if (isWildCard)
			wildcardMap.add(new EntityData(entityName.toString(), drop, min, max));
		else
			entityMap.put(entityName.toString(), new EntityData(drop, min, max));

	}

	private void writeDefault() throws IOException
	{
		write.writeStart();
		addEntity("Cow*", "minecraft", new String[] { "minecraft:leather",
				"minecraft:beef" }, "2.5", "3.0");
		addEntity("Chicken", "minecraft", new String[] { "" }, "10.0", "10.0");
		addEntity("Pig", "minecraft", new String[] { "minecraft:porkchop" }, "2.5", "3.0");
		addEntity("Villager*", "minecraft", new String[] { "cannibalism:villagerFlesh" }, "5.0", "6.0");
		addEntity("Zombie*", "minecraft", new String[] { "minecraft:rotten_flesh" }, "2.5", "3.0");
		write.write();
	}

	public HashMap<String, EntityData> getEntityMap()
	{
		return entityMap;
	}
	
	public ArrayList<EntityData> getWildcardMap()
	{
		return wildcardMap;
	}
	
	public boolean checkEntity(EntityLivingBase entity)
	{
		return entityMap.containsKey(EntityList.getEntityString(entity));
	}

	public int getWildCardIndex(EntityLivingBase entity, World world)
	{
		int index = -1;
		
		for (int x = 0; x < wildcardMap.size(); x++)
		{
			if(wildcardMap.get(x).getEntity(world).getClass().isInstance(entity))
				index = x;
		}
		
		return index;
	}
	
	public EntityData getData(EntityLivingBase entity)
	{
		return entityMap.get(EntityList.getEntityString(entity));
	}
	
	public EntityData getData(EntityLivingBase entity, World world)
	{
		return wildcardMap.get(getWildCardIndex(entity, world));
	}

	public void codeRed()
	{
		entityMap.put("Chicken", new EntityData(new String[] { "" }, 2.3F, 3.0F));
		entityMap.put("Pig", new EntityData(new String[] { "minecraft:porkchop" }, 2.3F, 3.0F));
		wildcardMap.add(new EntityData("Cow", new String[] {
				"minecraft:leather", "minecraft:beef" }, 2.3F, 3.0F));
		wildcardMap.add(new EntityData("Villager", new String[] { "cannibalism:villagerFlesh" }, 2.3F, 3.0F));
		wildcardMap.add(new EntityData("Zombie", new String[] { "minecraft:rotten_flesh" }, 2.3F, 3.0F));
	}

	public String getDirPath()
	{
		return dirPath;
	}

}
