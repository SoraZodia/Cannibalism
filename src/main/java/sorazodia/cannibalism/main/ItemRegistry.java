package sorazodia.cannibalism.main;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.registries.IForgeRegistry;
import sorazodia.cannibalism.config.ConfigHandler;
import sorazodia.cannibalism.items.ItemDevKnife;
import sorazodia.cannibalism.items.ItemFlesh;
import sorazodia.cannibalism.items.ItemHeart;
import sorazodia.cannibalism.items.ItemKnife;
import sorazodia.cannibalism.items.KnifeType;

public class ItemRegistry
{
	
	// Developer's item
	public static ItemKnife devKnife = (ItemDevKnife) new ItemDevKnife();

	// Flesh variables + names
	public static ItemFlesh playerFlesh = (ItemFlesh) new ItemFlesh("playerflesh", 4, 0.3F);

	public static ItemFlesh villagerFlesh = (ItemFlesh) new ItemFlesh("villagerflesh", 4, 0.3F);

	public static ItemFlesh witchFlesh = (ItemFlesh) new ItemFlesh("witchflesh", 4, 0.3F);

	public static ItemFlesh playerFleshCooked = (ItemFlesh) new ItemFlesh("playerfleshcooked", 8, 0.8F);

	public static ItemFlesh villagerFleshCooked = (ItemFlesh) new ItemFlesh("villagerfleshcooked", 8, 0.8F);
	
	public static ItemHeart heart = new ItemHeart("wendigoheart");
	
	public static ItemHeart groundedheart = new ItemHeart("groundedplayerheart");
	
	public static void register(IForgeRegistry<Item> registry)
	{
		ItemRegistry.addSelectedKnifes(registry);
		ItemRegistry.addOtherItems(registry);		
	}

	private static void addSelectedKnifes(IForgeRegistry<Item> registry)
	{
		// devKnife will always be enable
		registry.register(devKnife);
		ItemRegistry.addTexture(devKnife);
		
		for(KnifeType type : ConfigHandler.getEnabledKnifeList()) 
		{
			ItemKnife knife = type.getKnife();
			registry.register(knife);
			ItemRegistry.addTexture(knife);
		}
	}
	
	private static void addOtherItems(IForgeRegistry<Item> registry)
	{
		registry.registerAll(playerFlesh, villagerFlesh, witchFlesh, playerFleshCooked, villagerFleshCooked, heart , groundedheart);
		ItemRegistry.addTexture(playerFlesh);
		ItemRegistry.addTexture(villagerFlesh);
		ItemRegistry.addTexture(witchFlesh);
		ItemRegistry.addTexture(playerFleshCooked);
		ItemRegistry.addTexture(villagerFleshCooked);
		ItemRegistry.addTexture(heart);
		ItemRegistry.addTexture(groundedheart);
	}
	
	
	public static void addTexture(Item item) {
		 ItemRegistry.addTexture(item, item.getRegistryName().toString());
	}
	
	public static void addTexture(Item item, String name) {
		 if(FMLCommonHandler.instance().getSide().isClient())
	            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(name, "inventory"));
	}
}
