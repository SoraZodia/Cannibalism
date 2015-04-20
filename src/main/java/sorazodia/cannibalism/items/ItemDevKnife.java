package sorazodia.cannibalism.items;

import java.util.List;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import sorazodia.cannibalism.main.Cannibalism;
import sorazodia.cannibalism.mechanic.events.EntityNBTEvents;
import sorazodia.cannibalism.mob.EntityWendigo;

/**
 * Wa ha ha, My own special knife :P #evilDev
 * 
 * @author SoraZodia
 */
public class ItemDevKnife extends ItemKnife
{

	private static final ToolMaterial dev = EnumHelper.addToolMaterial("Dev", 4, -1, 100.0F, 200000F, 5);
	private static boolean spawn = true;

	public ItemDevKnife()
	{
		super(dev);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		player.setItemInUse(stack, getMaxItemUseDuration(stack));

		if (!world.isRemote)
		{
			if (!player.isSneaking())
			{
				EntityWendigo wendigo = (EntityWendigo) EntityList.createEntityByName(Cannibalism.MODID
						+ ".wendigo", world);
				wendigo.setLocationAndAngles(player.posX, player.posY, player.posZ, 0, 0);
				world.spawnEntityInWorld(wendigo);
			} else
			{
				spawn = !spawn;
				EntityNBTEvents.setSpawn(spawn);
				player.addChatMessage(new ChatComponentText("Wendigo Spawn set to "
						+ spawn));
			}
		}

		return stack;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		list.add("FOR DEVELOPMENT TESTING ONLY");
	}

}
