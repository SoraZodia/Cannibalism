package sorazodia.cannibalism.mechanic.nbt;

import net.minecraft.entity.player.EntityPlayer;
import sorazodia.api.nbt.Database;
import sorazodia.cannibalism.main.Cannibalism;

public class CannibalismNBT
{	
	public static final String NBTNAME = "cannibalismVariables";
	public final String tags[] = {"wendigo", "wendigoExist", "applyWarningEffect"};
	private static Database data;
	private EntityPlayer player;

	public CannibalismNBT(EntityPlayer player)
	{
		data = Cannibalism.getDatabase();
		this.player = player;
	}

	public void changeWendigoValue(float amount)
	{
		float level = this.getWendigoValue();
		level += amount;
		
		data.record(player, tags[0], level);
	}

	public void setWendigoValue(float amount)
	{
		data.record(player, tags[0], amount);
	}

	public float getWendigoValue()
	{
		float value;
		
		if (data.get(player) == null || data.get(player, tags[0]) == null)
			value = 0;
		else
			value = (float) data.get(player, tags[0]);

		return value;
	}

	public static CannibalismNBT getNBT(EntityPlayer player)
	{
		return new CannibalismNBT(player);
	}

	public boolean wendigoSpawned()
	{
		boolean value;
		
		if (data.get(player) == null || data.get(player, tags[1]) == null)
			value = true;
		else
			value = (boolean) data.get(player, tags[1]);

		return value;
	}

	public void setWedigoSpawn(boolean doSpawn)
	{
		data.record(player, tags[1], doSpawn);
	}
	
	public boolean doWarningEffect()
	{
		boolean value;
		
		if (data.get(player) == null || data.get(player, tags[2]) == null)
			value = true;
		else
			value = (boolean) data.get(player, tags[2]);

		return value;
	}

	public void setWarningEffect(boolean doApply)
	{
		data.record(player, tags[2], doApply);
	}
	
}
