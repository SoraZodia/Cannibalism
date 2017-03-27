package sorazodia.cannibalism.main;

import sorazodia.api.registryhelper.SimpleItemsRegistry;

import static sorazodia.cannibalism.items.manager.ItemList.*;

public class ItemRegistry
{

	public static void init()
	{
		SimpleItemsRegistry.init(Cannibalism.MODID, Cannibalism.cannibalismTab);

		// initializate the knifes
		SimpleItemsRegistry.registerItems(woodenKnife, woodenKnifeName);
		SimpleItemsRegistry.registerItems(flintKnife, flintKnifeName);
		SimpleItemsRegistry.registerItems(stoneKnife, stoneKnifeName);
		SimpleItemsRegistry.registerItems(goldKnife, goldKnifeName);
		SimpleItemsRegistry.registerItems(ironKnife, ironKnifeName);
		SimpleItemsRegistry.registerItems(diamondKnife, diamondKnifeName);
		SimpleItemsRegistry.registerItems(devKnife, devKnifeName, flintKnifeName);

		// initializate the flesh
		SimpleItemsRegistry.registerItems(playerFlesh, playerFleshName);
		SimpleItemsRegistry.registerItems(villagerFlesh, villagerFleshName);
		SimpleItemsRegistry.registerItems(witchFlesh, witchFleshName);
		SimpleItemsRegistry.registerItems(playerFleshCooked, playerFleshCookedName);
		SimpleItemsRegistry.registerItems(villagerFleshCooked, villagerFleshCookedName);

	}

}
