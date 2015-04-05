import static java.lang.System.out;
import java.util.ArrayList;

/**class definition of Lord of the Land
 */
public class Lord extends Noble
{
	private float armyStrength;
	private ArrayList<Protector> employee;
	/**
	 * Constructor of Lord <br>
	 * Use ArrayList to store employees
	 */
	public Lord (String name)
	{
		super(name);
		employee = new ArrayList<Protector>();
	}

	/**
	 * Try to add warrior to his employee <br>
	 * Hire will failed if either noble or warrior was dead
	 * @param protector the protector to be hired
	 */
	public void hire (Protector protector)
	{
		if (this.isDead())
		{
			out.println(this.getName() + " could not hire " + protector.getName());
			return;
		}
		else if (protector.setEmployer(this))
		{
			employee.add(protector);
			this.updateStrength();
		}
	}

	/**
	 * Polymophism fight method <br>
	 * Each protector fight in his own way
	 */
	@Override
	public void fight()
	{
		for (Protector protector : employee)
		{
			protector.fight();
		}
	}

	/**
	 * The side effects when a Lord lose a battle <br>
	 * Will be called by winBattle() <br>
	 * Only call directly when Mutual Annihilation <br>
	 */
	@Override
	public void loseBattle ()
	{
		for (Protector protector : employee)
		{
			protector.setStrength(0);
		}	
		this.updateStrength();
	}

	/**
	 *The side effects when a Lord win a battle <br>
	 *Will also called polymophism loseBattle() method for loser Noble
	 */
	@Override
	public void winBattle (Noble noble)
	{
		float ratio = noble.getStrength()/armyStrength;
		for (Protector protector : employee)
		{
			protector.setStrength(protector.getStrength()*(1-ratio));
		}
		noble.loseBattle();
		this.updateStrength();
	}

	/**
	 * overriding toString() method to print out Noble info in good format
	 */
	@Override
	public String toString()
	{
		String string = this.getName() + " has an army of " + employee.size();
		for (Protector protector : employee)
			string += "\n\t" + protector.toString();

		return string;	
	}



	/**
	 * Get total army strength of the Lord
	 * @return float: value of army strength
	 */
	public float getArmyStrength()
	{
		return armyStrength;
	}

	/**
	 * Calculate the armyStrength of the Lord <br>
	 * And then update it to the strength in Noble class <br>
	 * Already called in other method (winBattle, loseBattle, hire, cancleProtector) when neccessary
	 */
	public void updateStrength()
	{
		armyStrength = 0;
		for (Protector protector : employee)
			armyStrength += protector.getStrength();
		setStrength(armyStrength);

	}

	/**
	 * Side effects to Lord when a protector quit <br>
	 * Should not be called directly
	 */
	public void cancleProtector(Protector protector)
	{
		employee.remove(protector);
		this.updateStrength();
	}



}
