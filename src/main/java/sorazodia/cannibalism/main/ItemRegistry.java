package sorazodia.cannibalism.main;

import sorazodia.api.registryhelper.SimpleItemsRegistry;

import static sorazodia.cannibalism.items.manager.ItemList.*;

public class ItemRegistry
{

	public static void init()
	{
		SimpleItemsRegistry.init(Cannibalism.MODID, Cannibalism.cannibalismTab);

		// initializate the knifes
		SimpleItemsRegistry.registerItems(woodenKnife, woodenKnifeName, woodenKnifeTexture);
		SimpleItemsRegistry.registerItems(flintKnife, flintKnifeName, flintKnifeTexture);
		SimpleItemsRegistry.registerItems(stoneKnife, stoneKnifeName, stoneKnifeTexture);
		SimpleItemsRegistry.registerItems(goldKnife, goldKnifeName, goldKnifeTexture);
		SimpleItemsRegistry.registerItems(ironKnife, ironKnifeName, ironKnifeTexture);
		SimpleItemsRegistry.registerItems(diamondKnife, diamondKnifeName, diamondKnifeTexture);
		SimpleItemsRegistry.registerItems(devKnife, devKnifeName, flintKnifeTexture);

		// initializate the flesh
		SimpleItemsRegistry.registerItems(playerFlesh, playerFleshName, playerFleshTexture);
		SimpleItemsRegistry.registerItems(villagerFlesh, villagerFleshName, villagerFleshTexture);
		SimpleItemsRegistry.registerItems(witchFlesh, witchFleshName, witchFleshTexture);
		SimpleItemsRegistry.registerItems(playerFleshCooked, playerFleshCookedName, playerFleshCookedTexture);
		SimpleItemsRegistry.registerItems(villagerFleshCooked, villagerFleshCookedName, villagerFleshCookedTexture);

	}

}
