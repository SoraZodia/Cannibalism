package sorazodia.cannibalism.main;

import sorazodia.api.registryhelper.SmeltingRegistry;
import sorazodia.cannibalism.items.manager.ItemList;

public class CookingRegistry
{

	public static void init()
	{
		//Cooking!
		SmeltingRegistry.addSmelting(ItemList.playerFlesh, ItemList.playerFleshCooked, 0.3F);
		SmeltingRegistry.addSmelting(ItemList.villagerFlesh, ItemList.villagerFleshCooked, 0.3F);
	}
	
}
