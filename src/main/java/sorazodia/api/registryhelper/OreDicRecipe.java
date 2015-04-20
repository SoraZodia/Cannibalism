package sorazodia.api.registryhelper;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Makes adding recipes via the OreDictionary a bit more simple
 * 
 * @author SoraZodia
 */
public class OreDicRecipe
{

	/**
	 * Acts like GameRegistry.addShapedRecipe() but with OreDictionary support
	 * 
	 * @param What
	 *            item/block will be make
	 * @param The
	 *            pattern which the item/block is crafted from
	 */
	public static void shapedOreSimplifer(ItemStack output, Object... input)
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(output, input));
	}

}
