package sorazodia.cannibalism.mechanic.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import sorazodia.cannibalism.config.ConfigHandler;
import sorazodia.cannibalism.main.Cannibalism;
import sorazodia.cannibalism.mechanic.nbt.CannibalismNBT;

public class InteractionEvent
{
	public static final String HEIRLOOM_NBT_TAG = "heirloom";
	
	@SubscribeEvent
	public void onPlayerRightClick(RightClickItem event) 
	{
		EntityPlayer player = event.getEntityPlayer();
		World world = event.getWorld();
		ItemStack heldStack = event.getItemStack();
		boolean isJumping = !(player.onGround || player.isOnLadder() || player.isInWater() || player.isRiding());
		
		if (ConfigHandler.allowMyth() == true)
		{
			if(player.isSneaking() && isJumping && !world.isRemote ) 
			{
				CannibalismNBT nbt = CannibalismNBT.getNBT(player);
				if (nbt.getWendigoValue() <= 0 && nbt.getHeirloomCount() <= 3) 
				{
					NBTTagCompound tag = new NBTTagCompound();
					tag.setString(InteractionEvent.HEIRLOOM_NBT_TAG, player.getName());

					if (heldStack.getTagCompound() == null) heldStack.setTagCompound(new NBTTagCompound());
					heldStack.getTagCompound().setTag(Cannibalism.MODID, tag);

					nbt.increaseHeirloomCount();
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onItemFinish(LivingEntityUseItemEvent.Finish event)
	{
		if (event.getEntityLiving() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			World world = player.world;
			ItemStack heldStack = event.getItem();
			
			if (ConfigHandler.processAsFlesh(Item.REGISTRY.getNameForObject(heldStack.getItem()).toString()).isPresent() && !world.isRemote)
			{
				float gluttonyIncrease = ConfigHandler.processAsFlesh(Item.REGISTRY.getNameForObject(heldStack.getItem()).toString()).get();
				CannibalismNBT nbt = CannibalismNBT.getNBT(player);

				nbt.changeWendigoValue(gluttonyIncrease);
			}
		}
	}

}
