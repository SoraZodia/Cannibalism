package sorazodia.cannibalism.main;

import java.io.IOException;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import sorazodia.cannibalism.config.ConfigHandler;
import sorazodia.cannibalism.config.JSONConfig;
import sorazodia.cannibalism.main.proxy.ServerProxy;
import sorazodia.cannibalism.mechanic.events.ConfigEvent;
import sorazodia.cannibalism.mechanic.events.DeathEvent;
import sorazodia.cannibalism.mechanic.events.EntityNBTEvents;
import sorazodia.cannibalism.tab.CannibalismTab;

import com.google.gson.JsonSyntaxException;

@Mod(modid = Cannibalism.MODID, version = Cannibalism.VERSION, name = Cannibalism.NAME, guiFactory = Cannibalism.GUI_FACTORY)
public class Cannibalism
{

	// @MOD variables
	public static final String MODID = "cannibalism";
	public static final String VERSION = "1.1.1";
	public static final String NAME = "Cannibalism";
	public static final String GUI_FACTORY = "sorazodia.cannibalism.config.ConfigGUIFactory";

	@Mod.Instance(MODID)
	public static Cannibalism instance;

	@SidedProxy(clientSide = "sorazodia.cannibalism.main.proxy.ClientProxy", serverSide = "sorazodia.cannibalism.main.proxy.ServerProxy")
	public static ServerProxy common;
	
	public static CannibalismTab cannibalismTab = new CannibalismTab();
	
	@SuppressWarnings("unused")
	private static ConfigHandler config;
	private static JSONConfig json;
	private static boolean error = false;

	@EventHandler
	public void preInit(FMLPreInitializationEvent preEvent)
	{
		FMLLog.info("[Cannibalism] Initializating Mod");
		FMLLog.info("[Cannibalism] Adding Items and Syncing Config");
		config = new ConfigHandler(preEvent);

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

		ItemRegistry.init();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{

		FMLLog.info("[Cannibalism] Initializating Recipes and Events");
		common.init();
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
		catch (JsonSyntaxException | NumberFormatException | ClassCastException | NullPointerException | IOException error)
		{
			error(error, json.getFileName());
		} 
		 catch (Exception wtfHappened)
		{
			error(wtfHappened, json.getFileName());
		}
	}
	
	private void error(Exception e, String fileName)
	{
		FMLLog.severe("[Cannibalism] **********************UNABLE TO READ %s, PLAN B GOOOOOOO*******************************************", fileName);
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
	
	public static JSONConfig getJson()
	{
		return json;
	}

}