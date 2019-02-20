package sorazodia.cannibalism.main;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.FMLCommonHandler;
import sorazodia.cannibalism.items.ItemDevKnife;
import sorazodia.cannibalism.items.ItemFlesh;
import sorazodia.cannibalism.items.ItemHeirloom;
import sorazodia.cannibalism.items.ItemKnife;

public class ItemRegistry
{
	private static final ToolMaterial FLINT = EnumHelper.addToolMaterial("FLINT", 1, 100, 3.0F, 2.5F, 5);

	// Knife variables + names
	public static ItemKnife woodenKnife = (ItemKnife) new ItemKnife("woodenknife", Item.ToolMaterial.WOOD);

	public static ItemKnife flintKnife = (ItemKnife) new ItemKnife("flintknife", FLINT);

	public static ItemKnife stoneKnife = (ItemKnife) new ItemKnife("stoneknife", Item.ToolMaterial.STONE);

	public static ItemKnife goldKnife = (ItemKnife) new ItemKnife("goldknife", Item.ToolMaterial.GOLD);

	public static ItemKnife ironKnife = (ItemKnife) new ItemKnife("ironknife", Item.ToolMaterial.IRON);

	public static ItemKnife diamondKnife = (ItemKnife) new ItemKnife("diamondknife", Item.ToolMaterial.DIAMOND);

	public static ItemKnife devKnife = (ItemDevKnife) new ItemDevKnife();

	// Flesh variables + names
	public static ItemFlesh playerFlesh = (ItemFlesh) new ItemFlesh("playerflesh", 4, 0.3F);

	public static ItemFlesh villagerFlesh = (ItemFlesh) new ItemFlesh("villagerflesh", 4, 0.3F);

	public static ItemFlesh witchFlesh = (ItemFlesh) new ItemFlesh("witchflesh", 4, 0.3F);

	public static ItemFlesh playerFleshCooked = (ItemFlesh) new ItemFlesh("playerfleshcooked", 8, 0.8F);

	public static ItemFlesh villagerFleshCooked = (ItemFlesh) new ItemFlesh("villagerfleshcooked", 8, 0.8F);
	
	public static ItemHeirloom heart = new ItemHeirloom("wendigoheart");
	
	public static ItemHeirloom groundedheart = new ItemHeirloom("groundedplayerheart");

	public static void addAllTexture() {
		ItemRegistry.addTexture(woodenKnife);
		ItemRegistry.addTexture(flintKnife);
		ItemRegistry.addTexture(stoneKnife);
		ItemRegistry.addTexture(goldKnife);
		ItemRegistry.addTexture(ironKnife);
		ItemRegistry.addTexture(diamondKnife);
		ItemRegistry.addTexture(devKnife);
		ItemRegistry.addTexture(playerFlesh);
		ItemRegistry.addTexture(villagerFlesh);
		ItemRegistry.addTexture(witchFlesh);
		ItemRegistry.addTexture(playerFleshCooked);
		ItemRegistry.addTexture(villagerFleshCooked);
		ItemRegistry.addTexture(heart);
		ItemRegistry.addTexture(groundedheart);
	}
	
	public static void addTexture(Item item) {
		 ItemRegistry.addTexture(item, item.getRegistryName().toString());
	}
	
	public static void addTexture(Item item, String name) {
		 if(FMLCommonHandler.instance().getSide().isClient())
	            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(name, "inventory"));
	}
}
