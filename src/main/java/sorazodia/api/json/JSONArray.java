package sorazodia.api.json;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import sorazodia.cannibalism.main.Cannibalism;

public class JSONArray
{
	private BufferedReader reader;
	private JsonParser parser = new JsonParser();
	private JsonArray jArray;

	public JSONArray(String path) throws FileNotFoundException
	{
		reader = new BufferedReader(new FileReader(path));
		jArray = (JsonArray) parser.parse(reader);
		try
		{
			reader.close();
			Cannibalism.getLogger().debug("[Cannibalism] Reader successfully closed");
		}
		catch (IOException io)
		{
			Cannibalism.getLogger().error("[Cannibalism] Unable to close/use BufferedReader");
			io.printStackTrace();
		}
	}

	public int size()
	{
		return jArray.size();
	}

	public String getString(int index, String key)
	{
		Optional<JsonElement> element = Optional.ofNullable(((JsonObject) jArray.get(index)).get(key));
		return element.isPresent() ? element.get().toString() : "";
	}

}
