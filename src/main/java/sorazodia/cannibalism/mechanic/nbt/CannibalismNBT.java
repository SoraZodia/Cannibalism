package sorazodia.cannibalism.mechanic.nbt;

import net.minecraft.entity.player.EntityPlayer;
import sorazodia.api.nbt.Database;
import sorazodia.cannibalism.main.Cannibalism;

public class CannibalismNBT
{	
	public static final String NBTNAME = "cannibalismVariables";
	public final String tags[] = {"wendigo", "wendigoExist", "applyWarningEffect", "spawnchance"};
	private static Database data;
	private EntityPlayer player;

	public CannibalismNBT(EntityPlayer player)
	{
		data = Cannibalism.getDatabase();
		this.player = player;
	}
	
	public void changeSpawnChance(float amount) {
		data.record(player, tags[3], this.getSpawnChance() + amount);
	}
	
	public void setSpawnChance(float amount) {
		data.record(player, tags[3], amount);
	}

	public float getSpawnChance()
	{	
		return (float) data.get(player, tags[3]).orElse(0.3F);
	}

	public void changeWendigoValue(float amount)
	{
		float level = this.getWendigoValue();
		level += amount;
		
		if (level < 0) level = 0;
		
		data.record(player, tags[0], level);
	}

	public void setWendigoValue(float amount)
	{
		data.record(player, tags[0], amount);
	}

	public float getWendigoValue()
	{
		return (float) data.get(player, tags[0]).orElse(0);
	}

	public static CannibalismNBT getNBT(EntityPlayer player)
	{
		return new CannibalismNBT(player);
	}

	public boolean wendigoSpawned()
	{
		return (boolean) data.get(player, tags[1]).orElse(true);
	}

	public void setWedigoSpawn(boolean doSpawn)
	{
		data.record(player, tags[1], doSpawn);
	}
	
	public boolean doWarningEffect()
	{
		return (boolean) data.get(player, tags[2]).orElse(true);
	}

	public void setWarningEffect(boolean doApply)
	{
		data.record(player, tags[2], doApply);
	}
	
}
