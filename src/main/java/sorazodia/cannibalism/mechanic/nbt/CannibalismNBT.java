package sorazodia.cannibalism.mechanic.nbt;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import sorazodia.api.nbt.Database;
import sorazodia.cannibalism.main.Cannibalism;

public class CannibalismNBT
{	
	public static final String NBTNAME = "cannibalismVariables";
	public static final float WENDIGO_SPAWN_BASE = 0.1F;
	public final String tags[] = {"wendigo", "wendigoExist", "applyWarningEffect", "spawnchance", "heirloomCount", "embeddedHeart"};
	private static Database data;
	private EntityPlayer player;

	public CannibalismNBT(EntityPlayer player)
	{
		data = Cannibalism.getDatabase();
		this.player = player;
	}
	
	public void setHeart(boolean hasHeart) {
		data.record(player, tags[5], hasHeart);
	}
	
	public boolean hasHeart() {
		return (boolean) data.get(player, tags[5]).orElse(false);
	}
	
	public void increaseHeirloomCount() {
		data.record(player, tags[4], this.getHeirloomCount() + 1);
	}
	
	public void setHeirloomCount(int count) {
		data.record(player, tags[4], count);
	}
	
	public int getHeirloomCount() {
		return (int) data.get(player, tags[4]).orElse(0);
	}
	
	public void changeSpawnChance(float amount) {
		data.record(player, tags[3], this.getSpawnChance() + amount);
	}
	
	public void setSpawnChance(float amount) {
		data.record(player, tags[3], amount);
	}

	public float getSpawnChance()
	{	
		return (float) data.get(player, tags[3]).orElse(WENDIGO_SPAWN_BASE);
	}

	public void changeWendigoValue(float amount)
	{
		float level = this.getWendigoValue();
		level += amount;
		
		if (level < 0) level = 0;
		
		if (level < Float.MAX_VALUE) data.record(player, tags[0], level);
	}

	public void setWendigoValue(float amount)
	{
		data.record(player, tags[0], amount);
	}

	public float getWendigoValue()
	{
		return (float) data.get(player, tags[0]).orElse(0.0F);
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
	
	public void printDataRecords(ICommandSender sender)
	{
		for (int x = 0; x < tags.length; x++) {
			sender.sendMessage(new TextComponentTranslation("command.stat." + tags[x], data.get(player, tags[x]).orElse(x == 3 ? WENDIGO_SPAWN_BASE: 0)));
		}
	}
	
	
}
