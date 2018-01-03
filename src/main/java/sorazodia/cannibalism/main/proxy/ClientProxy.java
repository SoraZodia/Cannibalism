package sorazodia.cannibalism.main.proxy;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import sorazodia.cannibalism.mob.EntityTurnedVillager;
import sorazodia.cannibalism.mob.EntityWendigo;
import sorazodia.cannibalism.mob.render.RenderTurnedVillager;
import sorazodia.cannibalism.mob.render.RenderWendigo;

public class ClientProxy extends ServerProxy
{

	@Override
	protected void registerRender()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityWendigo.class, RenderWendigo::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTurnedVillager.class, RenderTurnedVillager::new);
	}

}
