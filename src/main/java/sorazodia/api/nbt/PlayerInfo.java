package sorazodia.api.nbt;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Player data container, be warn that this will not check data types
 */
public class PlayerInfo implements Serializable
{	
	private static final long serialVersionUID = 8771112364964905112L;
	protected HashMap<String, Object> data;
	
	/**
	 * Create the object with a empty hashmap
	 */
	public PlayerInfo()
	{
		this.data = new HashMap<>();
	}
	
	/**
	 * Creates the object that will already have an value in the hashmap
	 * @param key, the data label
	 * @param value, the actual data
	 */
	public PlayerInfo(String key, Object value)
	{
		this.data = new HashMap<>();
		this.data.put(key, value);
	}

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
		return !this.data.containsKey(key) && this.data.put(key, info) == null;
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
		return this.data.get(key);
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
		return this.data.replace(key, newInfo);
	}
	
	/**
	 * Removes a existing player data
	 * 
	 * @param String, the key associated with the data
	 * @return Object, the deleted player data
	 */
	public Object remove(String key)
	{
		return this.data.remove(key);
	}
	
}
