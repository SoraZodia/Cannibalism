package sorazodia.api.json;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JSONWriter
{
	private BufferedWriter writer;
	private StringBuilder fuser = new StringBuilder();
	private final String quote = "\"";
	private final String colon = ":";
	private final String comma = ",";
	private final String breakL = "\n";

	public JSONWriter(String path) throws IOException
	{
		writer = new BufferedWriter(new FileWriter(new File(path)));
	}

	public void writeArray(String key, String[] value)
	{
		fuser.append(quote).append(key).append(quote).append(colon).append(quote);
		for (String s : value)
		{
			fuser.append(s).append(comma);
		}
		fuser.insert(fuser.lastIndexOf(comma), quote);
		fuser.append(breakL);
	}

	public void writeObject(String key, String value)
	{
		fuser.append(quote).append(key).append(quote).append(colon).append(quote).append(value).append(quote).append(comma).append(breakL);
	}

	public void writeArrayStart()
	{
		fuser.append("{").append(breakL);
	}

	public void writeArrayEnd()
	{
		fuser.deleteCharAt(fuser.lastIndexOf(comma)).append("},").append(breakL);
	}

	public void writeStart() throws IOException
	{
		fuser.append("[").append(breakL);
	}

	public void write() throws IOException
	{
		fuser.deleteCharAt(fuser.lastIndexOf(comma));
		fuser.append("]").append(breakL);
		writer.write(fuser.toString());
		fuser.delete(0, fuser.length());
		writer.close();
	}

	public void append() throws IOException
	{
		fuser.deleteCharAt(fuser.lastIndexOf(comma));
		fuser.append("]").append(breakL);
		writer.append(fuser.toString());
		fuser.delete(0, fuser.length());
		writer.close();
	}
}