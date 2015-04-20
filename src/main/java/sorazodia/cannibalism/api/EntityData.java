package sorazodia.cannibalism.api;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EntityData
{
	private ItemStack[] drops;
	private float minDamage;
	private float maxDamage;

	public EntityData(String[] drop, float min, float max)
	{
		drops = covertString(drop);
		minDamage = min;
		maxDamage = max;
	}

	private ItemStack[] covertString(String[] convert)
	{
		ItemStack[] drops = new ItemStack[convert.length];
		for (int x = 0; x < drops.length; x++)
		{
			drops[x] = new ItemStack((Item) Item.itemRegistry.getObject(convert[x]));
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
}