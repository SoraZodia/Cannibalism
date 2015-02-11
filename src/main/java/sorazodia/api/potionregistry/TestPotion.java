package sorazodia.api.potionregistry;

public class TestPotion extends Potion
{
	int spam = 0;

	@Override
	public String getName()
	{
		return "test";
	}

	@Override
	public void effect(int powerLevel)
	{		
		System.out.println("Spam yo" + spam++);
	}

}
