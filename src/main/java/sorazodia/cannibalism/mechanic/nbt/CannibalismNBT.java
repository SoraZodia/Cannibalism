package sorazodia.cannibalism.mechanic.nbt;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.INBTSerializable;

public class CannibalismNBT implements Capability.IStorage<CannibalismNBT>, INBTSerializable<NBTTagCompound>
{
	@CapabilityInject(CannibalismNBT.class)
	public static Capability.IStorage<CannibalismNBT> INSTANCE = null;
	
	public static final String NBTNAME = "cannibalismVariables";
	private float wendigoLevel;
	private boolean spawnWendigo;
	private boolean applyEffect;

	public CannibalismNBT()
	{
		this.wendigoLevel = 0;
		this.spawnWendigo = false;
		this.applyEffect = true;
	}

	@Override
	public NBTBase writeNBT(Capability<CannibalismNBT> capability, CannibalismNBT instance, EnumFacing side)
	{
		return generateNBT();
	}

	@Override
	public void readNBT(Capability<CannibalismNBT> capability, CannibalismNBT instance, EnumFacing side, NBTBase nbtBase)
	{
		initNBTData(((NBTTagCompound)nbtBase).getCompoundTag(NBTNAME));
	}
	
	@Override
	public NBTTagCompound serializeNBT()
	{
		return generateNBT();
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		initNBTData(nbt);
	}
	
	private NBTTagCompound generateNBT()
	{
		NBTTagCompound compound = new NBTTagCompound();
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setFloat("wendigo", wendigoLevel);
		nbt.setBoolean("wendigoExist", spawnWendigo);
		nbt.setBoolean("applyWarningEffect", applyEffect);
		compound.setTag(NBTNAME, nbt);
		
		return compound;
	}
	
	private void initNBTData(NBTTagCompound nbt)
	{
		NBTTagCompound data = nbt.getCompoundTag(NBTNAME);
		wendigoLevel = data.getFloat("wendigo");
		spawnWendigo = data.getBoolean("wendigoExist");
		applyEffect = data.getBoolean("applyWarningEffect");
	}

	public void changeWendigoValue(float amount)
	{
		wendigoLevel += amount;
	}

	public void setWendigoValue(float amount)
	{
		wendigoLevel = amount;
	}

	public float getWendigoValue()
	{
		return wendigoLevel;
	}

	public static CannibalismNBT getNBT(EntityLivingBase entity)
	{
		return (CannibalismNBT) INSTANCE;
	}

	public boolean wendigoSpawned()
	{
		return spawnWendigo;
	}

	public void setWedigoSpawn(boolean doSpawn)
	{
		spawnWendigo = doSpawn;
	}
	
	public boolean doWarningEffect()
	{
		return applyEffect;
	}

	public void setWarningEffect(boolean doApply)
	{
		applyEffect = doApply;
	}

//	public static final void register(EntityLivingBase entity)
//	{
//		entity.registerExtendedProperties(NBTNAME, new CannibalismNBT());
//	}

}
