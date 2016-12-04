package sorazodia.api.nbt;

import java.util.HashMap;

public class PlayerInfo 
{	
	protected static HashMap<String, Object> data = new HashMap<>();

	public static boolean add (String key, Object info)
	{
		return data.put(key, info) != null;
	}
	
	public static Object get(String key)
	{
		return data.get(key);
	}
	
	public static boolean set(String key, Object newInfo)
	{
		return data.replace(key, newInfo) != null;
	}
	
}
