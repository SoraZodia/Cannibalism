package sorazodia.cannibalism.main;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import org.apache.logging.log4j.Logger;

import sorazodia.api.nbt.Database;
import sorazodia.api.nbt.IO;
import sorazodia.cannibalism.config.ConfigHandler;
import sorazodia.cannibalism.config.JSONConfig;
import sorazodia.cannibalism.main.proxy.ServerProxy;
import sorazodia.cannibalism.mechanic.events.ConfigEvent;
import sorazodia.cannibalism.mechanic.events.DeathEvent;
import sorazodia.cannibalism.tab.CannibalismTab;

import com.google.gson.JsonSyntaxException;

@Mod(modid = Cannibalism.MODID, version = Cannibalism.VERSION, name = Cannibalism.NAME, guiFactory = Cannibalism.GUI_FACTORY)
public class Cannibalism
{
	
	public static final String MODID = "cannibalism";
	public static final String VERSION = "3.0.0";
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

	private static Logger log;
	
	private static Database data;

	@EventHandler
	public void serverStart(FMLServerStartingEvent preServerEvent)
	{
		//preServerEvent.registerServerCommand(new CommandWendigoLevel());
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent preEvent)
	{
		log = preEvent.getModLog();

		log.info("[Cannibalism] Initializating Mod");
		log.info("[Cannibalism] Adding Items, events, and Syncing Config");

		json = new JSONConfig(preEvent);
	    config = new ConfigHandler(preEvent, json);
	    setupData("\\cannibalism data");
	    
	    UUID id = UUID.fromString("f10820b2-ad08-4b82-aca2-75b0445b6a1f");
	    UUID id2 = UUID.fromString("f10820b2-ad08-4b82-aca2-75b0445b6a1f");
	    
	    data.register(id);
	    data.register(id2);
		
	    System.out.println(data.get(id).get("test"));
	    System.out.println(data.get(id2).get("test"));
	    
	    data.get(id).add("test", "Success");
	    data.get(id2).add("test", 76);
	    
	    try
		{
			IO.write("\\cannibalism data\\cannibalismData.dat", data.getDatabase());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	    
		ItemRegistry.init();
		common.preInit();
		
		MinecraftForge.EVENT_BUS.register(new DeathEvent());
		//MinecraftForge.EVENT_BUS.register(new EntityNBTEvents());
		MinecraftForge.EVENT_BUS.register(new ConfigEvent());
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		log.info("[Cannibalism] Initializating Recipes");
		RecipesRegistry.init();
		CookingRegistry.init();
		EntitysRegistry.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent postEvent)
	{
		log.info("[Cannibalism] Checking Entity Mappings...");
		this.initEntityMapping();
		log.info("[Cannibalism] Mod Locked and Loaded");
	}
	
	private void setupData(String dirPath)

	{
		File dir = new File(dirPath);
		
		if (!dir.exists())
			dir.mkdir();
	
			try
			{
				data = IO.read(dirPath + "\\cannibalismData.dat");
			}
			catch (ClassNotFoundException | IOException e)
			{
				log.info("[Cannibalism] File not found! Defaulting to empty database");
				data = new Database();
				e.printStackTrace();
			}
		
	}

	private void initEntityMapping()
	{
		try
		{
			json.initEntityMappings();
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
		log.error("[Cannibalism] **********************UNABLE TO READ " + fileName + " PLAN B GOOOOOOO*******************************************");
		e.printStackTrace();
		log.error("[Cannibalism] Defaulting to backup");
		json.loadMapData();
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