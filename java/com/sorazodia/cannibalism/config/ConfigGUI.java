package com.sorazodia.cannibalism.config;

import com.sorazodia.cannibalism.main.Cannibalism;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.config.GuiConfig;

public class ConfigGUI extends GuiConfig{
	private static final String title = "Cannibalism Config";
	public ConfigGUI(GuiScreen parent) {
		super(parent,
				new ConfigElement(
						ConfigHandler.configFile.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
						Cannibalism.MODID, false, false, title);
	}
}
