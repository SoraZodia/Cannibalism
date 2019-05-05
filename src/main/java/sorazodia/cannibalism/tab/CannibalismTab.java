package sorazodia.cannibalism.tab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sorazodia.cannibalism.main.ItemRegistry;

public class CannibalismTab extends CreativeTabs
{

	public CannibalismTab()
	{
		super("cannibalism");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getTabIconItem()
	{
		return new ItemStack(ItemRegistry.devKnife);
	}

}
