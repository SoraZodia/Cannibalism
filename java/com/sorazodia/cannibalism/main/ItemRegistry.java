package com.sorazodia.cannibalism.main;

import com.sorazodia.cannibalism.items.manager.ItemList;
import com.sorazodia.registryhelper.GameRegistryItems;

public class ItemRegistry {

	public static void init(){
		//initializate the knifes, goanna figure out how to do copper (wait, is that good?) and stuff later
		GameRegistryItems.registerItems(ItemList.woodenKnife, ItemList.woodenKnifeName);
		GameRegistryItems.registerItems(ItemList.flintKnife, ItemList.flintKnifeName);	
		GameRegistryItems.registerItems(ItemList.stoneKnife, ItemList.stoneKnifeName);	
		GameRegistryItems.registerItems(ItemList.goldKnife, ItemList.goldKnifeName);
		GameRegistryItems.registerItems(ItemList.ironKnife, ItemList.ironKnifeName);		
		GameRegistryItems.registerItems(ItemList.diamondKnife, ItemList.diamondKnifeName);
		
		//initializate the flesh
		GameRegistryItems.registerItems(ItemList.playerFlesh, ItemList.playerFleshName);
		GameRegistryItems.registerItems(ItemList.villagerFlesh, ItemList.villagerFleshName);
		GameRegistryItems.registerItems(ItemList.playerFleshCooked, ItemList.playerFleshCookedName);
		GameRegistryItems.registerItems(ItemList.villagerFleshCooked, ItemList.villagerFleshCookedName);
		
	}
}
