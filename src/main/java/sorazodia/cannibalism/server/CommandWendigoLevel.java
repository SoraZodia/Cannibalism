package sorazodia.cannibalism.server;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;
import sorazodia.cannibalism.main.Cannibalism;
import sorazodia.cannibalism.mechanic.nbt.CannibalismNBT;

import com.mojang.authlib.GameProfile;

public class CommandWendigoLevel implements ICommand
{
	private final String commandSet = "set";
	private final String commandStat = "stat";

	@Override
	public String getCommandName()
	{
		return Cannibalism.MODID;
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		sender.addChatMessage(new ChatComponentTranslation("command.set"));
		return StatCollector.translateToLocalFormatted("command.stat");
	}

	@Override
	public List<String> getCommandAliases()
	{
		ArrayList<String> aliases = new ArrayList<>();
		
		aliases.add(Cannibalism.MODID);
		aliases.add(commandSet);
		aliases.add(commandStat);	
		return aliases;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args)
	{
		if (args.length >= 2)
		{
			EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(args[1]);

			if (player == null)
			{
				sender.addChatMessage(new ChatComponentTranslation("command.error.player"));
				return;
			}

			CannibalismNBT nbt = CannibalismNBT.getNBT(player);

			switch (args[0])
			{
			case commandSet:
				if (isFloat(args[2]))
				{
					nbt.setWendigoValue(Float.parseFloat(args[2]));
					sender.addChatMessage(new ChatComponentTranslation("command.wendigolevelchange"));
				}
				else
				{
					sender.addChatMessage(new ChatComponentTranslation("command.error.number"));
				}
				break;
			case commandStat:
				sender.addChatMessage(new ChatComponentTranslation("command.wendigostat", nbt.getWendigoValue()));
				break;
			}
		}
	}

	//http://stackoverflow.com/questions/237159/whats-the-best-way-to-check-to-see-if-a-string-represents-an-integer-in-java
	public static boolean isFloat(String arg)
	{
		if (arg == null)
			return false;

		int length = arg.length();

		if (length == 0)
			return false;

		int x = 0;

		if (arg.charAt(0) == '-')
		{
			if (length == 1)
				return false;
			x = 1;
		}

		for (; x < length; x++)
		{
			char c = arg.charAt(x);
			if ((c <= '/' || c >= ':') && c != '.')
				return false;
		}

		return true;
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender)
	{
		return sender.canCommandSenderUseCommand(2, Cannibalism.MODID);
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos blockpos)
	{
	    ArrayList<String> argsList = new ArrayList<>();

		if (args.length >= 1)
		{
			String lastLetter = args[args.length - 1];
			GameProfile[] profiles = MinecraftServer.getServer().getGameProfiles();

			if (args.length == 1)
			{
				argsList.add(commandStat);
				argsList.add(commandSet);
			}

			if (args.length > 1)
			{
				for (int x = 0; x < profiles.length; x++)
				{
					if (lastLetter.regionMatches(true, 0, profiles[x].getName(), 0, profiles[x].getName().length()))
					{
						argsList.add(profiles[x].getName());
					}
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

	@Override
	public int compareTo(ICommand command)
	{
		return Cannibalism.MODID.compareTo(((ICommand) command).getCommandName());
	}

}