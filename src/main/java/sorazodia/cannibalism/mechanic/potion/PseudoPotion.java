package sorazodia.cannibalism.mechanic.potion;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.util.Constants;

public class PseudoPotion implements IExtendedEntityProperties
{

	public static final String NBTNAME = "pseudoPotionEffects";
	private NBTTagList potionsList = new NBTTagList();
	
	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		compound.setTag(NBTNAME, potionsList);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		potionsList = compound.getTagList(NBTNAME, Constants.NBT.TAG_COMPOUND);
	}

	@Override
	public void init(Entity entity, World world)
	{	
	}
	
	public void addPotions(String name, float duration, int powerLevel)
	{
		NBTTagCompound potion = new NBTTagCompound();
		NBTTagCompound potionInfo = new NBTTagCompound();
		potionInfo.setFloat("duration", duration);
		potionInfo.setInteger("powerLevel", powerLevel);
		potion.setTag(name, potionInfo);
		potionsList.appendTag(potion);
	}

}
