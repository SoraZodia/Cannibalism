package sorazodia.api.registryhelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class ModelRegistry
{
	private static ItemModelMesher model = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
	
	public static void addTexture(Item item, String modID, String name) 
	{
		model.register(item, 0, new ModelResourceLocation(modID +":"+ name, "inventory"));
	}
}
