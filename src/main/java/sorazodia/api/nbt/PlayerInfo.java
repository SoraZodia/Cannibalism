package sorazodia.api.nbt;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Player data container, be warn that this will not check data types
 */
public class PlayerInfo implements Serializable
{	
	private static final long serialVersionUID = 8771112364964905112L;
	protected HashMap<String, Object> data = new HashMap<>();

	/**
	 * Adds data about the player into a HashMap, which can later be 
	 * received by the key associated with it. Note that an PlayerInfo
	 * object can also be stored, so there can be nested data.
	 * 
	 * @param String, the key associated with the data
	 * @param Object, the player data
	 * @return True if the key was not taken, false otherwise
	 */
	public boolean add(String key, Object info)
	{
		return !data.containsKey(key) && data.put(key, info) == null;
	}
	
	/**
	 * Provides the data that the key is associated to. The data is returned as
	 * a Object.
	 * 
	 * @param String, the key associated with the data
	 * @return Object
	 */
	public Object get(String key)
	{
		return data.get(key);
	}
	
	/**
	 * Replaces a existing player data with another value
	 * 
	 * @param String, the key associated with the data
	 * @param Object, the player data
	 * @return Object, the overwritten player data
	 */
	public Object set(String key, Object newInfo)
	{
		return data.replace(key, newInfo);
	}
	
	/**
	 * Removes a existing player data
	 * 
	 * @param String, the key associated with the data
	 * @return Object, the deleted player data
	 */
	public Object remove(String key)
	{
		return data.remove(key);
	}
	
}
