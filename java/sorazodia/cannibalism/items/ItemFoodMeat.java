package sorazodia.cannibalism.items;

import net.minecraft.item.ItemFood;

public class ItemFoodMeat extends ItemFood{

	public ItemFoodMeat(int hunger, float saturation, boolean isWolfFood) {
		super(hunger, saturation, isWolfFood);
	}

	public ItemFoodMeat(int hunger, float saturation) {
		this(hunger, saturation, true);
	}

}
