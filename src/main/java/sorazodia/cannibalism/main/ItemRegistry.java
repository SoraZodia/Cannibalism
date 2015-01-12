package sorazodia.cannibalism.main;

import scala.util.Random;
import sorazodia.cannibalism.items.manager.ItemList;
import sorazodia.registryhelper.GameItemsRegistry;

public class ItemRegistry 
{

	public static void init()
	{
		//initializate the knifes, goanna figure out how to do copper (wait, is that good?) and stuff later
		GameItemsRegistry.registerItems(ItemList.woodenKnife, ItemList.woodenKnifeName);
		GameItemsRegistry.registerItems(ItemList.flintKnife, ItemList.flintKnifeName);	
		GameItemsRegistry.registerItems(ItemList.stoneKnife, ItemList.stoneKnifeName);	
		GameItemsRegistry.registerItems(ItemList.goldKnife, ItemList.goldKnifeName);
		GameItemsRegistry.registerItems(ItemList.ironKnife, ItemList.ironKnifeName);		
		GameItemsRegistry.registerItems(ItemList.diamondKnife, ItemList.diamondKnifeName);
		
		//initializate the flesh
		GameItemsRegistry.registerEasterItems(ItemList.playerFlesh, ItemList.playerFleshName, "playerPoptart");
		GameItemsRegistry.registerEasterItems(ItemList.villagerFlesh, ItemList.villagerFleshName, "villagerPoptart");
		GameItemsRegistry.registerEasterItems(ItemList.playerFleshCooked, ItemList.playerFleshCookedName, "playerPoptartCooked");
		GameItemsRegistry.registerEasterItems(ItemList.villagerFleshCooked, ItemList.villagerFleshCookedName, "villagerPoptartCooked");
		
	}

}
