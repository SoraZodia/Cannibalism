package sorazodia.cannibalism.main;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import sorazodia.cannibalism.mob.EntityTurnedVillager;
import sorazodia.cannibalism.mob.EntityWendigo;

public class EntitysRegistry
{

	private static int modID = 0;

	public static void init()
	{
		registerEntity(EntityWendigo.class, "wendigo", 42);
		registerEntity(EntityTurnedVillager.class, "wendigo_villager", 15);
	}

	private static void registerEntity(Class<? extends EntityLiving> entity, String name, int range)
	{
		EntityRegistry.registerModEntity(new ResourceLocation(Cannibalism.MODID + ":" + name), entity, name, modID++, Cannibalism.instance, range, 1, true);
	}

}
