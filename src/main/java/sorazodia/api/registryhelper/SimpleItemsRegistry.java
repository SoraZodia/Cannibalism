package sorazodia.api.registryhelper;

import java.util.Random;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Item registry class for Cannibalism mod, have methods to make item
 * registation in the GameRegistry a bit easier/less typing. THE init() METHOD
 * MUST BE CALLED FOR IT TO WORK
 * 
 * @author SoraZodia
 */
public class SimpleItemsRegistry
{

	private static String MODID;
	private static CreativeTabs tabs;

	/**
	 * Intitizate the MODID and CreativeTabs variable for the rest of the class
	 * to use. This must be called before any other methods in this class or
	 * funny stuff may happen.
	 * 
	 * @param MOD_ID_String
	 * @param CreativeTabs
	 */
	public static void init(String modID, CreativeTabs tab)
	{
		MODID = modID;
		tabs = tab;
	}

	/**
	 * A more steamline way to register your items, does all of the extra stuff
	 * for you, just make sure your item object is initializated
	 */
	public static void registerItems(Item item, String itemName, String imageName)
	{
		GameRegistry.registerItem(item, itemName, MODID).setCreativeTab(tabs).setUnlocalizedName(itemName).setTextureName(MODID
				+ ":" + imageName);
	}

	/**
	 * This method is used when the item's name is the same as its texture name.
	 * Helps reduce repetivite typing #lazy:P. Please make sure your item object
	 * is initializated
	 */
	public static void registerItems(Item item, String name)
	{
		GameRegistry.registerItem(item, name, MODID).setCreativeTab(tabs).setUnlocalizedName(name).setTextureName(MODID
				+ ":" + name);
	}

	/**
	 * This one... allows for alternate names... under... conditions
	 * 
	 * @param item
	 * @param name
	 */
	public static void registerEasterItems(Item item, String name, String easterName)
	{
		GameRegistry.registerItem(item, name, MODID).setCreativeTab(tabs).setUnlocalizedName(doEasterEgg(name, easterName)).setTextureName(MODID
				+ ":" + name);
	}

	private static String doEasterEgg(String defaultName, String easterName)
	{
		String output;
		Random rand = new Random();
		short chance = (short) rand.nextInt(1000000);

		if (chance == 1)
			output = easterName;
		else
			output = defaultName;

		return output;
	}

}
