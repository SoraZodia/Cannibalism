package sorazodia.cannibalism.main;

import static sorazodia.cannibalism.main.ItemRegistry.*;

import java.io.IOException;

import org.apache.logging.log4j.Logger;

import com.google.gson.JsonSyntaxException;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import sorazodia.api.nbt.Database;
import sorazodia.api.nbt.IO;
import sorazodia.cannibalism.config.ConfigHandler;
import sorazodia.cannibalism.config.JSONConfig;
import sorazodia.cannibalism.main.proxy.ServerProxy;
import sorazodia.cannibalism.mechanic.events.ConfigEvent;
import sorazodia.cannibalism.mechanic.events.DeathEvent;
import sorazodia.cannibalism.mechanic.events.EntityNBTEvents;
import sorazodia.cannibalism.mechanic.events.InteractionEvent;
import sorazodia.cannibalism.server.CommandWendigoLevel;
import sorazodia.cannibalism.tab.CannibalismTab;

@Mod(modid = Cannibalism.MODID, version = Cannibalism.VERSION, name = Cannibalism.NAME, guiFactory = Cannibalism.GUI_FACTORY)
public class Cannibalism
{

	public static final String MODID = "cannibalism";
	public static final String VERSION = "5.2.1";
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
	public static ResourceLocation wendigoLootTable;
	
	private static Logger log;

	private static Database data = new Database();
	private IO io;

	@EventHandler
	public void serverStart(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandWendigoLevel());
		io = new IO(event, "cannibalismData");
		log.info("[Cannibalism] Attempting to read playerdata...");
		data = io.read();
		log.info("[Cannibalism] Completed");
	}

	@EventHandler
	public void serverStop(FMLServerStoppingEvent event)
	{
		log.info("[Cannibalism] Writing playerdata to file...");
		io.write(data.getDatabase());
		log.info("[Cannibalism] Completed");

	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent preEvent)
	{
		log = preEvent.getModLog();

		log.info("[Cannibalism] Initializating Mod");
		log.info("[Cannibalism] Adding Items, events, and Syncing Config");

		json = new JSONConfig(preEvent);
		config = new ConfigHandler(preEvent, json);
		common.preInit();
		MinecraftForge.EVENT_BUS.register(new DeathEvent());
		MinecraftForge.EVENT_BUS.register(new EntityNBTEvents());
		MinecraftForge.EVENT_BUS.register(new ConfigEvent());
		MinecraftForge.EVENT_BUS.register(new InteractionEvent());
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void register(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(woodenKnife, flintKnife, stoneKnife, goldKnife, ironKnife, diamondKnife, devKnife, playerFlesh, villagerFlesh, witchFlesh, playerFleshCooked, villagerFleshCooked, heart , groundedheart);
		ItemRegistry.addAllTexture();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		log.info("[Cannibalism] Initializating Recipes");
		CookingRegistry.init();
		EntitysRegistry.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent postEvent)
	{
		log.info("[Cannibalism] Checking Entity Mappings...");
		this.initEntityMapping();
		wendigoLootTable = LootTableList.register(new ResourceLocation(MODID, "wendigo"));
		log.info("[Cannibalism] Mod Locked and Loaded");
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

	public static Database getDatabase()
	{
		return data;
	}

}