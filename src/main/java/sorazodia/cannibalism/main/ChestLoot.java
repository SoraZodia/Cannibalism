package sorazodia.cannibalism.main;

import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChestLoot
{
	@SubscribeEvent
	public void lootLoad(LootTableLoadEvent event)
	{
		switch (event.getName().toString())
		{
		case "minecraft:chests/abandoned_mineshaft":
			break;
		case "minecraft:chests/desert_pyramid":
			break;
		case "minecraft:chests/jungle_temple":
			break;
		case "minecraft:chests/simple_dungeon":
			break;
		case "minecraft:chests/spawn_bonus_chest":
			break;
		case "minecraft:chests/stronghold_corridor":
			break;
		case "minecraft:chests/village_blacksmith":
			break;
		}
	}
}
