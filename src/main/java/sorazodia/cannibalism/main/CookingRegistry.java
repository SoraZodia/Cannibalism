package sorazodia.cannibalism.main;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CookingRegistry
{

	public static void init()
	{
		CookingRegistry.addSmelting(ItemRegistry.playerFlesh, ItemRegistry.playerFleshCooked, 0.3F);
		CookingRegistry.addSmelting(ItemRegistry.villagerFlesh, ItemRegistry.villagerFleshCooked, 0.3F);
	}
	
	/**
	 * Register the Items into the furnace
	 */
	public static void addSmelting(Item input, Item output, float xp)
	{
		GameRegistry.addSmelting(new ItemStack(input), new ItemStack(output), xp);
	}

	/**
	 * Register the Items into the furnace
	 */
	public static void addSmelting(Item input, Item output)
	{
		addSmelting(input, output, 0.5F);
	}

}
