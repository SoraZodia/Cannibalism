package com.sorazodia.cannibalism.main;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.sorazodia.cannibalism.items.manager.ItemList;

import cpw.mods.fml.common.registry.GameRegistry;

public class RecipesRegistry {

	private static Item diamond = Items.diamond;
	private static Item gold = Items.gold_ingot;
	private static Item iron = Items.iron_ingot;
	private static Item flint = Items.flint;
	private static Block cobblestone = Blocks.cobblestone;
	private static Block stone = Blocks.stone;
	private static Block woodPlank = Blocks.planks;
	
	private static ItemStack woodenKnife = new ItemStack(ItemList.woodenKnife);
	private static ItemStack flintKnife = new ItemStack(ItemList.flintKnife);
	private static ItemStack stoneKnife = new ItemStack(ItemList.stoneKnife);
	private static ItemStack goldKnife = new ItemStack(ItemList.goldKnife);
	private static ItemStack ironKnife = new ItemStack(ItemList.ironKnife);
	private static ItemStack diamondKnife = new ItemStack(ItemList.diamondKnife);
	
	private static String stickVanilla  = "stickWood";
	private static String stickTConsturct = "rodWood";
	
	public static void init(){
		shapedOreSimplifer(woodenKnife, " x ","  y", 'x', woodPlank, 'y', stickVanilla);
		shapedOreSimplifer(woodenKnife, "x  "," y ", 'x', woodPlank, 'y', stickVanilla);
		
		shapedOreSimplifer(flintKnife, " x ","  y", 'x', flint, 'y', stickVanilla);
		shapedOreSimplifer(flintKnife, "x  "," y ", 'x', flint, 'y', stickVanilla);
		
		shapedOreSimplifer(stoneKnife, " x ","  y", 'x', cobblestone, 'y', stickVanilla);
		shapedOreSimplifer(stoneKnife, "x  "," y ", 'x', cobblestone, 'y', stickVanilla);
		
		shapedOreSimplifer(stoneKnife, " x ","  y", 'x', stone, 'y', stickVanilla);
		shapedOreSimplifer(stoneKnife, "x  "," y ", 'x', stone, 'y', stickVanilla);
		
		shapedOreSimplifer(goldKnife, " x ","  y", 'x', gold, 'y', stickVanilla);
		shapedOreSimplifer(goldKnife, "x  "," y ", 'x', gold, 'y', stickVanilla);
		
		shapedOreSimplifer(ironKnife, " x ","  y", 'x', iron, 'y', stickVanilla);
		shapedOreSimplifer(ironKnife, "x  "," y ", 'x', iron, 'y', stickVanilla);
		
		shapedOreSimplifer(diamondKnife, " x ","  y", 'x', diamond, 'y', stickVanilla);
		shapedOreSimplifer(diamondKnife, "x  "," y ", 'x', diamond, 'y', stickVanilla);
	}
	
	private static void shapedOreSimplifer(ItemStack output, Object... input){
		GameRegistry.addRecipe(new ShapedOreRecipe(output, input));
	}
}
