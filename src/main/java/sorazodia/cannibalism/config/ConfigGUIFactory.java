package sorazodia.cannibalism.config;

import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;

public class ConfigGUIFactory implements IModGuiFactory
{
	@Override
	public void initialize(Minecraft minecraftInstance)
	{

	}

	@Override
	public GuiScreen createConfigGui(GuiScreen screen)
	{
		return new ConfigGUI(screen);
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
	{
		return null;
	}

	@Override
	public boolean hasConfigGui()
	{
		return true;
	}
}
