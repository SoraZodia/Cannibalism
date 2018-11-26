package sorazodia.cannibalism.mechanic.events.client;

import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.resources.I18n;
import sorazodia.cannibalism.main.Cannibalism;
import sorazodia.cannibalism.mechanic.events.InteractionEvent;

public class TooltipEvent
{
	@SubscribeEvent()
	public void tooltipEvent (ItemTooltipEvent event)
	{
		if (event.getItemStack().getTagCompound() != null &&
			event.getItemStack().getTagCompound().getCompoundTag(Cannibalism.MODID).getInteger(InteractionEvent.NBT_TAG) > 0) 
		{
			event.getToolTip().add(I18n.format("hierloom.lore1", event.getEntityPlayer().getDisplayName().getFormattedText()));
			event.getToolTip().add(I18n.format("hierloom.lore2"));
		}
	}
}
