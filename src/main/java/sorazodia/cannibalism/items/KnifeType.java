package sorazodia.cannibalism.items;

import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

public enum KnifeType
{
	// Knife variables + names
	WOOD(new ItemKnife("woodenknife", Item.ToolMaterial.WOOD)),
	FLINT(new ItemKnife("flintknife",EnumHelper.addToolMaterial("FLINT", 1, 100, 3.0F, 2.5F, 5))),
	STONE(new ItemKnife("stoneknife", Item.ToolMaterial.STONE)),
	GOLD(new ItemKnife("goldknife", Item.ToolMaterial.GOLD)),
	IRON(new ItemKnife("ironknife", Item.ToolMaterial.IRON)),
	DIAMOND(new ItemKnife("diamondknife", Item.ToolMaterial.DIAMOND));
	
	private ItemKnife knife = null;
	
	private KnifeType(ItemKnife knife)
	{
		this.knife = knife;
	}
	
	public ItemKnife getKnife()
	{
		return knife;
	}
}
