package sorazodia.cannibalism.main;

import net.minecraftforge.common.MinecraftForge;
import sorazodia.api.potionregistry.PotionEventHandler;
import sorazodia.api.potionregistry.PseudoPotion;
import sorazodia.api.potionregistry.TestPotion;
import sorazodia.cannibalism.config.ConfigHandler;
import sorazodia.cannibalism.mechanic.events.ConfigEvent;
import sorazodia.cannibalism.mechanic.events.DeathEvent;
import sorazodia.cannibalism.mechanic.events.EntityEvents;
import sorazodia.cannibalism.tab.CannibalismTab;
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
		ItemRegistry.init();
		MinecraftForge.EVENT_BUS.register(new PotionEventHandler());
		PseudoPotion.registerPotion(MODID, new TestPotion());
	}

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

	}

}