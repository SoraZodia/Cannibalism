package sorazodia.cannibalism.concurrency;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ThreadError extends Thread
{

	private Exception error;
	private String filePath;
	private BufferedWriter writer;
	
	public ThreadError(String filePath, Exception error)
	{
		filePath = this.filePath;
		error = this.error;
		try
		{
			writer = new BufferedWriter(new FileWriter(filePath));
		} catch (IOException io)
		{
			io.printStackTrace();
		}
		
	}
	
	@Override
	public void run()
	{
		try
		{
			writer.write(error.getStackTrace().toString());
			writer.close();
			Desktop.getDesktop().open(new File(filePath));
		} catch (IOException io)
		{
			io.printStackTrace();
		}
	}

}
