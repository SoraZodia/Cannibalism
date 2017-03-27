package sorazodia.api.localization;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class Chat
{

	public static void displayPlainChat(EntityPlayer receiver, String text)
	{
		receiver.sendMessage(new TextComponentString(text));
	}

	public static void displayLocalizatedChat(EntityPlayer receiver, String unlocalizatedText)
	{
		displayLocalizatedChat(receiver, unlocalizatedText, TextFormatting.WHITE);
	}
	
	public static void displayLocalizatedChat(EntityPlayer receiver, String unlocalizatedText, Object... variables)
	{
		displayLocalizatedChat(receiver, unlocalizatedText, TextFormatting.WHITE, variables);
	}

	public static void displayLocalizatedChat(EntityPlayer receiver, String unlocalizatedText, TextFormatting color)
	{
		displayLocalizatedChat(receiver, unlocalizatedText, color, "");
	}
	
	public static void displayLocalizatedChat(EntityPlayer receiver, String unlocalizatedText, TextFormatting color, Object... variables)
	{
		receiver.sendMessage(new TextComponentTranslation(unlocalizatedText, variables).setStyle(new Style().setColor(color)));
	}

}
