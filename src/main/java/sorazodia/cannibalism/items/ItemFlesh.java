package sorazodia.cannibalism.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import sorazodia.cannibalism.config.ConfigHandler;
import sorazodia.cannibalism.items.manager.ItemList;
import sorazodia.cannibalism.mechanic.nbt.CannibalismNBT;

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

	@Override
	public void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
	{
		if (CannibalismNBT.getNBT(player) != null && ConfigHandler.getMyth() == true && !world.isRemote)
		{
			CannibalismNBT nbt = CannibalismNBT.getNBT(player);
			float wendigoLevel = nbt.getWendigoValue();
			
			nbt.changeWendigoValue(10);
			player.getFoodStats().addStats((int)wendigoLevel / 10, wendigoLevel / 10);
			
			if (this.getUnlocalizedName().equals(ItemList.witchFleshName))
			{
				if (wendigoLevel >= 100)
					player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 1, 20));
				else
					player.addPotionEffect(new PotionEffect(Potion.harm.id, 1, 20));
			}
		}
		else
		{
			if (this.getUnlocalizedName().equals(ItemList.witchFleshName))
				player.addPotionEffect(new PotionEffect(Potion.harm.id, 1, 20));
		}
	}

}
