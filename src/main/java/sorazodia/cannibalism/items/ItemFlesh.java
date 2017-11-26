package sorazodia.cannibalism.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import sorazodia.cannibalism.config.ConfigHandler;
import sorazodia.cannibalism.main.Cannibalism;
import sorazodia.cannibalism.main.ItemRegistry;
import sorazodia.cannibalism.mechanic.nbt.CannibalismNBT;

public class ItemFlesh extends ItemFood
{

	public ItemFlesh(String name, int hunger, float saturation, boolean isWolfFood)
	{
		super(hunger, saturation, isWolfFood);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(Cannibalism.cannibalismTab);
	}

	public ItemFlesh(String name, int hunger, float saturation)
	{
		this(name, hunger, saturation, true);
	}

	@Override
	public void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
	{
		if (ConfigHandler.allowMyth() == true && !world.isRemote)
		{
			CannibalismNBT nbt = CannibalismNBT.getNBT(player);
			float wendigoLevel = nbt.getWendigoValue();
			
			nbt.changeWendigoValue(10);
			player.getFoodStats().addStats((int)wendigoLevel / 10, wendigoLevel / 10);
			//System.out.println(stack.getItem().getRegistryName());
			if (stack.getItem() == ItemRegistry.witchFlesh)
			{
				if (wendigoLevel >= 100)
					player.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 1, 1));
				else
					player.addPotionEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE, 1, 1));
			}
		}
		else if (!world.isRemote)
		{
			if (stack.getItem() == ItemRegistry.witchFlesh)
				player.addPotionEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE, 1, 1));
		}
	}

}
