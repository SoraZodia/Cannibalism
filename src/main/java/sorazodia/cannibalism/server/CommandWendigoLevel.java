package sorazodia.cannibalism.server;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;
import sorazodia.cannibalism.main.Cannibalism;
import sorazodia.cannibalism.mechanic.nbt.CannibalismNBT;

import com.mojang.authlib.GameProfile;

public class CommandWendigoLevel implements ICommand
{
	private final String commandSet = "set";
	private final String commandStat = "stat";

	@Override
	public String getName()
	{
		return Cannibalism.MODID;
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		sender.sendMessage(new TextComponentTranslation("command.set"));
		return I18n.translateToLocalFormatted("command.stat");
	}

	@Override
	public List<String> getAliases()
	{
		ArrayList<String> aliases = new ArrayList<>();
		
		aliases.add(Cannibalism.MODID);
		aliases.add(commandSet);
		aliases.add(commandStat);	
		return aliases;
	}

	@Override
	public void execute(MinecraftServer server,ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length >= 2)
		{
			EntityPlayerMP player = server.getPlayerList().getPlayerByUsername(args[1]);

			if (player == null)
			{
				sender.sendMessage(new TextComponentTranslation("command.error.player"));
				return;
			}

			CannibalismNBT nbt = CannibalismNBT.getNBT(player);

			switch (args[0])
			{
			case commandSet:
				if (args.length == 3 && isFloat(args[2], true))
				{
					nbt.setWendigoValue(Float.parseFloat(args[2]));
					sender.sendMessage(new TextComponentTranslation("command.wendigolevelchange"));
				}
				else if (args.length == 4 && isFloat(args[2], true) && isFloat(args[3], false)) {
					nbt.setWendigoValue(Float.parseFloat(args[2]));
					nbt.setHeirloomCount(Integer.parseInt(args[3]));
					sender.sendMessage(new TextComponentTranslation("command.wendigolevelchange"));
				}
				else if (args.length == 5 && isFloat(args[2], true) && isFloat(args[3], false) && isFloat(args[4], true)) {
					nbt.setWendigoValue(Float.parseFloat(args[2]));
					nbt.setHeirloomCount(Integer.parseInt(args[3]));
					nbt.setSpawnChance(Float.parseFloat(args[4]));
					sender.sendMessage(new TextComponentTranslation("command.wendigolevelchange"));
				}
				else
				{
					sender.sendMessage(new TextComponentTranslation("command.error.number"));
				}
				break;
			case commandStat:
				sender.sendMessage(new TextComponentTranslation("command.wendigostat", nbt.getWendigoValue()));
				sender.sendMessage(new TextComponentTranslation("command.wendigospawnchance", nbt.getSpawnChance() * 100));
				sender.sendMessage(new TextComponentTranslation("command.wendigohasspawn", !nbt.wendigoSpawned()));
				sender.sendMessage(new TextComponentTranslation("command.wendigoheirlooms", nbt.getHeirloomCount() - 1));
				break;
			}
		}
	}

	//http://stackoverflow.com/questions/237159/whats-the-best-way-to-check-to-see-if-a-string-represents-an-integer-in-java
	public static boolean isFloat(String arg, boolean isFloat)
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
			if (!isFloat && c == '.')
				return false;
		}

		return true;
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return sender.canUseCommand(2, Cannibalism.MODID);
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos blockpos)
	{
	    ArrayList<String> argsList = new ArrayList<>();

		if (args.length >= 1)
		{
			String lastLetter = args[args.length - 1];
			GameProfile[] profiles = server.getOnlinePlayerProfiles();

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
		return Cannibalism.MODID.compareTo(((ICommand) command).getName());
	}

}