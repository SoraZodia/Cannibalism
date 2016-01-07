package sorazodia.cannibalism.server;

import java.util.ArrayList;
import java.util.List;

import sorazodia.cannibalism.main.Cannibalism;
import sorazodia.cannibalism.mechanic.nbt.CannibalismNBT;

import com.mojang.authlib.GameProfile;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChatComponentTranslation;

public class CommandWendigoLevel implements ICommand
{
	
	@Override
	public int compareTo(Object o)
	{
		return Cannibalism.MODID.compareTo(((ICommand) o).getCommandName());
	}

	@Override
	public String getCommandName()
	{
		return Cannibalism.MODID;
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "todo - Mainly used for debugging";
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List getCommandAliases()
	{
		return null;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args)
	{
		if (args.length >= 2)
		{
			EntityPlayerMP player =MinecraftServer.getServer().getConfigurationManager().func_152612_a(args[1]);
			CannibalismNBT nbt = CannibalismNBT.getNBT(player);
			
			switch (args[0])
			{
			case "set":
				nbt.setWendigoValue(Float.parseFloat(args[2]));
				break;
			case "stat":
				sender.addChatMessage(new ChatComponentTranslation("item.devknife.wendigostat", nbt.getWendigoValue()));
				break;
			}
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender)
	{
		return sender.canCommandSenderUseCommand(2, Cannibalism.MODID);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args)
	{
		ServerConfigurationManager manager = MinecraftServer.getServer().getConfigurationManager();
		ArrayList<String> argsList = new ArrayList<>();
		
		if (args.length >= 1)
		{
			String lastLetter = args[args.length - 1];
			GameProfile[] profiles = MinecraftServer.getServer().func_152357_F();

			if (args.length == 1)
			{
				argsList.add("stat");
				argsList.add("set");
			}

			for (int x = 0; x < profiles.length; x++)
			{
				if (manager.func_152596_g(profiles[x]) && lastLetter.regionMatches(true, 0, profiles[x].getName(), 0, profiles[x].getName().length())) 
				{
					argsList.add(profiles[x].getName());
				}
			}
		}

		return argsList;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{
		if (args.length >= 2 && index == 2)
			return true;
		
		return false;
	}
}
