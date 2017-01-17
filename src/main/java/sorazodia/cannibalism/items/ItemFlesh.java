package sorazodia.cannibalism.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
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
		if (ConfigHandler.getMyth() == true && !world.isRemote)
		{
			CannibalismNBT nbt = CannibalismNBT.getNBT(player);
			float wendigoLevel = nbt.getWendigoValue();
			
			nbt.changeWendigoValue(10);
			player.getFoodStats().addStats((int)wendigoLevel / 10, wendigoLevel / 10);
			System.out.println(stack.getItem().getRegistryName());
			if (stack.getItem().getUnlocalizedName().contains(ItemList.witchFleshName))
			{
				if (wendigoLevel >= 100)
					player.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 1, 1));
				else
					player.addPotionEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE, 1, 1));
			}
		}
		else if (!world.isRemote)
		{
			if (stack.getItem().getUnlocalizedName().contains(ItemList.witchFleshName))
				player.addPotionEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE, 1, 1));
		}
	}

}
