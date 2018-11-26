package sorazodia.cannibalism.mechanic.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import sorazodia.cannibalism.config.ConfigHandler;
import sorazodia.cannibalism.main.Cannibalism;
import sorazodia.cannibalism.mechanic.nbt.CannibalismNBT;

public class InteractionEvent
{
	public static final String NBT_TAG = "heirloom";
	
	@SubscribeEvent
	public void onPlayerRightClick(RightClickItem event) 
	{
		EntityPlayer player = event.getEntityPlayer();
		World world = event.getWorld();
		ItemStack heldStack = event.getItemStack();
		
		if (ConfigHandler.allowMyth() == true && player.isSneaking() && !world.isRemote) 
		{
			CannibalismNBT nbt = CannibalismNBT.getNBT(player);
			if (nbt.getWendigoValue() <= 0 && nbt.getHeirloomCount() <= 3) 
			{
				NBTTagCompound tag = new NBTTagCompound();
				tag.setInteger(InteractionEvent.NBT_TAG, 1);
				
				if (heldStack.getTagCompound() == null) heldStack.setTagCompound(new NBTTagCompound());
				heldStack.getTagCompound().setTag(Cannibalism.MODID, tag);
				
				nbt.increaseHeirloomCount();
			}
		}
	}

}
