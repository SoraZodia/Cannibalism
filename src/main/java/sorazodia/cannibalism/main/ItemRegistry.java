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
		SimpleItemsRegistry.registerItems(ItemList.devKnife, ItemList.devKnifeName, ItemList.flintKnifeName);

		// initializate the flesh
		SimpleItemsRegistry.registerItems(ItemList.playerFlesh, ItemList.playerFleshName);
		SimpleItemsRegistry.registerItems(ItemList.villagerFlesh, ItemList.villagerFleshName);
		SimpleItemsRegistry.registerItems(ItemList.playerFleshCooked, ItemList.playerFleshCookedName);
		SimpleItemsRegistry.registerItems(ItemList.villagerFleshCooked, ItemList.villagerFleshCookedName);

	}

}
