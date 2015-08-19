package sorazodia.cannibalism.main;

import java.io.IOException;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.Logger;

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
	public static final String VERSION = "1.2.0";
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

	private Logger log;

	@EventHandler
	public void preInit(FMLPreInitializationEvent preEvent)
	{
		log = preEvent.getModLog();
		log.info("Initializating Mod");
		log.info("Adding Items and Syncing Config");
		config = new ConfigHandler(preEvent);

		try
		{
			json = new JSONConfig(preEvent);
			json.initJSON();
		} catch (IOException e)
		{
			log.error("**********************UNABLE TO START NOR CREATE JSON*******************************************");
			e.printStackTrace();
			error = true;
		}

		ItemRegistry.init();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{

		log.info("Initializating Recipes, Textures, and Events");
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

		log.info("Reading JSON");
		
		if(error == true)
			error();
		else
			tryRead();

		log.info("Mod Locked and Loaded");
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
		log.error("**********************UNABLE TO READ %s, PLAN B GOOOOOOO*******************************************", fileName);
		e.printStackTrace();
		log.error("Defaulting to backup");
		json.codeRed();
	}
	
	private void error()
	{
		log.error("**********************UNABLE TO FIND JSON, PLAN B GOOOOOOO*******************************************");
		log.error("Defaulting to backup");
		json.codeRed();
	}
	
	public static JSONConfig getJson()
	{
		return json;
	}

}