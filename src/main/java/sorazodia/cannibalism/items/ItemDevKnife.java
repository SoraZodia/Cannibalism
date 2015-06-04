package sorazodia.cannibalism.items;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import sorazodia.cannibalism.api.EntityData;
import sorazodia.cannibalism.config.JSONConfig;
import sorazodia.cannibalism.main.Cannibalism;
import sorazodia.cannibalism.mob.EntityWendigo;

/**
 * Wa ha ha, My own special knife :P #evilDev
 * 
 * @author SoraZodia
 */
public class ItemDevKnife extends ItemKnife
{

	private static final ToolMaterial dev = EnumHelper.addToolMaterial("Dev", 4, -1, 100.0F, -4, 5);
	private JSONConfig json = Cannibalism.getJson();

	public ItemDevKnife()
	{
		super(dev);
	}

	@Override
	public boolean hitEntity(ItemStack item, EntityLivingBase victim, EntityLivingBase user)
	{
		World world = user.worldObj;

		if (!(user instanceof EntityPlayer))
			return false;

		//As much as it would be funny to see players accidentally Wendigos, don't really want that to happen...
		if (!world.isRemote)
		{
			if (user.isSneaking()
					&& user.getUniqueID().equals(UUID.fromString("f10820b2-ad08-4b82-aca2-75b0445b6a1f")))
			{
				EntityWendigo wendigo = (EntityWendigo) EntityList.createEntityByName(Cannibalism.MODID
						+ ".wendigo", world);
				wendigo.setLocationAndAngles(user.posX, user.posY, user.posZ, 0, 0);
				world.spawnEntityInWorld(wendigo);
			}
		}

		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{

		player.setItemInUse(stack, getMaxItemUseDuration(stack));

		if (!(player.worldObj.isRemote) && player.isSneaking())
		{
			displayLocalizatedChat(player, "item.devKnife.reload");
			try
			{
				json.getEntityMap().clear();
				json.getWildcardMap().clear();
				json.read();
				displayLocalizatedChat(player, "item.devKnife.reloadFinish", EnumChatFormatting.GREEN);
			} 
			catch (com.google.gson.JsonSyntaxException syntax)
			{
				error(player, syntax);
			} 
			catch (IOException io)
			{
				error(player, io);
			}

		}

		return stack;
	}

	private void error(EntityPlayer player, Exception exception)
	{
		displayLocalizatedChat(player,"item.devKnife.reloadFail", EnumChatFormatting.RED);
		json.codeRed();
		player.addChatMessage(new ChatComponentText(exception.getMessage()));
		displayLocalizatedChat(player,"item.devKnife.reloadFinish", EnumChatFormatting.YELLOW);
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target)
	{
		if (!(player.worldObj.isRemote))
		{
			if (!player.isSneaking())
			{
				displayLocalizatedChat(player, "item.devKnife.format");
				player.addChatMessage(new ChatComponentTranslation("item.devKnife.mobName", EntityList.getEntityString(target)));
			} else
			{
				if (json.checkEntity(target))
				{
					EntityData data = json.getData(target);
					player.addChatMessage(new ChatComponentText(data.toString()));
				}

				else if (json.getWildCardIndex(target, player.worldObj) >= 0)
				{
					EntityData data = json.getData(target, player.worldObj);
					player.addChatMessage(new ChatComponentText(data.toString()));
				}
			}

		}

		return true;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		list.add(StatCollector.translateToLocal("item.devKnife.lore1"));
		list.add(StatCollector.translateToLocal("item.devKnife.lore2"));
	}
	
	private void displayLocalizatedChat(EntityPlayer receiver, String unlocalizatedText)
	{
		displayLocalizatedChat(receiver, unlocalizatedText, EnumChatFormatting.WHITE);
	}
	
	private void displayLocalizatedChat(EntityPlayer receiver, String unlocalizatedText, EnumChatFormatting color)
	{
		receiver.addChatMessage(new ChatComponentTranslation(unlocalizatedText).setChatStyle(new ChatStyle().setColor(color)));
	}

}
