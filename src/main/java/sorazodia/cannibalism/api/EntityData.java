package sorazodia.cannibalism.api;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityData implements Comparable<EntityData>
{
	private ItemStack[] drops;
	private String[] itemList;
	private float minDamage;
	private float maxDamage;
	private String name;

	public EntityData(String[] drop, float min, float max)
	{
		this("", drop, min, max);
	}

	public EntityData(String entityName, String[] drop, float min, float max)
	{
		drops = covertString(drop);
		itemList = drop;
		minDamage = min;
		maxDamage = max;
		setName(entityName);
		// System.out.println(toString());
	}

	private ItemStack[] covertString(String[] str)
	{
		ItemStack[] drops = new ItemStack[str.length];
		for (int x = 0; x < drops.length; x++)
		{
			String[] itemData = str[x].trim().split("#");
			int metadata = 0;

			if (itemData.length >= 3) //Just in case someone used # as part of the item unlocalizated name...
			{
				StringBuilder concat = new StringBuilder();
				for (int y = 0; y < itemData.length - 1; y++)
				{
					concat.append("#");
					concat.append(itemData[y]);
				}
				concat.deleteCharAt(0); //delete the extra #
				itemData[0] = concat.toString();
				itemData[1] = itemData[itemData.length - 1];
			}
			if (itemData.length > 1)
				if (isInteger(itemData[1]))
					metadata = Integer.parseInt(itemData[1]);

			Item item = (Item) Item.REGISTRY.getObject(new ResourceLocation(itemData[0]));

			drops[x] = new ItemStack(item, 1, metadata);
		}

		return drops;
	}

	public ItemStack[] getDrops()
	{
		return drops;
	}

	public void setDrops(ItemStack[] drops)
	{
		this.drops = drops;
	}

	public float getMinDamage()
	{
		return minDamage;
	}

	public void setMinDamage(float minDamage)
	{
		if(minDamage < 0)
			minDamage = 0;
		
		this.minDamage = minDamage;
	}

	public float getMaxDamage()
	{
		return maxDamage;
	}

	public void setMaxDamage(float maxDamage)
	{
		this.maxDamage = maxDamage;
	}

	public EntityLivingBase getEntity(World world)
	{
		try
		{
			return (EntityLivingBase) EntityList.createEntityByName(name, world);
		}
		catch (NullPointerException nu)
		{
			return null;
		}
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	//From http://stackoverflow.com/questions/237159/whats-the-best-way-to-check-to-see-if-a-string-represents-an-integer-in-java
	private boolean isInteger(String arg)
	{
		if (arg == null)
			return false;

		int length = arg.length();

		if (length == 0)
			return false;

		int x = 0;

		if (arg.charAt(0) == '-')
		{
			if (length == 1)
				return false;
			x = 1;
		}

		for (; x < length; x++)
		{
			char c = arg.charAt(x);
			if (c <= '/' || c >= ':')
				return false;
		}

		return true;
	}

	@Override
	public String toString()
	{
		StringBuilder drops = new StringBuilder("[ ");

		for (String s : itemList)
			drops.append(s).append(", ");

		drops.deleteCharAt(drops.lastIndexOf(",")).append("]");

		return "EntityID: " + getName() + ", Drops: " + drops.toString() + ", Min/Max Damage: " + getMinDamage() + "/" + getMaxDamage();
	}

	@Override
	public int compareTo(EntityData secordData)
	{
		String name = secordData.getName();
		
		return this.getName().compareTo(name);
	}

}