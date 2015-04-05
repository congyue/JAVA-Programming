import static java.lang.System.out;

/**
 * class definition of Archer
 */
public class Archer extends Warrior
{
	/**
	 * Archer constructor
	 */
	public Archer(String name, float strength)
	{
		super(name, strength);
	}

	/**
	 * return the word to say when fight
	 * @return String of word
	 */
	public String hit()
	{
		return "TWANG. ";
	}
}
