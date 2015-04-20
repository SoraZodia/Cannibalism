package sorazodia.cannibalism.main.proxy;

import sorazodia.cannibalism.mob.EntityWendigo;
import sorazodia.cannibalism.mob.mobel.ModelWendigo;
import sorazodia.cannibalism.mob.render.RenderWendigo;
import cpw.mods.fml.client.registry.RenderingRegistry;

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
		RenderingRegistry.registerEntityRenderingHandler(EntityWendigo.class, new RenderWendigo(new ModelWendigo(), 1.0F));
	}

}
