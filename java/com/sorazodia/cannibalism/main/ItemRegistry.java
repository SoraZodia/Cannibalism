package com.sorazodia.cannibalism.main;

import com.sorazodia.api.GameRegistryItems;
import com.sorazodia.api.SmeltingRegistry;
import com.sorazodia.cannibalism.items.manager.ItemList;
import com.sorazodia.cannibalism.items.manager.ItemNames;

public class ItemRegistry {

	public static void init(){
		//initializate the knifes, goanna figure out how to do copper (wait, is that good?) and stuff later
		GameRegistryItems.registerItems(ItemList.woodenKnife, ItemNames.woodenKnifeName);
		GameRegistryItems.registerItems(ItemList.flintKnife, ItemNames.flintKnifeName);	
		GameRegistryItems.registerItems(ItemList.stoneKnife, ItemNames.stoneKnifeName);	
		GameRegistryItems.registerItems(ItemList.goldKnife, ItemNames.goldKnifeName);
		GameRegistryItems.registerItems(ItemList.ironKnife, ItemNames.ironKnifeName);		
		GameRegistryItems.registerItems(ItemList.diamondKnife, ItemNames.diamondKnifeName);
		
		//initializate the flesh
		GameRegistryItems.registerItems(ItemList.playerFlesh, ItemNames.playerFleshName);
		GameRegistryItems.registerItems(ItemList.villagerFlesh, ItemNames.villagerFleshName);
		GameRegistryItems.registerItems(ItemList.playerFleshCooked, ItemNames.playerFleshCookedName);
		GameRegistryItems.registerItems(ItemList.villagerFleshCooked, ItemNames.villagerFleshCookedName);
		
	}
}
