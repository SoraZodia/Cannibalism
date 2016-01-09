package sorazodia.cannibalism.main.proxy;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import sorazodia.cannibalism.mob.EntityWendigo;
import sorazodia.cannibalism.mob.mobel.ModelWendigo;
import sorazodia.cannibalism.mob.render.RenderWendigo;

public class ClientProxy extends ServerProxy
{

	@Override
	public void preInit()
	{
		registerRender();
	}

	@Override
	protected void registerRender()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityWendigo.class, new RenderWendigo(Minecraft.getMinecraft().getRenderManager(), new ModelWendigo(), 1.0F));
	}

}
