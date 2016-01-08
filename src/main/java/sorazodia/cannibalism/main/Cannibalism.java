package sorazodia.cannibalism.main;

import java.io.IOException;

import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.Logger;

import sorazodia.cannibalism.config.ConfigHandler;
import sorazodia.cannibalism.config.JSONConfig;
import sorazodia.cannibalism.main.proxy.ServerProxy;
import sorazodia.cannibalism.mechanic.events.ConfigEvent;
import sorazodia.cannibalism.mechanic.events.DeathEvent;
import sorazodia.cannibalism.mechanic.events.EntityNBTEvents;
import sorazodia.cannibalism.server.CommandWendigoLevel;
import sorazodia.cannibalism.tab.CannibalismTab;

import com.google.gson.JsonSyntaxException;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = Cannibalism.MODID, version = Cannibalism.VERSION, name = Cannibalism.NAME, guiFactory = Cannibalism.GUI_FACTORY)
public class Cannibalism
{

	// @MOD variables
	public static final String MODID = "cannibalism";
	public static final String VERSION = "1.2.2";
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

	private static Logger log;

	@EventHandler
	public void serverStart(FMLServerStartingEvent preServerEvent)
	{
		preServerEvent.registerServerCommand(new CommandWendigoLevel());
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent preEvent)
	{
		log = preEvent.getModLog();

		log.info("[Cannibalism] Initializating Mod");
		log.info("[Cannibalism] Adding Items and Syncing Config");
		config = new ConfigHandler(preEvent);

		try
		{
			json = new JSONConfig(preEvent);
			json.initJSON();
		}
		catch (IOException e)
		{
			log.error("**********************UNABLE TO START NOR CREATE JSON*******************************************");
			e.printStackTrace();
			error = true;
		}

		ItemRegistry.init();
		common.preInit();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		log.info("[Cannibalism] Initializating Recipes and Events");
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

		log.info("[Cannibalism] Reading JSON");

		if (error == true)
			error();
		else
			tryRead();

		log.info("[Cannibalism] Mod Locked and Loaded");
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
		log.error("[Cannibalism] **********************UNABLE TO READ %s, PLAN B GOOOOOOO*******************************************", fileName);
		e.printStackTrace();
		log.error("[Cannibalism] Defaulting to backup");
		json.codeRed();
	}

	private void error()
	{
		log.error("[Cannibalism] **********************UNABLE TO FIND JSON, PLAN B GOOOOOOO*******************************************");
		log.error("[Cannibalism] Defaulting to backup");
		json.codeRed();
	}

	public static JSONConfig getJson()
	{
		return json;
	}

	public static Logger getLogger()
	{
		return log;
	}

}