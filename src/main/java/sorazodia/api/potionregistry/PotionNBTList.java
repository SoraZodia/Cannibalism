package sorazodia.api.potionregistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.util.Constants;

public class PotionNBTList implements IExtendedEntityProperties
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
	
	protected void addPotions(String name, float duration, int powerLevel)
	{
		NBTTagCompound potionInfo = new NBTTagCompound();
		potionInfo.setFloat("duration", duration);
		potionInfo.setInteger("powerLevel", powerLevel);
		potionInfo.setString("name", name);
		potionsList.appendTag(potionInfo);
	}
	
	protected NBTTagList getNBTList()
	{
		return potionsList;
	}
	
	protected NBTTagCompound getCompoundFromList(int index)
	{
		return potionsList.getCompoundTagAt(index);
	}
	
	protected static final PotionNBTList getNBT(EntityLivingBase entity)
	{
		return (PotionNBTList) entity.getExtendedProperties(NBTNAME);
	}
	
	protected static void register(EntityLivingBase entity)
	{
		entity.registerExtendedProperties(NBTNAME, new PotionNBTList());
	}

}
