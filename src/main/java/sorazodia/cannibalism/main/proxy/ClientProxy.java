package sorazodia.cannibalism.main.proxy;

import sorazodia.cannibalism.main.ImageRegistry;

public class ClientProxy extends ServerProxy
{

	@Override
	public void init()
	{
		ImageRegistry.init();
	}

}
