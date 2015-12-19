package sorazodia.cannibalism.mob.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import sorazodia.cannibalism.main.Cannibalism;

public class RenderWendigo extends RenderLiving
{

	private ResourceLocation wendigoTexture = new ResourceLocation(Cannibalism.MODID + ":textures/entity/wendigo.png");

	public RenderWendigo(ModelBase mobel, float shadowSize)
	{
		super(mobel, shadowSize);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity)
	{
		return wendigoTexture;
	}

}
