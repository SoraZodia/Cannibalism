package sorazodia.api.nbt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.UUID;

public class IO
{
	private ObjectOutputStream writer;
	private ObjectInputStream reader;
	
	public IO(String path) throws IOException
	{
		File file = new File(path);
		writer = new ObjectOutputStream(new FileOutputStream(file));
		reader = new ObjectInputStream(new FileInputStream(file));
	}
	
	public void write(HashMap<UUID, PlayerInfo> data) throws IOException
	{
		writer.writeObject(data);
		writer.close();
	}
	
	@SuppressWarnings("unchecked")
	public Database read() throws ClassNotFoundException, IOException
	{
		HashMap<UUID, PlayerInfo> data = (HashMap<UUID, PlayerInfo>) reader.readObject();
		reader.close();
		
		return new Database(data);
	}
}
