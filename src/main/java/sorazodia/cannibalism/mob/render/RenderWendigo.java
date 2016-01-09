package sorazodia.cannibalism.mob.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import sorazodia.cannibalism.main.Cannibalism;

@SuppressWarnings("rawtypes")
public class RenderWendigo extends RenderLiving
{

	private ResourceLocation wendigoTexture = new ResourceLocation(Cannibalism.MODID + ":textures/entity/wendigo.png");

	public RenderWendigo(RenderManager manager, ModelBase mobel, float shadowSize)
	{
		super(manager, mobel, shadowSize);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity)
	{
		return wendigoTexture;
	}

}
