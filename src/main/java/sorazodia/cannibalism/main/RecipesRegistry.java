package sorazodia.cannibalism.main;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import sorazodia.api.registryhelper.OreDicRecipe;
import sorazodia.cannibalism.items.manager.ItemList;

public class RecipesRegistry
{

	private static String diamond = "gemDiamond";
	private static String gold = "ingotGold";
	private static String iron = "ingotIron";
	private static String cobblestone = "cobblestone";
	private static String stone = "stone";
	private static String woodPlank = "plankWood";
	private static Item flint = Items.flint;

	private static ItemStack woodenKnife = new ItemStack(ItemList.woodenKnife);
	private static ItemStack flintKnife = new ItemStack(ItemList.flintKnife);
	private static ItemStack stoneKnife = new ItemStack(ItemList.stoneKnife);
	private static ItemStack goldKnife = new ItemStack(ItemList.goldKnife);
	private static ItemStack ironKnife = new ItemStack(ItemList.ironKnife);
	private static ItemStack diamondKnife = new ItemStack(ItemList.diamondKnife);

	private static String stickVanilla = "stickWood";
	private static String stickTConsturct = "rodWood";

	public static void init()
	{
		OreDicRecipe.shapedOreSimplifer(woodenKnife, " x ", "  y", 'x', woodPlank, 'y', stickVanilla);
		OreDicRecipe.shapedOreSimplifer(woodenKnife, "x  ", " y ", 'x', woodPlank, 'y', stickTConsturct);

		OreDicRecipe.shapedOreSimplifer(flintKnife, " x ", "  y", 'x', flint, 'y', stickVanilla);
		OreDicRecipe.shapedOreSimplifer(flintKnife, "x  ", " y ", 'x', flint, 'y', stickTConsturct);

		OreDicRecipe.shapedOreSimplifer(stoneKnife, " x ", "  y", 'x', cobblestone, 'y', stickVanilla);
		OreDicRecipe.shapedOreSimplifer(stoneKnife, "x  ", " y ", 'x', cobblestone, 'y', stickTConsturct);

		OreDicRecipe.shapedOreSimplifer(stoneKnife, " x ", "  y", 'x', stone, 'y', stickVanilla);
		OreDicRecipe.shapedOreSimplifer(stoneKnife, "x  ", " y ", 'x', stone, 'y', stickTConsturct);

		OreDicRecipe.shapedOreSimplifer(goldKnife, " x ", "  y", 'x', gold, 'y', stickVanilla);
		OreDicRecipe.shapedOreSimplifer(goldKnife, "x  ", " y ", 'x', gold, 'y', stickTConsturct);

		OreDicRecipe.shapedOreSimplifer(ironKnife, " x ", "  y", 'x', iron, 'y', stickVanilla);
		OreDicRecipe.shapedOreSimplifer(ironKnife, "x  ", " y ", 'x', iron, 'y', stickTConsturct);

		OreDicRecipe.shapedOreSimplifer(diamondKnife, " x ", "  y", 'x', diamond, 'y', stickVanilla);
		OreDicRecipe.shapedOreSimplifer(diamondKnife, "x  ", " y ", 'x', diamond, 'y', stickTConsturct);
	}
}
