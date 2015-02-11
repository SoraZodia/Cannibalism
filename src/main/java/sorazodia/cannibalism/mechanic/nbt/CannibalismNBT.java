package sorazodia.cannibalism.mechanic.nbt;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class CannibalismNBT implements IExtendedEntityProperties
{
	public static final String NBTNAME = "cannibalismVariables";
	private float sanity = 100;
	private float hunger = 100;

	public CannibalismNBT(){
		this.hunger = this.sanity = 100;
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) 
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setFloat("hunger", hunger);
		nbt.setFloat("sanity", sanity);
		compound.setTag(NBTNAME, nbt);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		NBTTagCompound nbt = compound.getCompoundTag(NBTNAME);
		hunger = nbt.getFloat("hunger");
		sanity = nbt.getFloat("sanity");
	}

	@Override
	public void init(Entity entity, World world) 
	{
	}
	
	public void changeNBTValue(String nbtName, float amount)
	{
		switch(nbtName)
		{            
		case "hunger":
			hunger += amount;
			break;
			
		case "sanity":
			sanity += amount;
			break;
		}
	}
	
	public void setNBTValue(String nbtName, float amount)
	{
		switch(nbtName)
		{
		case "hunger":
			hunger = amount;
			break;
			
		case "sanity":
			sanity = amount;
			break;
		}
	}
	
	public float getNBTValue(String nbtName)
	{
		float value = 0;
		
		switch(nbtName)
		{
		case "hunger":
			value = hunger;
			break;
			
		case "sanity":
			value = sanity;
			break;
		}
		
		return value;
	}

	public static final CannibalismNBT getNBT(EntityLivingBase entity)
	{
		return (CannibalismNBT) entity.getExtendedProperties(NBTNAME);
	}
	
	public static final void register(EntityLivingBase entity)
	{
		entity.registerExtendedProperties(NBTNAME, new CannibalismNBT());
	}

}
