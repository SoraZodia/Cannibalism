package sorazodia.cannibalism.main;

import static sorazodia.cannibalism.main.Cannibalism.MODID;
import sorazodia.api.registryhelper.ModelRegistry;
import sorazodia.cannibalism.items.manager.ItemList;

public class ImageRegistry
{
	public static void init()
	{
		ModelRegistry.addTexture(ItemList.devKnife, MODID, ItemList.flintKnifeName);
		ModelRegistry.addTexture(ItemList.diamondKnife, MODID, ItemList.diamondKnifeName);
		ModelRegistry.addTexture(ItemList.flintKnife, MODID, ItemList.flintKnifeName);
		ModelRegistry.addTexture(ItemList.goldKnife, MODID, ItemList.goldKnifeName);
		ModelRegistry.addTexture(ItemList.ironKnife, MODID, ItemList.ironKnifeName);
		ModelRegistry.addTexture(ItemList.woodenKnife, MODID, ItemList.woodenKnifeName);
		
		ModelRegistry.addTexture(ItemList.playerFlesh, MODID, ItemList.playerFleshName);
		ModelRegistry.addTexture(ItemList.playerFleshCooked, MODID, ItemList.playerFleshCookedName);
		
		ModelRegistry.addTexture(ItemList.villagerFlesh, MODID, ItemList.villagerFleshName);
		ModelRegistry.addTexture(ItemList.villagerFleshCooked, MODID, ItemList.villagerFleshCookedName);
	}
}
