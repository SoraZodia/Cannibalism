package sorazodia.api.nbt;

import java.io.Serializable;
import java.util.HashMap;

public class PlayerInfo implements Serializable
{	
	private static final long serialVersionUID = 8771112364964905112L;
	protected HashMap<String, Object> data = new HashMap<>();

	public boolean add(String key, Object info)
	{
		return !data.containsKey(key) && data.put(key, info) == null;
	}
	
	public Object get(String key)
	{
		return data.get(key);
	}
	
	public Object set(String key, Object newInfo)
	{
		return data.replace(key, newInfo);
	}
	
	public Object remove(String key)
	{
		return data.remove(key);
	}
	
}
