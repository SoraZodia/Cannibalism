package sorazodia.cannibalism.config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import sorazodia.api.json.JSONArray;
import sorazodia.cannibalism.api.EntityData;
import sorazodia.cannibalism.main.Cannibalism;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class JSONConfig
{
	private static JSONArray json;
	private static HashMap<String, EntityData> entityMap = new HashMap<>();
	private static String dirPath;
	private static String filePath;
	private static StringBuilder entityName = new StringBuilder();
	private static final String MODID = "modID";
	private static final String ENTITYID = "entityID";
	private static final String DROPS = "drops";
	private static final String MIN = "minDamage";
	private static final String MAX = "maxDamage";
	private static String regEx = "[\\s+[\"+]]";
    private static String[] defaultFile = {
    	"[",
    	"{",
    	"\"" + ENTITYID + "\": \"Cow\" , ",
    	"\"" + MODID + "\": \"minecraft\" , ",
    	"\"" + DROPS + "\": \" minecraft:leather,  minecraft:beef \" ",
    	"\"" + MIN + "\": \" 2.5 \" ",
    	"\"" + MAX + "\": \" 3.0 \" ",
    	"}",
    	"]"
    };
	public JSONConfig(FMLPreInitializationEvent preEvent) 
	{
		dirPath = preEvent.getModConfigurationDirectory().getAbsolutePath() + "\\" + Cannibalism.MODID;
		filePath = dirPath + "\\" + Cannibalism.MODID;
		initJSON(preEvent);
		read();
	}
	
	private void initJSON(FMLPreInitializationEvent preEvent)
	{
		File folder = new File(dirPath);
		File config = new File(filePath);
		try
		{
			folder.mkdir();
			if(config.createNewFile() && config.canWrite())
				writeDefault(new BufferedWriter(new FileWriter(config)));			
		}
		catch(IOException io) 
		{
			io.printStackTrace();
		}
	}
	
	private void read() {
		try
		{
			json = new JSONArray(filePath);
			for (int x = 0; x < json.size(); x++) 
			{	
				if (!(json.getString(x, MODID).equalsIgnoreCase("\"minecraft\"")))
					entityName.append(json.getString(x, MODID).replaceAll(regEx, "")).append(".");				
				entityName.append(json.getString(x, ENTITYID).replaceAll(regEx, ""));
				
				String[] drop = json.getString(x, DROPS).replaceAll(regEx, "").split(",+");
				
				float min = Float.parseFloat(json.getString(x, MIN).replaceAll(regEx, ""));
				float max = Float.parseFloat(json.getString(x, MAX).replaceAll(regEx, ""));
				
				entityMap.put(entityName.toString(), new EntityData(drop, min, max));
				
				System.out.println(entityName.toString());
				entityName.delete(0, entityName.length());
			}
		} catch (IOException io)
		{
			io.printStackTrace();
		}
	}
	
	private void writeDefault(BufferedWriter writer) throws IOException {
		for(String s : defaultFile) {
			writer.write(s);
			writer.newLine();
		}
		writer.close();
	}
	
	public static EntityData getEntityDrops(String key)
	{
		return entityMap.get(key);
	}
	
	public static boolean contains(String key)
	{
		return entityMap.containsKey(key);
	}
	
}
