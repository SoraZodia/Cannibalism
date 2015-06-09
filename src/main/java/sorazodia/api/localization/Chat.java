package sorazodia.api.localization;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class Chat
{
	
	public static void displayLocalizatedChat(EntityPlayer receiver, String unlocalizatedText)
	{
		displayLocalizatedChat(receiver, unlocalizatedText, EnumChatFormatting.WHITE);
	}
	
	public static void displayPlainChat(EntityPlayer receiver, String text)
	{
	   receiver.addChatComponentMessage(new ChatComponentText(text));
	}
	
	public static void displayLocalizatedChat(EntityPlayer receiver, String unlocalizatedText, EnumChatFormatting color)
	{
		receiver.addChatMessage(new ChatComponentTranslation(unlocalizatedText).setChatStyle(new ChatStyle().setColor(color)));
	}
	
	public static void displayLocalizatedChat(EntityPlayer receiver, String unlocalizatedText, EnumChatFormatting color, Object... variables)
	{
		receiver.addChatMessage(new ChatComponentTranslation(unlocalizatedText, variables).setChatStyle(new ChatStyle().setColor(color)));
	}

}
