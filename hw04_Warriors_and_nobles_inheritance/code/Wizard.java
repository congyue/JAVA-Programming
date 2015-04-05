import static java.lang.System.out;

/**
 * clss definition of Wizard
 */
public class Wizard extends Protector
{
	/**
	 * Wizard constructor
	 */
	public Wizard (String name, float strength)
	{
		super(name,strength);
	}

	/**
	 * Print out the word to say when fight
	 */	
	@Override
	public void fight()
	{
		out.println("POOF");
	}
}
