package com.sorazodia.cannibalism.items.manager;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

import com.sorazodia.cannibalism.items.ItemFoodMeat;
import com.sorazodia.cannibalism.items.ItemKnife;

public class ItemList {
	
	private static final ToolMaterial FLINT = new EnumHelper().addToolMaterial("FLINT", 1, 100, 3.0F, 2.5F, 5);
	
	//Knife variables
	public static ItemKnife woodenKnife = (ItemKnife) new ItemKnife(Item.ToolMaterial.WOOD);
	public static ItemKnife flintKnife = (ItemKnife) new ItemKnife(FLINT);
	public static ItemKnife stoneKnife = (ItemKnife) new ItemKnife(Item.ToolMaterial.STONE);
	public static ItemKnife goldKnife =  (ItemKnife) new ItemKnife(Item.ToolMaterial.GOLD);	
	public static ItemKnife ironKnife =  (ItemKnife) new ItemKnife(Item.ToolMaterial.IRON);	
	public static ItemKnife diamondKnife = (ItemKnife) new ItemKnife(Item.ToolMaterial.EMERALD);
	
	//Flesh variables
	public static ItemFoodMeat playerFlesh = (ItemFoodMeat) new ItemFoodMeat(4, 1.0F);	
	public static ItemFoodMeat villagerFlesh = (ItemFoodMeat) new ItemFoodMeat(4, 1.0F);	
	public static ItemFoodMeat playerFleshCooked = (ItemFoodMeat) new ItemFoodMeat(8, 2.0F);	
	public static ItemFoodMeat villagerFleshCooked = (ItemFoodMeat) new ItemFoodMeat(8, 2.0F);
	

}
