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
		float level = (float) DATA.get(player, tags[0]);
		level += amount;
		
		DATA.record(player, tags[0], level);
	}

	public void setWendigoValue(float amount)
	{
		DATA.record(player, tags[0], amount);
	}

	public float getWendigoValue()
	{
		return (float) DATA.get(player, tags[0]);
	}

	public static CannibalismNBT getNBT(EntityPlayer player)
	{
		return new CannibalismNBT(player);
	}

	public boolean wendigoSpawned()
	{
		return (boolean) DATA.get(player, tags[1]);
	}

	public void setWedigoSpawn(boolean doSpawn)
	{
		DATA.record(player, tags[1], doSpawn);
	}
	
	public boolean doWarningEffect()
	{
		return (boolean) DATA.get(player, tags[2]);
	}

	public void setWarningEffect(boolean doApply)
	{
		DATA.record(player, tags[2], doApply);
	}
	
}
