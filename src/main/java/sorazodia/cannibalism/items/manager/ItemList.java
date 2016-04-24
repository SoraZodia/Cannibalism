package sorazodia.cannibalism.items.manager;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;
import sorazodia.cannibalism.items.ItemDevKnife;
import sorazodia.cannibalism.items.ItemFlesh;
import sorazodia.cannibalism.items.ItemKnife;

public class ItemList
{

	private static final ToolMaterial FLINT = EnumHelper.addToolMaterial("FLINT", 1, 100, 3.0F, 2.5F, 5);

	// Knife variables + names
	public static ItemKnife woodenKnife = (ItemKnife) new ItemKnife(Item.ToolMaterial.WOOD);
	public static String woodenKnifeName = "woodenKnife";

	public static ItemKnife flintKnife = (ItemKnife) new ItemKnife(FLINT);
	public static String flintKnifeName = "flintKnife";

	public static ItemKnife stoneKnife = (ItemKnife) new ItemKnife(Item.ToolMaterial.STONE);
	public static String stoneKnifeName = "stoneKnife";

	public static ItemKnife goldKnife = (ItemKnife) new ItemKnife(Item.ToolMaterial.GOLD);
	public static String goldKnifeName = "goldKnife";

	public static ItemKnife ironKnife = (ItemKnife) new ItemKnife(Item.ToolMaterial.IRON);
	public static String ironKnifeName = "ironKnife";

	public static ItemKnife diamondKnife = (ItemKnife) new ItemKnife(Item.ToolMaterial.EMERALD);
	public static String diamondKnifeName = "diamondKnife";

	public static ItemKnife devKnife = (ItemDevKnife) new ItemDevKnife();
	public static String devKnifeName = "knifeOfTesting";

	// Flesh variables + names
	public static ItemFlesh playerFlesh = (ItemFlesh) new ItemFlesh(4, 0.3F);
	public static String playerFleshName = "playerFlesh";

	public static ItemFlesh villagerFlesh = (ItemFlesh) new ItemFlesh(4, 0.3F);
	public static String villagerFleshName = "villagerFlesh";
	
	public static ItemFlesh witchFlesh = (ItemFlesh) new ItemFlesh(4, 0.3F);
	public static String witchFleshName = "witchFlesh";

	public static ItemFlesh playerFleshCooked = (ItemFlesh) new ItemFlesh(8, 0.8F);
	public static String playerFleshCookedName = "playerFleshCooked";

	public static ItemFlesh villagerFleshCooked = (ItemFlesh) new ItemFlesh(8, 0.8F);
	public static String villagerFleshCookedName = "villagerFleshCooked";

}
