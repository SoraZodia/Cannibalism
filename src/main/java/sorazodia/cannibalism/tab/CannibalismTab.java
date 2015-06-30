package sorazodia.cannibalism.tab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sorazodia.cannibalism.items.manager.ItemList;

public class CannibalismTab extends CreativeTabs
{

	public CannibalismTab()
	{
		super("cannibalism");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem()
	{
		return ItemList.ironKnife;
	}

}
