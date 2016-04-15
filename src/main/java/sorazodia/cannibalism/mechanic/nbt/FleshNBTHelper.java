package sorazodia.cannibalism.mechanic.nbt;

import com.google.common.base.Optional;

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

	public static void addProfession(ItemStack stack, String job)
	{
		NBTTagCompound modID = new NBTTagCompound();
		NBTTagCompound tag = new NBTTagCompound();

		tag.setString("profession", job);
		modID.setTag(Cannibalism.MODID, tag);

		stack.setTagCompound(modID);
	}

	public static String getProfession(ItemStack stack)
	{
		Optional<NBTTagCompound> tag = Optional.fromNullable(stack.getTagCompound().getCompoundTag(Cannibalism.MODID));

		if (!tag.isPresent())
			return " ";

		Optional<String> profession = Optional.fromNullable(tag.get().getString("profession"));

		if (profession.isPresent())
			return profession.get();
		else
			return " ";
	}

	public static void setItemNickname(ItemStack stack)
	{
		NBTTagCompound tag = stack.getTagCompound().getCompoundTag(Cannibalism.MODID);
		stack.setStackDisplayName(tag.getString("name"));
	}

}
