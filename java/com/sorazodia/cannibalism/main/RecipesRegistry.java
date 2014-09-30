package com.sorazodia.cannibalism.main;

import com.sorazodia.cannibalism.items.manager.ItemList;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipesRegistry {

	private static Item diamond = Items.diamond;
	private static Item gold = Items.gold_ingot;
	private static Item iron = Items.iron_ingot;
	private static Item flint = Items.flint;
	private static Item stick = Items.stick;
	private static Block cobblestone = Blocks.cobblestone;
	private static Block stone = Blocks.stone;
	private static Block woodPlank = Blocks.planks;
	
	private static ItemStack woodenKnife = new ItemStack(ItemList.woodenKnife);
	private static ItemStack flintKnife = new ItemStack(ItemList.flintKnife);
	private static ItemStack stoneKnife = new ItemStack(ItemList.stoneKnife);
	private static ItemStack goldKnife = new ItemStack(ItemList.goldKnife);
	private static ItemStack ironKnife = new ItemStack(ItemList.ironKnife);
	private static ItemStack diamondKnife = new ItemStack(ItemList.diamondKnife);
	
	public static void init(){
		GameRegistry.addShapedRecipe(woodenKnife, " x ","  y", 'x', woodPlank, 'y', stick);
		GameRegistry.addShapedRecipe(woodenKnife, "x  "," y ", 'x', woodPlank, 'y', stick);
		
		GameRegistry.addShapedRecipe(flintKnife, " x ","  y", 'x', flint, 'y', stick);
		GameRegistry.addShapedRecipe(flintKnife, "x  "," y ", 'x', flint, 'y', stick);
		
		GameRegistry.addShapedRecipe(stoneKnife, " x ","  y", 'x', cobblestone, 'y', stick);
		GameRegistry.addShapedRecipe(stoneKnife, "x  "," y ", 'x', cobblestone, 'y', stick);
		
		GameRegistry.addShapedRecipe(stoneKnife, " x ","  y", 'x', stone, 'y', stick);
		GameRegistry.addShapedRecipe(stoneKnife, "x  "," y ", 'x', stone, 'y', stick);
		
		GameRegistry.addShapedRecipe(goldKnife, " x ","  y", 'x', gold, 'y', stick);
		GameRegistry.addShapedRecipe(goldKnife, "x  "," y ", 'x', gold, 'y', stick);
		
		GameRegistry.addShapedRecipe(ironKnife, " x ","  y", 'x', iron, 'y', stick);
		GameRegistry.addShapedRecipe(ironKnife, "x  "," y ", 'x', iron, 'y', stick);
		
		GameRegistry.addShapedRecipe(diamondKnife, " x ","  y", 'x', diamond, 'y', stick);
		GameRegistry.addShapedRecipe(diamondKnife, "x  "," y ", 'x', diamond, 'y', stick);
	}
}
