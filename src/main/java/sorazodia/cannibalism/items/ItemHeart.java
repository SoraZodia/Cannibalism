package sorazodia.cannibalism.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import sorazodia.cannibalism.main.Cannibalism;
import sorazodia.cannibalism.main.ItemRegistry;
import sorazodia.cannibalism.mechanic.nbt.CannibalismNBT;

public class ItemHeart extends Item
{
	public ItemHeart(String name)
	{
		super();
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(Cannibalism.cannibalismTab);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) 
	{
		ItemStack heldHeart = player.getHeldItem(hand);
		CannibalismNBT nbt = CannibalismNBT.getNBT(player);
		
		if (!nbt.hasHeart() && heldHeart.getItem() == ItemRegistry.groundedheart) {
			nbt.setHeart(true);
			nbt.setWarningEffect(true);
			nbt.setWendigoValue(500);
			if (!player.capabilities.isCreativeMode)
				heldHeart.shrink(1);
		}
		return new ActionResult<>(EnumActionResult.SUCCESS, heldHeart);
	}
}
