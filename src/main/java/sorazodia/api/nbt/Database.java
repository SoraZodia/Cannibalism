package sorazodia.api.nbt;

import java.util.HashMap;
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
	 * Adds a player to the Database
	 */
	public boolean register(EntityPlayer player)
	{
		UUID id = EntityPlayer.getUUID(player.getGameProfile());
		return !data.containsKey(id) && data.put(id, new PlayerInfo()) == null;
	}
	
	/**
	 * Adds a player to the Database
	 */
	public boolean register(UUID id)
	{
		return !data.containsKey(id) && data.put(id, new PlayerInfo()) == null;
	}
	
	/**
	 * Provides the information associated with that player.
	 * @param EntityPlayer
	 * @return PlayerInfo
	 */
	public PlayerInfo get(EntityPlayer player)
	{
		UUID id = EntityPlayer.getUUID(player.getGameProfile());
		return data.get(id);
	}
	
	/**
	 * Provides the information associated with that player.
	 * @param EntityPlayer
	 * @return PlayerInfo
	 */
	public PlayerInfo get(UUID id)
	{
		return data.get(id);
	}
	
	/**
	 * Take the player and its info out of the Database
	 * @param player EntityPlayer
	 * @return PlayerInfo, the information attached to the player that was removed
	 */
	public PlayerInfo remove(EntityPlayer player)
	{
		UUID id = EntityPlayer.getUUID(player.getGameProfile());
		return data.remove(id);
	}
	
	/**
	 * Take the player and its info out of the Database
	 * @param player EntityPlayer
	 * @return PlayerInfo, the information attached to the player that was removed
	 */
	public PlayerInfo remove(UUID id)
	{
		return data.remove(id);
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
		return data.replace(id, newData);
	}
	
	/**
	 * Replace the PlayerInfo attached to a player
	 * @param EntityPlayer
	 * @param PlayerInfo
	 * @return The PlayerInfo that was replaced
	 */
	public PlayerInfo overwrite(UUID id, PlayerInfo newData)
	{
		return data.replace(id, newData);
	}
	
	/**
	 * Provides access to the HashMap for further manipulation.
	 * The HashMap uses a player's UUID as the key with PlayerInfo as its data
	 */
	public HashMap<UUID, PlayerInfo> getDatabase()
	{
		return data;
	}
	
}
