package sorazodia.api.nbt;

import java.util.HashMap;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;

public class Database
{
	protected HashMap<UUID, PlayerInfo> data;
	
	public Database()
	{
		data = new HashMap<>();
	}
	
	protected Database(HashMap<UUID, PlayerInfo> parsedData)
	{
		this.data = parsedData;
	}
	
	public boolean register(EntityPlayer player)
	{
		UUID id = EntityPlayer.getUUID(player.getGameProfile());
		return !data.containsKey(id) && data.put(id, new PlayerInfo()) == null;
	}
	
	public boolean register(UUID id)
	{
		return !data.containsKey(id) && data.put(id, new PlayerInfo()) == null;
	}
	
	public PlayerInfo get(EntityPlayer player)
	{
		UUID id = EntityPlayer.getUUID(player.getGameProfile());
		return data.get(id);
	}
	
	public PlayerInfo get(UUID id)
	{
		return data.get(id);
	}
	
	public PlayerInfo remove(EntityPlayer player)
	{
		UUID id = EntityPlayer.getUUID(player.getGameProfile());
		return data.remove(id);
	}
	
	public PlayerInfo remove(UUID id)
	{
		return data.remove(id);
	}
	
	public PlayerInfo overwrite(EntityPlayer player, PlayerInfo newData)
	{
		UUID id = EntityPlayer.getUUID(player.getGameProfile());
		return data.replace(id, newData);
	}
	
	public PlayerInfo overwrite(UUID id, PlayerInfo newData)
	{
		return data.replace(id, newData);
	}
	
	public HashMap<UUID, PlayerInfo> getDatabase()
	{
		return data;
	}
	
}
