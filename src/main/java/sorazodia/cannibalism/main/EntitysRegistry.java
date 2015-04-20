package sorazodia.cannibalism.main;

import sorazodia.cannibalism.mob.EntityWendigo;
import net.minecraft.entity.EntityLiving;
import cpw.mods.fml.common.registry.EntityRegistry;

public class EntitysRegistry
{

	private static int modID = 0;
	private final static String WENDIGO_NAME = "wendigo";

	public static void init()
	{
		registerEntity(EntityWendigo.class, WENDIGO_NAME, 40);
	}

	private static void registerEntity(Class<? extends EntityLiving> entity, String name, int range)
	{
		EntityRegistry.registerModEntity(entity, name, modID++, Cannibalism.cannibalism, range, 1, true);
	}

}
