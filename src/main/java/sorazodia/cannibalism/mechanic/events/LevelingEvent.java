package sorazodia.cannibalism.mechanic.events;

import java.util.Optional;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class LevelingEvent extends Event
{
	public float increaseLevelBy = 10;
	public float increaseRateBy = 10;
	private EntityPlayer player = null;
	
	public LevelingEvent(EntityPlayer player, float increaseLevelBy, float increaseRateBy) 
	{
		this.player = player;
		this.increaseLevelBy = increaseLevelBy;
		this.increaseRateBy = increaseRateBy;
	}
	
	public Optional<EntityPlayer> getPlayer() {
		return Optional.ofNullable(this.player);
	}

}
