package sorazodia.cannibalism.mechanic.nbt;

import net.minecraft.entity.player.EntityPlayer;
import sorazodia.api.nbt.Database;
import sorazodia.cannibalism.main.Cannibalism;

public class CannibalismNBT
{	
	public static final String NBTNAME = "cannibalismVariables";
	public final String tags[] = {"wendigo", "wendigoExist", "applyWarningEffect"};
	private static final Database DATA = Cannibalism.getDatabase();
	private EntityPlayer player;

	public CannibalismNBT(EntityPlayer player)
	{
		this.player = player;
	}

	public void changeWendigoValue(float amount)
	{
		float level = this.getWendigoValue();
		level += amount;
		
		DATA.record(player, tags[0], level);
	}

	public void setWendigoValue(float amount)
	{
		DATA.record(player, tags[0], amount);
	}

	public float getWendigoValue()
	{
		float value;
		
		if (DATA.get(player) == null || DATA.get(player, tags[0]) == null)
			value = 0;
		else
			value = (float) DATA.get(player, tags[0]);

		return value;
	}

	public static CannibalismNBT getNBT(EntityPlayer player)
	{
		return new CannibalismNBT(player);
	}

	public boolean wendigoSpawned()
	{
		boolean value;
		
		if (DATA.get(player) == null || DATA.get(player, tags[1]) == null)
			value = true;
		else
			value = (boolean) DATA.get(player, tags[1]);

		return value;
	}

	public void setWedigoSpawn(boolean doSpawn)
	{
		DATA.record(player, tags[1], doSpawn);
	}
	
	public boolean doWarningEffect()
	{
		boolean value;
		
		if (DATA.get(player) == null || DATA.get(player, tags[2]) == null)
			value = true;
		else
			value = (boolean) DATA.get(player, tags[2]);

		return value;
	}

	public void setWarningEffect(boolean doApply)
	{
		DATA.record(player, tags[2], doApply);
	}
	
}
