package sorazodia.cannibalism.mechanic.nbt;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import sorazodia.cannibalism.main.Cannibalism;

/**
 * Adds NBT to the player meat
 * 
 * @author SoraZodia
 */
public class FleshNBTHelper
{

	public static void addName(ItemStack stack, String name)
	{
		NBTTagCompound modID = new NBTTagCompound();
		NBTTagCompound tag = new NBTTagCompound();

		tag.setString("name", name);
		modID.setTag(Cannibalism.MODID, tag);

		stack.setTagCompound(modID);
	}

	public static void setItemNickname(ItemStack stack)
	{
		NBTTagCompound tag = stack.getTagCompound().getCompoundTag(Cannibalism.MODID);
		stack.setStackDisplayName(tag.getString("name"));
	}

}
