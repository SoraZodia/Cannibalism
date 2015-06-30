package sorazodia.cannibalism.mechanic.nbt;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import sorazodia.cannibalism.main.Cannibalism;

/**
 * Adds NBT to the player meat
 * 
 * @author SoraZodia
 */
public class MeatOriginNBT
{

	public static void addNameToNBT(ItemStack stack, String playerName)
	{
		NBTTagCompound modID = new NBTTagCompound();
		NBTTagCompound tag = new NBTTagCompound();

		tag.setString("name", playerName);
		modID.setTag(Cannibalism.MODID, tag);

		stack.setTagCompound(modID);
	}

	public static void getNameFromNBT(ItemStack stack)
	{
		NBTTagCompound tag = stack.getTagCompound().getCompoundTag(Cannibalism.MODID);
		stack.setStackDisplayName(tag.getString("name"));

	}

}
