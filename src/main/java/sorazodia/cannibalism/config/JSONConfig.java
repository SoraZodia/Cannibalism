package sorazodia.cannibalism.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.Logger;

import sorazodia.api.json.JSONArray;
import sorazodia.api.json.JSONWriter;
import sorazodia.cannibalism.api.EntityData;
import sorazodia.cannibalism.main.Cannibalism;

import com.google.gson.JsonSyntaxException;

public class JSONConfig
{
	private JSONArray json;
	private JSONWriter write;
	private StringBuilder entityName = new StringBuilder();
	private final String dirPath;
	private final String filePath;
	private final String overwriteFiles[] = {"overwrite.json", "overwrite.txt"};
	private String fileName;
	private static Logger log = Cannibalism.getLogger();
	private static HashMap<String, EntityData> entityMap = new HashMap<>();
	private static ArrayList<EntityData> wildcardMap = new ArrayList<>();
	private static EntityData playerData = new EntityData("player", new String[0], 5.0F, 5.5F);
	private static final String MODID = "modID";
	private static final String ENTITYID = "entityID";
	private static final String DROPS = "drops";
	private static final String MIN = "minDamage";
	private static final String MAX = "maxDamage";
	private static String regEx = "[\"+]";

	public JSONConfig(FMLPreInitializationEvent preEvent)
	{
		dirPath = Paths.get(preEvent.getModConfigurationDirectory().getAbsolutePath(), Cannibalism.MODID).toString();
		filePath = Paths.get(dirPath, Cannibalism.MODID + ".json").toString();
		System.out.println("[Cannibalism] Config path: " + this.dirPath);
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

	public void initEntityMappings() throws IOException
	{
		File folder = new File(dirPath);

		if (!folder.exists())
		{
			log.info("[Cannibalism] JSON Folder created");
			folder.mkdir();
		}

		log.info("[Cannibalism] Loading and reading data...");
		this.loadMapData();
		read();
	}

	public void read() throws JsonSyntaxException, NumberFormatException, ClassCastException, NullPointerException, IOException
	{
		File overwriteFile = null;
		
		if (new File(Paths.get(dirPath, overwriteFiles[0]).toString()).exists())
			overwriteFile = new File(Paths.get(dirPath, "overwrite.json").toString());
		else if (new File(Paths.get(dirPath, overwriteFiles[1]).toString()).exists())
			overwriteFile = new File(Paths.get(dirPath, "overwrite.txt").toString());
		
		if (overwriteFile != null)
		{
			json = new JSONArray(overwriteFile.getAbsolutePath());

			log.info("[Cannibalism] Overwrite JSON Found! Changing Data...");

			entityMap.clear();
			wildcardMap.clear();

			for (int x = 0; x < json.size(); x++)
			{
				parseEntity(x);
				entityName.delete(0, entityName.length());
			}
		}

		for (File files : new File(dirPath).listFiles())
		{
			fileName = files.getName();
			json = new JSONArray(files.getAbsolutePath());

			if (fileName.equals(overwriteFiles[0]) || fileName.equals(overwriteFiles[1]))
				continue;

			for (int x = 0; x < json.size(); x++)
			{
				parseEntity(x);
				entityName.delete(0, entityName.length());
			}
		}

		wildcardMap.sort((firstData, secordData) -> firstData.compareTo(secordData));
	}

	/*
	 * Get all the information from the JSON and process the data into
	 * strings that the class can uses
	 */
	private void parseEntity(int index)
	{
		String entityID = json.getString(index, ENTITYID).replaceAll(regEx, "").trim();
		String[] drop = json.getString(index, DROPS).replaceAll(regEx, "").split(",+");
		float min = Float.parseFloat(json.getString(index, MIN).replaceAll(regEx, ""));
		float max = Float.parseFloat(json.getString(index, MAX).replaceAll(regEx, ""));
		boolean isWildCard = false;

		entityName.append(json.getString(index, MODID).replaceAll(regEx, "")).append(":");

		if (entityID.endsWith("*"))
		{
			isWildCard = true;
			entityID = entityID.substring(0, entityID.lastIndexOf("*"));
		}

		entityName.append(entityID);

		if (entityName.toString().toLowerCase().equals("player"))
		{
			playerData.setMinDamage(min);
			playerData.setMaxDamage(max);
		}
		else if (isWildCard)
			wildcardMap.add(new EntityData(entityName.toString(), drop, min, max));
		else
			entityMap.put(entityName.toString(), new EntityData(entityName.toString(),drop, min, max));

	}

	public void writeDefault() throws IOException
	{
		write = new JSONWriter(filePath);

		write.writeStart();
		addEntity("cow*", "minecraft", new String[] { "minecraft:leather", "minecraft:beef" }, "2.5", "3.0");
		addEntity("chicken", "minecraft", new String[] { "" }, "10.0", "10.0");
		addEntity("pig", "minecraft", new String[] { "minecraft:porkchop" }, "2.5", "3.0");
		addEntity("sheep", "minecraft", new String[] { "minecraft:wool", "minecraft:mutton" }, "2.5", "3.0");
		addEntity("villager*", "minecraft", new String[] { "cannibalism:villagerFlesh" }, "5.0", "6.0");
		addEntity("witch", "minecraft", new String[] { "cannibalism:witchFlesh" }, "5.0", "6.0");
		addEntity("zombie*", "minecraft", new String[] { "minecraft:rotten_flesh" }, "2.5", "3.0");
		write.write();
	}

	public void loadMapData()
	{
		entityMap.put("minecraft:chicken", new EntityData("minecraft:chicken",new String[] { "" }, 10.0F, 10.0F));
		entityMap.put("minecraft:pig", new EntityData("minecraft:pig",new String[] { "minecraft:porkchop" }, 2.3F, 3.0F));
		entityMap.put("minecraft:sheep", new EntityData("minecraft:sheep",new String[] { "minecraft:wool", "minecraft:mutton" }, 2.3F, 3.0F));
		entityMap.put("minecraft:witch", new EntityData("minecraft:witch",new String[] { "cannibalism:witchFlesh" }, 5.0F, 6.0F));
		wildcardMap.add(new EntityData("cow", new String[] { "minecraft:leather", "minecraft:beef" }, 2.3F, 3.0F));
		wildcardMap.add(new EntityData("villager", new String[] { "cannibalism:villagerFlesh" }, 2.3F, 3.0F));
		wildcardMap.add(new EntityData("zombie", new String[] { "minecraft:rotten_flesh" }, 2.3F, 3.0F));
	}

	public HashMap<String, EntityData> getEntityMap()
	{
		return entityMap;
	}

	public ArrayList<EntityData> getWildcardMap()
	{
		return wildcardMap;
	}
	
	public EntityData getPlayerData()
	{
		return playerData;
	}

	public boolean checkEntity(EntityLivingBase entity)
	{
		ResourceLocation entityData = EntityList.getKey(entity);
		
		if (entityData != null) 
		{
			String modKey = entityData.toString();
			String modID = modKey.split(":")[0];
			
			return entityMap.containsKey(modKey) || entityMap.containsKey(modID + ":" + EntityList.getEntityString(entity));
		}
		else // EntityPlayer will normally trigger this case
		{
			return false;
		}
	}

	public int getWildCardIndex(EntityLivingBase entity, World world)
	{
		int index = -1;

		for (int x = 0; x < wildcardMap.size(); x++)
		{
			Entity e = wildcardMap.get(x).getEntity(world);
			if (e == null)
				continue;
			else if (e.getClass().isInstance(entity))
				index = x;
		}

		return index;
	}

	public boolean isWildCardEntry(String name)
	{
		if (wildcardMap.size() <= 0)
			return false;

		int lastIndex = wildcardMap.size() - 1;
		int firstIndex = 0;

		while (lastIndex > firstIndex)
		{
			int midIndex = lastIndex / 2;
			int compare = wildcardMap.get(midIndex).getName().compareTo(name);

			if (compare == 1)
				lastIndex = midIndex;
			if (compare == -1)
				firstIndex = midIndex;
			else
				return true;
		}

		return false;
	}

	public EntityData getData(EntityLivingBase entity)
	{
		String modKey = EntityList.getKey(entity).toString();
		String modID = modKey.split(":")[0];
		
		return Optional.ofNullable(entityMap.get(EntityList.getKey(entity).toString()))
					   .orElse(entityMap.get(modID + ":" + EntityList.getEntityString(entity)));
	}

	public EntityData getData(EntityLivingBase entity, World world)
	{
		return wildcardMap.get(getWildCardIndex(entity, world));
	}

	public String getDirPath()
	{
		return dirPath;
	}

	public String getFileName()
	{
		return fileName;
	}
}
