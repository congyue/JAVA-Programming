import static java.lang.System.out;

public abstract class Warrior extends Protector
{
	/**
	 * Polymophism method to return the word to say based on type of warrior
	 */
	public abstract String hit();

	public Warrior(String name, float strength)
	{
		super(name, strength);
	}

	/**
	 * Print out the warrior's slogan when fight
	 */
	@Override
	public void fight()
	{
		out.println(this.hit() + this.getName() +"says: Take that in the name of my lord, " 
				+ (this.getEmployer()).getName());

	}
}
