package sorazodia.api.json;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import sorazodia.cannibalism.main.Cannibalism;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
			Cannibalism.getLogger().debug("Reader successfully closed");
		} catch (IOException io)
		{
			Cannibalism.getLogger().error("Unable to close/use BufferedReader");
			io.printStackTrace();
		}
	}

	public int size()
	{
		return jArray.size();
	}

	public String getString(int index, String key)
	{
		JsonObject jObj = (JsonObject) jArray.get(index);
		return jObj.get(key).toString();
	}

}
