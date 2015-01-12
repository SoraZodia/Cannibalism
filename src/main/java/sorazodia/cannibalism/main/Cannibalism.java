package sorazodia.cannibalism.main;

import net.minecraftforge.common.MinecraftForge;
import sorazodia.cannibalism.apitest.EntityRegistation;
import sorazodia.cannibalism.apitest.TestEntity;
import sorazodia.cannibalism.config.ConfigHandler;
import sorazodia.cannibalism.events.ConfigEvent;
import sorazodia.cannibalism.events.DeathEvent;
import sorazodia.cannibalism.events.EntityEvents;
import sorazodia.cannibalism.tab.CannibalismTab;
import sorazodia.registryhelper.GameItemsRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid=Cannibalism.MODID, version=Cannibalism.VERSION, name = Cannibalism.NAME, guiFactory = "sorazodia.cannibalism.config.ConfigGUIFactory")
public class Cannibalism {

	//@MOD variables
	public static final String MODID = "cannibalism";
	public static final String VERSION = "1.1.0";
	public static final String NAME = "Cannibalism";

	@Mod.Instance(MODID)
	public static Cannibalism cannibalism;

	public static ConfigHandler config;
	public static CannibalismTab cannibalismTab = new CannibalismTab();

	@EventHandler
	public void preInit(FMLPreInitializationEvent preEvent)
	{

		FMLLog.info("[Cannibalism] Initializating Mod");
		FMLLog.info("[Cannibalism] Adding Items and Syncing Config");
		config = new ConfigHandler(preEvent);
		GameItemsRegistry.init(MODID, cannibalismTab);	
		//EntityRegistation.registerEntity(TestEntity.class, "Test", cannibalism);
		ItemRegistry.init();
		config.syncConfig();
	}//PreInit End, because {} are so hard to differentiate o_O

	@EventHandler
	public void init(FMLInitializationEvent event)
	{

		FMLLog.info("[Cannibalism] Initializating Recipes and Events");
		RecipesRegistry.init();
		CookingRegistry.init();
		MinecraftForge.EVENT_BUS.register(new DeathEvent());
		MinecraftForge.EVENT_BUS.register(new EntityEvents());
		FMLCommonHandler.instance().bus().register(new ConfigEvent());
		FMLLog.info("[Cannibalism] Completed");

	}//Init End, because {} are so hard to differentiate o_O

}