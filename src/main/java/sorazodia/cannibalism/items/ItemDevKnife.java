package sorazodia.cannibalism.items;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

import org.apache.logging.log4j.Level;

import sorazodia.api.localization.Chat;
import sorazodia.cannibalism.api.EntityData;
import sorazodia.cannibalism.config.JSONConfig;
import sorazodia.cannibalism.main.Cannibalism;
import sorazodia.cannibalism.mob.EntityWendigo;

import com.google.gson.JsonSyntaxException;

/**
 * Wa ha ha, My own special knife :P #evilDev
 * 
 * @author SoraZodia
 */
public class ItemDevKnife extends ItemKnife
{

	private static final ToolMaterial dev = EnumHelper.addToolMaterial("Dev", 4, -1, 100.0F, 0, 5);
	private JSONConfig json = Cannibalism.getJson();

	public ItemDevKnife()
	{
		super("knifeoftesting", dev);
	}

	@Override
	public boolean hitEntity(ItemStack item, EntityLivingBase victim, EntityLivingBase user)
	{
		World world = user.world;

		if (!(user instanceof EntityPlayer))
			return false;

		victim.attackEntityFrom(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);

		// As much as it would be funny to see players accidentally Wendigos,
		// don't really want that to happen...
		if (!world.isRemote)
		{
			if (user.isSneaking() && user.getUniqueID().equals(UUID.fromString("f10820b2-ad08-4b82-aca2-75b0445b6a1f")))
			{
				EntityWendigo wendigo = (EntityWendigo) EntityList.createEntityByIDFromName(new ResourceLocation(Cannibalism.MODID + ":wendigo"), world);
				wendigo.setLocationAndAngles(user.posX + 1, user.posY, user.posZ, 0, 0);
				world.spawnEntity(wendigo);
			}
		}

		return true;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BLOCK;
    }

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{

		if (!world.isRemote)
		{
			Chat.displayLocalizatedChat(player, "item.devKnife.reload");
			try
			{
				json.getEntityMap().clear();
				json.getWildcardMap().clear();
				json.initEntityMappings();
				Chat.displayLocalizatedChat(player, "item.devKnife.reloadFinish", TextFormatting.GREEN);
			}
			catch (JsonSyntaxException | NumberFormatException | ClassCastException | NullPointerException | IOException error)
			{
				if (error instanceof JsonSyntaxException)
					error(player, error.getMessage(), json.getFileName());
				else
					error(player, "item.devKnife.errorCharacter", json.getFileName());
			}
			catch (Exception wtfHappened)
			{
				error(player, "item.devKnife.errorUnknown", json.getFileName());
			}
		}

		return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));
	}

	private void error(EntityPlayer player, String errorMessage, String fileName)
	{
		Chat.displayLocalizatedChat(player, "item.devKnife.reloadFail", TextFormatting.RED);
		Chat.displayLocalizatedChat(player, "item.devKnife.reloadFailCheck", TextFormatting.WHITE, fileName);
		Chat.displayLocalizatedChat(player, errorMessage);
		json.loadMapData();
		Chat.displayLocalizatedChat(player, "item.devKnife.reloadFinish", TextFormatting.YELLOW);
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand)
	{
		if (!(player.world.isRemote))
		{
			if (!player.isSneaking())
			{
				Chat.displayLocalizatedChat(player, "item.devKnife.format");
				Chat.displayLocalizatedChat(player, "item.devKnife.mobName", EntityList.getEntityString(target));
				Chat.displayLocalizatedChat(player, "item.devKnife.superName", EntityList.getEntityString(getSuperEntity(target)));
			}
			else
			{
				if (json.checkEntity(target))
				{
					EntityData data = json.getData(target);
					Chat.displayPlainChat(player, data.toString());
				}

				else if (json.getWildCardIndex(target, player.world) >= 0)
				{
					EntityData data = json.getData(target, player.world);
					Chat.displayPlainChat(player, data.toString());
				}
				
				if (player.getUniqueID().equals(UUID.fromString("f10820b2-ad08-4b82-aca2-75b0445b6a1f"))) {
					NBTTagCompound nbt = target.getEntityData();
					if (nbt != null) {
						Chat.displayPlainChat(player, "Wendigo Strength: " + nbt.getInteger(EntityWendigo.nbtKey));
						Chat.displayPlainChat(player, "Wendigo Health: " + target.getHealth());
					}
				}
			}

		}

		return true;
	}

	@Override
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag tooltipFlage)
	{
		list.add(I18n.translateToLocal("item.devKnife.lore1"));
		list.add(I18n.translateToLocal("item.devKnife.lore2"));
	}

	private Entity getSuperEntity(Entity child)
	{
		Class<?> superClass;
		Entity entity = child;
		try
		{
			superClass = Class.forName(child.getClass().getSuperclass().getName());
			Constructor<?> cons = superClass.getConstructor(World.class);
			entity = (Entity) cons.newInstance(child.world);

		}
		catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			Cannibalism.getLogger().log(Level.INFO, "[Cannibalism - Dev Knife Usage] This entity extended from EntityMob or the likes, it has no mobs ingame which it is extended from");
		}
		catch (Exception wtfHappened)
		{
			Cannibalism.getLogger().log(Level.ERROR, "[Cannibalism - Dev Knife Usage] Unknown Error happened, stacktrace incoming");
			Cannibalism.getLogger().log(Level.ERROR, wtfHappened.getMessage());
		}
		return entity;
	}

}
