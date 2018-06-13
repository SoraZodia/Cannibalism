package sorazodia.api.nbt;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;

/**
 * External storage for player data in Minecraft
 */
public class Database
{
	protected HashMap<UUID, PlayerInfo> data;
	
	/**
	 * Creates a new Database object with a empty HashMap. Only one 
	 * Database object should be used for a mod.
	 */
	public Database()
	{
		data = new HashMap<>();
	}
	
	/**
	 * Creates a new Database object that uses the HashMap given in the arguments, 
	 * only for internal uses
	 */
	protected Database(HashMap<UUID, PlayerInfo> parsedData)
	{
		this.data = parsedData;
	}
	
	/**
	 * Registers a player into the Database and adds information about them. This will
	 * also work as a alternative to {@link PlayerInfo#add(String, Object)} and {@link PlayerInfo#set(String, Object)}.
	 */
	public boolean record(EntityPlayer player, String key, Object info)
	{
		UUID id = EntityPlayer.getUUID(player.getGameProfile());
		boolean success = !this.data.containsKey(id) && this.data.put(id, new PlayerInfo(key, info)) == null;

		if (!success && !this.data.get(id).add(key, info)) //means that player and key is registered
			this.data.get(id).set(key, info);
		
		return success;
	}
	
	/**
	 * Registers a player into the Database and adds information about them. This will
	 * also work as a alternative to {@link PlayerInfo#add(String, Object)} and {@link PlayerInfo#set(String, Object)}.
	 */
	public boolean record(UUID id, String key, Object info)
	{
		boolean success = !this.data.containsKey(id) && this.data.put(id, new PlayerInfo(key, info)) == null;

		if (!success && !this.data.get(id).add(key, info)) //means that player and key is registered
			this.data.get(id).set(key, info);
		
		return success;
	}
	
	/**
	 * Adds a player to the Database
	 */
	public boolean register(EntityPlayer player)
	{
		UUID id = EntityPlayer.getUUID(player.getGameProfile());
		return !this.data.containsKey(id) && this.data.put(id, new PlayerInfo()) == null;
	}
	
	/**
	 * Adds a player to the Database
	 */
	public boolean register(UUID id)
	{
		return !this.data.containsKey(id) && this.data.put(id, new PlayerInfo()) == null;
	}
	
	/**
	 * Provides the PlayerInfo associated with that player.
	 * @param EntityPlayer
	 * @return PlayerInfo
	 */
	public Optional<PlayerInfo> get(EntityPlayer player)
	{
		UUID id = EntityPlayer.getUUID(player.getGameProfile());
		return Optional.ofNullable(this.data.get(id));
	}
	
	/**
	 * Provides the PlayerInfo associated with that player.
	 * @param EntityPlayer
	 * @return PlayerInfo
	 */
	public Optional<PlayerInfo> get(UUID id)
	{
		return Optional.ofNullable(this.data.get(id));
	}
	
	/**
	 * Provides the player data associated with the provided key. An alternative to {@link PlayerInfo#get(String)}
	 * 
	 * The data is returned as a Object
	 */
	public Optional<Object> get(EntityPlayer player, String key)
	{
		return Optional.ofNullable(this.get(player).orElse(new PlayerInfo()).get(key));
	}
	
	/**
	 * Provides the player data associated with the provided key. An alternative to {@link PlayerInfo#get(String)}
	 * 
	 * The data is returned as a Object
	 */
	public Optional<Object> get(UUID id, String key)
	{
		return Optional.ofNullable(this.get(id).orElse(new PlayerInfo()).get(key));
	}
	
	/**
	 * Delete a existing player data from the player. An alternative to {@link PlayerInfo#remove(String)}
	 * 
	 * @param player EntityPlayer
	 * @param key the name of the info to remove
	 * @return the deleted player data
	 */
	public Object remove(EntityPlayer player, String key)
	{
		return this.get(player).orElse(new PlayerInfo()).remove(key);
	}
	
	/**
	 * Delete a existing player data from the player. An alternative to {@link PlayerInfo#remove(String)}
	 * 
	 * @param player EntityPlayer
	 * @param key the name of the info to remove
	 * @return the deleted player data
	 */
	public Object remove(UUID id, String key)
	{
		return this.get(id).orElse(new PlayerInfo()).remove(key);
	}
	
	/**
	 * Take the player and its info out of the Database
	 * @param player EntityPlayer
	 * @return PlayerInfo, the information attached to the player that was removed
	 */
	public PlayerInfo remove(EntityPlayer player)
	{
		UUID id = EntityPlayer.getUUID(player.getGameProfile());
		return this.data.remove(id);
	}
	
	/**
	 * Take the player and its info out of the Database
	 * @param player EntityPlayer
	 * @return PlayerInfo, the information attached to the player that was removed
	 */
	public PlayerInfo remove(UUID id)
	{
		return this.data.remove(id);
	}
	
	/**
	 * Replace the PlayerInfo attached to a player
	 * @param EntityPlayer
	 * @param PlayerInfo
	 * @return The PlayerInfo that was replaced
	 */
	public PlayerInfo overwrite(EntityPlayer player, PlayerInfo newData)
	{
		UUID id = EntityPlayer.getUUID(player.getGameProfile());
		return this.data.replace(id, newData);
	}
	
	/**
	 * Replace the PlayerInfo attached to a player
	 * @param EntityPlayer
	 * @param PlayerInfo
	 * @return The PlayerInfo that was replaced
	 */
	public PlayerInfo overwrite(UUID id, PlayerInfo newData)
	{
		return this.data.replace(id, newData);
	}
	
	/**
	 * Provides access to the HashMap for further manipulation.
	 * The HashMap uses a player's UUID as the key with PlayerInfo as its data
	 */
	public HashMap<UUID, PlayerInfo> getDatabase()
	{
		return this.data;
	}
	
}
