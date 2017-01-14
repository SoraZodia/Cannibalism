package sorazodia.api.nbt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.UUID;

/**
 * Read and write the Database object to file
 */
public class IO
{
	/**
	 * Convert the Database into an external file for later use
	 * 
	 * @param File path to write the file to
	 * @param The HashMap stored in the Database object
	 * @throws IOException
	 */
	public static void write(String path, HashMap<UUID, PlayerInfo> data) throws IOException
	{
		File file = new File(path);
		ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(file));

		writer.writeObject(data);
		writer.close();
	}

	/**
	 * Convert a Database file into a object to be used
	 * 
	 * @param File path in which the Database file can be found
	 * @return a Database object
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static Database read(String path) throws ClassNotFoundException, IOException
	{
		File file = new File(path);
		Database database;

		if (file.exists())
		{
			ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file));

			HashMap<UUID, PlayerInfo> data = (HashMap<UUID, PlayerInfo>) reader.readObject();
			reader.close();

			database = new Database(data);
		}
		else
		{
			database = new Database();
		}

		return database;
	}
}
