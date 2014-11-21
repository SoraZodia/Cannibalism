package com.sorazodia.cannibalism.main;

import com.sorazodia.cannibalism.items.manager.ItemList;
import com.sorazodia.registryhelper.SmeltingRegistry;

public class CookingRegistry {

	public static void init(){
		//Cooking!
		SmeltingRegistry.addSmelting(ItemList.playerFlesh, ItemList.playerFleshCooked, 0.3F);
		SmeltingRegistry.addSmelting(ItemList.villagerFlesh, ItemList.villagerFleshCooked, 0.3F);
	}
	
}
