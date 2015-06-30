package sorazodia.api.registryhelper;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * A way to add new furnace recipes with less typing
 * 
 * @author SoraZodia
 */
public class SmeltingRegistry
{

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
