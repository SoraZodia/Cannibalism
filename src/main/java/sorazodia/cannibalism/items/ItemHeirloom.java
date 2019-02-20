package sorazodia.cannibalism.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sorazodia.cannibalism.main.Cannibalism;

public class ItemHeirloom extends Item
{
	public ItemHeirloom(String name)
	{
		super();
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(Cannibalism.cannibalismTab);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing face,
			float hitX, float hitY, float hitZ)
	{
		return EnumActionResult.SUCCESS;
	}

}
