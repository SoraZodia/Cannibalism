package sorazodia.cannibalism.mechanic.events;

import net.minecraftforge.fml.common.eventhandler.Event;

public class LevelingEvent extends Event
{
	public float increaseLevelBy = 10;
	public float increaseRateBy = 10;
	
	public LevelingEvent(float increaseLevelBy, float increaseRateBy) 
	{
		this.increaseLevelBy = increaseLevelBy;
		this.increaseRateBy = increaseRateBy;
	}

}
