package sorazodia.cannibalism.managers;

import java.util.function.BooleanSupplier;

import com.google.gson.JsonObject;

import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;
import sorazodia.cannibalism.config.ConfigHandler;
import sorazodia.cannibalism.items.KnifeType;
import sorazodia.cannibalism.main.Cannibalism;

public class RecipeManager implements IConditionFactory
{
	private final String ID_KEY = "id";
	private final String MOD_KEY = "modid";
	
	@Override
	public BooleanSupplier parse(JsonContext context, JsonObject json)
	{	
		if ((json.has(ID_KEY) && json.has(MOD_KEY)) && json.get(MOD_KEY).getAsString().equals(Cannibalism.MODID)) {
			return () -> ConfigHandler.getEnabledKnifeList().contains(KnifeType.valueOf(json.get(ID_KEY).getAsString().toUpperCase()));
		}
		
		return () -> true;
	}

}
