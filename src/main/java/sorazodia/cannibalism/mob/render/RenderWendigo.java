package sorazodia.cannibalism.mob.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sorazodia.cannibalism.main.Cannibalism;

public class RenderWendigo extends RenderLiving
{

	private ResourceLocation wendigoTexture = new ResourceLocation(Cannibalism.MODID+":/textures/entity/wendigo.png");
	
	public RenderWendigo(ModelBase mobel, float shadowSize)
	{
		super(mobel, shadowSize*5);
	}
	
	@Override
	public void preRenderCallback(EntityLivingBase living, float scale)
    {
		GL11.glScalef(3F, 5F, 3F);
    }
	
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return wendigoTexture;
	}

}
