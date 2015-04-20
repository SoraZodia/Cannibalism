package sorazodia.cannibalism.main;

import sorazodia.api.registryhelper.SimpleItemsRegistry;
import sorazodia.cannibalism.items.manager.ItemList;

public class ItemRegistry
{

	public static void init()
	{
		SimpleItemsRegistry.init(Cannibalism.MODID, Cannibalism.cannibalismTab);

		// initializate the knifes, goanna figure out how to do copper (wait, is
		// that good?) and stuff later
		SimpleItemsRegistry.registerItems(ItemList.woodenKnife, ItemList.woodenKnifeName);
		SimpleItemsRegistry.registerItems(ItemList.flintKnife, ItemList.flintKnifeName);
		SimpleItemsRegistry.registerItems(ItemList.stoneKnife, ItemList.stoneKnifeName);
		SimpleItemsRegistry.registerItems(ItemList.goldKnife, ItemList.goldKnifeName);
		SimpleItemsRegistry.registerItems(ItemList.ironKnife, ItemList.ironKnifeName);
		SimpleItemsRegistry.registerItems(ItemList.diamondKnife, ItemList.diamondKnifeName);
		SimpleItemsRegistry.registerItems(ItemList.devKnife, ItemList.devKnifeName, "flintKnife");

		// initializate the flesh
		SimpleItemsRegistry.registerEasterItems(ItemList.playerFlesh, ItemList.playerFleshName, "playerPoptart");
		SimpleItemsRegistry.registerEasterItems(ItemList.villagerFlesh, ItemList.villagerFleshName, "villagerPoptart");
		SimpleItemsRegistry.registerEasterItems(ItemList.playerFleshCooked, ItemList.playerFleshCookedName, "playerPoptartCooked");
		SimpleItemsRegistry.registerEasterItems(ItemList.villagerFleshCooked, ItemList.villagerFleshCookedName, "villagerPoptartCooked");

	}

}
