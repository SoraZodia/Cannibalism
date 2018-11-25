package sorazodia.cannibalism.items;

import net.minecraft.item.Item;
import sorazodia.cannibalism.main.Cannibalism;

public class ItemHeirloom extends Item{
	public ItemHeirloom (String name)
	{
		super();
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(Cannibalism.cannibalismTab);
	}

}
