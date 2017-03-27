package sorazodia.cannibalism.main;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import sorazodia.cannibalism.mob.EntityWendigo;

public class EntitysRegistry
{

	private static int modID = 0;
	private final static String WENDIGO_NAME = "wendigo";

	public static void init()
	{
		registerEntity(EntityWendigo.class, WENDIGO_NAME, 42);
	}

	private static void registerEntity(Class<? extends EntityLiving> entity, String name, int range)
	{
		EntityRegistry.registerModEntity(new ResourceLocation(Cannibalism.MODID + ":" + name), entity, name, modID++, Cannibalism.instance, range, 1, true);
	}

}
