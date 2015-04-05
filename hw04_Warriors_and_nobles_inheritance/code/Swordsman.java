/**
 * class definition of Swordsman
 */
public class Swordsman extends Warrior
{
	/**
	 * Swordsman constructor
	 */
	public Swordsman(String name, float strength)
	{
		super(name, strength);
	}

	/**
	 * return the word to say when fight
	 * @return String of word
	 */
	public String hit()
	{
		return "CLANG. ";
	}

}
