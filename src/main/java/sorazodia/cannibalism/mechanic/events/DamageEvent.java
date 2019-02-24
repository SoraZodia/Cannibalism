package sorazodia.cannibalism.mechanic.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import sorazodia.cannibalism.main.Cannibalism;
import sorazodia.cannibalism.mechanic.nbt.CannibalismNBT;

public class DamageEvent
{
	@SubscribeEvent
	public void onPlayerAttacked(LivingDamageEvent event) 
	{
		if (event.getEntityLiving() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			CannibalismNBT playerData = CannibalismNBT.getNBT(player);
			
			if (event.getSource().getTrueSource() instanceof EntityPlayer) 
			{
				EntityPlayer attacker = (EntityPlayer) event.getSource().getTrueSource();
				String heirloomOwner = "";
				boolean attackerHasHeirloom = (attacker.getHeldItemOffhand().getTagCompound() != null && (heirloomOwner =  attacker.getHeldItemOffhand().getTagCompound().getCompoundTag(Cannibalism.MODID).getString(InteractionEvent.HEIRLOOM_NBT_TAG)).length() > 0)
		                  || (attacker.getHeldItemMainhand().getTagCompound() != null && (heirloomOwner =  attacker.getHeldItemMainhand().getTagCompound().getCompoundTag(Cannibalism.MODID).getString(InteractionEvent.HEIRLOOM_NBT_TAG)).length() > 0);
				if (playerData.hasHeart() && attackerHasHeirloom)
				{
					if (player.getName().equals(heirloomOwner))
						event.setAmount(event.getAmount() * 1.5f);
				}
						
			}
			if (event.getSource().isFireDamage())
			{
				if (playerData.getWendigoValue() >= 100)
					event.setAmount(event.getAmount() * 1.5f);
				else
					playerData.changeWendigoValue(-20);
			}
		}
	}

}
