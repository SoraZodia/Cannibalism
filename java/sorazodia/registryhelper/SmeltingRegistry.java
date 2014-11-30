package sorazodia.registryhelper;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * A way to add new furnace recipes with less typing
 * @author SoraZodia
 */
public class SmeltingRegistry {

	/**
	 *Register the Items into the furnace 
	 */
	public static void addSmelting(Item input, Item output, float xpGained){
		GameRegistry.addSmelting(new ItemStack(input), new ItemStack(output), xpGained);
	}
	
	/**
	 *Register the Items into the furnace
	 */
	public static void addSmelting(Item input, Item output){
		GameRegistry.addSmelting(new ItemStack(input), new ItemStack(output), 0.5F);
	}
	
}
