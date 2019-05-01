package sorazodia.cannibalism.config;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiConfig;
import sorazodia.cannibalism.main.Cannibalism;

public class ConfigGUI extends GuiConfig
{
	private static final String TITLE = "Cannibalism Config";

	public ConfigGUI(GuiScreen parent)
	{
		super(parent, ConfigHandler.getConfigElements(), Cannibalism.MODID, false, false, TITLE);
	}
}
