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
	public static String woodenKnifeName = "woodenknife";

	public static ItemKnife flintKnife = (ItemKnife) new ItemKnife(FLINT);
	public static String flintKnifeName = "flintknife";

	public static ItemKnife stoneKnife = (ItemKnife) new ItemKnife(Item.ToolMaterial.STONE);
	public static String stoneKnifeName = "stoneknife";

	public static ItemKnife goldKnife = (ItemKnife) new ItemKnife(Item.ToolMaterial.GOLD);
	public static String goldKnifeName = "goldknife";

	public static ItemKnife ironKnife = (ItemKnife) new ItemKnife(Item.ToolMaterial.IRON);
	public static String ironKnifeName = "ironknife";

	public static ItemKnife diamondKnife = (ItemKnife) new ItemKnife(Item.ToolMaterial.DIAMOND);
	public static String diamondKnifeName = "diamondknife";

	public static ItemKnife devKnife = (ItemDevKnife) new ItemDevKnife();
	public static String devKnifeName = "knifeoftesting";

	// Flesh variables + names
	public static ItemFlesh playerFlesh = (ItemFlesh) new ItemFlesh(4, 0.3F);
	public static String playerFleshName = "playerflesh";

	public static ItemFlesh villagerFlesh = (ItemFlesh) new ItemFlesh(4, 0.3F);
	public static String villagerFleshName = "villagerflesh";
	
	public static ItemFlesh witchFlesh = (ItemFlesh) new ItemFlesh(4, 0.3F);
	public static String witchFleshName = "witchflesh";

	public static ItemFlesh playerFleshCooked = (ItemFlesh) new ItemFlesh(8, 0.8F);
	public static String playerFleshCookedName = "playerfleshcooked";

	public static ItemFlesh villagerFleshCooked = (ItemFlesh) new ItemFlesh(8, 0.8F);
	public static String villagerFleshCookedName = "villagerfleshcooked";

}
