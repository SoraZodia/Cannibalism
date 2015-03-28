package sorazodia.cannibalism.items;

import net.minecraft.item.ItemFood;

public class ItemFlesh extends ItemFood
{

	public ItemFlesh(int hunger, float saturation, boolean isWolfFood)
	{
		super(hunger, saturation, isWolfFood);
	}

	public ItemFlesh(int hunger, float saturation)
	{
		this(hunger, saturation, true);
	}

}
