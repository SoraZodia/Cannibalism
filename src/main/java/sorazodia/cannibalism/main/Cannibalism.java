package sorazodia.cannibalism.main;

import java.io.IOException;

import net.minecraftforge.common.MinecraftForge;
import sorazodia.cannibalism.config.ConfigHandler;
import sorazodia.cannibalism.config.JSONConfig;
import sorazodia.cannibalism.main.proxy.ServerProxy;
import sorazodia.cannibalism.mechanic.events.ConfigEvent;
import sorazodia.cannibalism.mechanic.events.DeathEvent;
import sorazodia.cannibalism.mechanic.events.EntityNBTEvents;
import sorazodia.cannibalism.tab.CannibalismTab;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Cannibalism.MODID, version = Cannibalism.VERSION, name = Cannibalism.NAME, guiFactory = Cannibalism.GUI_FACTORY)
public class Cannibalism
{

	// @MOD variables
	public static final String MODID = "cannibalism";
	public static final String VERSION = "1.1.0";
	public static final String NAME = "Cannibalism";
	public static final String GUI_FACTORY = "sorazodia.cannibalism.config.ConfigGUIFactory";

	@Mod.Instance(MODID)
	public static Cannibalism cannibalism;

	@SidedProxy(clientSide = "sorazodia.cannibalism.main.proxy.ClientProxy", serverSide = "sorazodia.cannibalism.main.proxy.ServerProxy")
	public static ServerProxy common;

	public static ConfigHandler config;
	public static JSONConfig json;
	public static CannibalismTab cannibalismTab = new CannibalismTab();
	
	private static boolean error = false;

	@EventHandler
	public void preInit(FMLPreInitializationEvent preEvent)
	{
		FMLLog.info("[Cannibalism] Initializating Mod");
		FMLLog.info("[Cannibalism] Adding Items and Syncing Config");
		config = new ConfigHandler(preEvent);
		ItemRegistry.init();
		try
		{
			json = new JSONConfig(preEvent);
			json.initJSON();
		} catch (IOException e)
		{
			FMLLog.severe("[Cannibalism] **********************UNABLE TO START NOR CREATE JSON*******************************************");
			e.printStackTrace();
			error = true;
		}

		common.preInit();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{

		FMLLog.info("[Cannibalism] Initializating Recipes and Events");
		RecipesRegistry.init();
		CookingRegistry.init();
		EntitysRegistry.init();
		MinecraftForge.EVENT_BUS.register(new DeathEvent());
		MinecraftForge.EVENT_BUS.register(new EntityNBTEvents());
		FMLCommonHandler.instance().bus().register(new EntityNBTEvents());
		FMLCommonHandler.instance().bus().register(new ConfigEvent());
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent postEvent)
	{

		FMLLog.info("[Cannibalism] Reading JSON");
		
		if(error == true)
			error();
		else
			tryRead();

		FMLLog.info("[Cannibalism] Mod Locked and Loaded");
	}
	
	private void tryRead()
	{
		try
		{
			json.read();
		} 
		catch (IOException io)
		{
			error(io);
		}
	}
	
	private void error(Exception e)
	{
		FMLLog.severe("[Cannibalism] **********************UNABLE TO READ JSON, PLAN B GOOOOOOO*******************************************");
		e.printStackTrace();
		FMLLog.severe("[Cannibalism] Defaulting to backup");
		json.codeRed();
	}
	
	private void error()
	{
		FMLLog.severe("[Cannibalism] **********************UNABLE TO FIND JSON, PLAN B GOOOOOOO*******************************************");
		FMLLog.severe("[Cannibalism] Defaulting to backup");
		json.codeRed();
	}

}