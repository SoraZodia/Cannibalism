package sorazodia.cannibalism.config;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import sorazodia.cannibalism.main.Cannibalism;

public class ConfigGUI extends GuiConfig
{
	private static final String TITLE = "Cannibalism Config";

	public ConfigGUI(GuiScreen parent)
	{
		super(parent, new ConfigElement(ConfigHandler.configFile.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), Cannibalism.MODID, false, false, TITLE);
	}
}
