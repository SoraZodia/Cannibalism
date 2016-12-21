package sorazodia.api.nbt;

import java.util.HashMap;
import java.util.Optional;

public class PlayerInfo 
{	
	protected HashMap<String, Optional<Object>> data = new HashMap<>();

	public boolean add(String key, Object info)
	{
		return !data.containsKey(key) && data.put(key, Optional.ofNullable(info)) == null;
	}
	
	public Optional<Object> get(String key)
	{
		return data.get(key);
	}
	
	public Optional<Object> set(String key, Object newInfo)
	{
		return data.replace(key, Optional.ofNullable(newInfo));
	}
	
	public Optional<Object> remove(String key)
	{
		return data.remove(key);
	}
	
}
