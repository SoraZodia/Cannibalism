package sorazodia.cannibalism.mob.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import sorazodia.cannibalism.main.Cannibalism;
import sorazodia.cannibalism.mob.EntityTurnedVillager;
import sorazodia.cannibalism.mob.mobel.ModelTurnedVillager;

public class RenderTurnedVillager extends RenderLiving<EntityTurnedVillager>
{

	public RenderTurnedVillager(RenderManager manager)
	{
		super(manager, new ModelTurnedVillager(0.5F), 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityTurnedVillager entity)
	{
		return new ResourceLocation(Cannibalism.MODID, "textures/entity/villager_turned.png");
	}

}
