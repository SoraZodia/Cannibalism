package com.sorazodia.cannibalism.main;

import net.minecraftforge.common.MinecraftForge;

import com.sorazodia.cannibalism.config.ConfigHandler;
import com.sorazodia.cannibalism.events.ConfigEvent;
import com.sorazodia.cannibalism.events.DeathEvent;
import com.sorazodia.cannibalism.tab.CannibalismTab;
import com.sorazodia.registryhelper.GameRegistryItems;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid=Cannibalism.MODID, version=Cannibalism.VERSION, name = Cannibalism.NAME, guiFactory = "com.sorazodia.cannibalism.config.ConfigGUIFactory")
public class Cannibalism {

//@MOD variables
public static final String MODID = "cannibalism";
public static final String VERSION = "1.0.1";
public static final String NAME = "Cannibalism";

@Mod.Instance(MODID)
public static Cannibalism cannibalism;

public static ConfigHandler config;
public static CannibalismTab cannibalismTab = new CannibalismTab();

@EventHandler
public void preInit(FMLPreInitializationEvent preEvent){
	
	FMLLog.info("[Cannibalism] Initializating Mod");
	FMLLog.info("[Cannibalism] Adding Items and Syncing Config");
	config = new ConfigHandler(preEvent);
	GameRegistryItems.init(MODID, cannibalismTab);	
	ItemRegistry.init();
	config.syncConfig();
     }//PreInit End, because {} are so hard to differentiate o_O

@EventHandler
public void preInit(FMLInitializationEvent event){
	
	FMLLog.info("[Cannibalism] Initializating Recipes and Events");
	RecipesRegistry.init();
	CookingRegistry.init();
	MinecraftForge.EVENT_BUS.register(new DeathEvent());
	FMLCommonHandler.instance().bus().register(new ConfigEvent());
	FMLLog.info("[Cannibalism] Completed");
	
     }//Init End, because {} are so hard to differentiate o_O

}