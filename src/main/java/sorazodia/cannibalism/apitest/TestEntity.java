package sorazodia.cannibalism.apitest;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import sorazodia.cannibalism.api.ICutable;

public class TestEntity extends EntityCreeper implements ICutable
{

	public TestEntity(World p_i1733_1_)
	{
		super(p_i1733_1_);
	}

	@Override
	public void cut(EntityPlayer player)
	{
		this.dropItem(Items.diamond_hoe, 1);
		this.attackEntityFrom(DamageSource.causePlayerDamage(player), 3.0F);
		
	}
	

}
