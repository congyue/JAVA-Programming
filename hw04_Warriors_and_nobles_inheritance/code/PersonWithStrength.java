import static java.lang.System.out;

public class PersonWithStrength extends Noble
{
	/**
	 * Constructor of PersonWithStrength
	 */
	public PersonWithStrength (String name, float strength)
	{
		super(name);
		this.setStrength(strength);
	}

	/**
	 * PersonWithStrength say nothing when fighting
	 */
	@Override
	public void fight(){}

	/**
	 * The side effects when a PersonWithStrength lose a battle <br>
	 * Will also be called by winBattle() <br>
	 * Only call directly when Mutual Annihilation <br>
	 */
	@Override
	public void loseBattle ()
	{
		this.setStrength(0);
	}

	/**
	 *The side effects when a Lord win a battle <br>
	 *Will also called loseBattle() method for loser
	 */
	@Override
	public void winBattle (Noble noble)
	{
		float currentStrength;
		currentStrength = this.getStrength();
		this.setStrength(currentStrength - noble.getStrength());
		noble.loseBattle();
	}

	/**
	 * overriding toString() method to print out PersonWithStrength info in good format
	 */
	@Override
	public String toString()
	{
		String string = this.getName() + ": " + this.getStrength();
		return string;	
	}





}
