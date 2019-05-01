package sorazodia.cannibalism.tab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sorazodia.cannibalism.items.KnifeType;

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
		return new ItemStack(KnifeType.IRON.getKnife());
	}

}
