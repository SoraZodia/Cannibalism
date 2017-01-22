package sorazodia.api.nbt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.UUID;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

/**
 * Read and write the Database object to file
 */
public class IO
{
	
	private String dirPath= "";
	private String filePath = "";
	
	/**
	 * Creates a IO object that has a preset path for the data file, so that
	 * the non-static variation of {@link #read(String)} and {@link #write(String, HashMap)}
	 * can be used.
	 * 
	 * @param FMLServerStartingEvent
	 * @param file name, please use your modID to avoid conflicts. The resulting file will be a .dat file.
	 */
	public IO(FMLServerStartingEvent event, String fileName)
	{
		this.dirPath = ".\\saves\\" + event.getServer().getFolderName() + "\\SavedPlayerData";
		this.filePath = dirPath + "\\" + fileName + ".dat";
	}

	/**
	 * Convert a Database file into a object to be used
	 */
	@SuppressWarnings("unchecked")
	public Database read()
	{
		
		File dir = new File(dirPath);
		File file = new File(filePath);
		Database database = new Database();

		if (!dir.exists())
		{
			dir.mkdirs();
		}
		
		try
		{
			if (file.exists())
			{
				ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file));

				HashMap<UUID, PlayerInfo> data = (HashMap<UUID, PlayerInfo>) reader.readObject();
				reader.close();

				database = new Database(data);
			}
		}
		catch (ClassNotFoundException | IOException e)
		{
			e.printStackTrace();
		}
		
		return database;
		
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
	
	/**
	 * Convert the Database into an external file for later use
	 * 
	 * @param The HashMap stored in the Database object
	 */
	public void write(HashMap<UUID, PlayerInfo> data)
	{
		ObjectOutputStream writer;
		try
		{
			writer = new ObjectOutputStream(new FileOutputStream(filePath));
			writer.writeObject(data);
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
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

}
