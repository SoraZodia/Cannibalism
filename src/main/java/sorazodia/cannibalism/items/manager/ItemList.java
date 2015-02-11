package sorazodia.cannibalism.items.manager;

import sorazodia.cannibalism.items.ItemFoodMeat;
import sorazodia.cannibalism.items.ItemKnife;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class ItemList 
{
	
	private static final ToolMaterial FLINT = EnumHelper.addToolMaterial("FLINT", 1, 100, 3.0F, 2.5F, 5);
	
	//Knife variables + names
	public static ItemKnife woodenKnife = (ItemKnife) new ItemKnife(Item.ToolMaterial.WOOD);
	public static String woodenKnifeName = "woodenKnife";
	
	public static ItemKnife flintKnife = (ItemKnife) new ItemKnife(FLINT);
	public static String flintKnifeName = "flintKnife";
	
	public static ItemKnife stoneKnife = (ItemKnife) new ItemKnife(Item.ToolMaterial.STONE);
	public static String stoneKnifeName = "stoneKnife";
	
	public static ItemKnife goldKnife =  (ItemKnife) new ItemKnife(Item.ToolMaterial.GOLD);	
	public static String goldKnifeName = "goldKnife";
	
	public static ItemKnife ironKnife =  (ItemKnife) new ItemKnife(Item.ToolMaterial.IRON);	
	public static String ironKnifeName = "ironKnife";
	
	public static ItemKnife diamondKnife = (ItemKnife) new ItemKnife(Item.ToolMaterial.EMERALD);
	public static String diamondKnifeName = "diamondKnife";
	
	//Flesh variables + names
	public static ItemFoodMeat playerFlesh = (ItemFoodMeat) new ItemFoodMeat(4, 1.0F);	
	public static String playerFleshName = "playerFlesh";
	
	public static ItemFoodMeat villagerFlesh = (ItemFoodMeat) new ItemFoodMeat(4, 1.0F);
	public static String villagerFleshName = "villagerFlesh";
	
	public static ItemFoodMeat playerFleshCooked = (ItemFoodMeat) new ItemFoodMeat(8, 2.0F);
	public static String playerFleshCookedName = "playerFleshCooked";
	
	public static ItemFoodMeat villagerFleshCooked = (ItemFoodMeat) new ItemFoodMeat(8, 2.0F);
	public static String villagerFleshCookedName = "villagerFleshCooked";
	
}
