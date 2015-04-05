import static java.lang.System.out;
import java.util.ArrayList;

public class Noble
{
	private String name;
	private float armyStrength;
	private ArrayList<Warrior> employee;
	private boolean dead;
	
	/**
	 * Noble object constructor
	 * @param name set name of the Noble
	 */
	public Noble (String name)
	{
		this.name = name;
		employee = new ArrayList<Warrior>();
	}
	
	/**
	 * Try to add warrior to his employee <br>
	 * Hire will failed if either noble or warrior was dead
	 * @param warrior the warrior to be hired
	 */
	public void hire (Warrior warrior)
	{
		if (dead)
		{
			out.println("Dead noble cannot hire anyone!");
			return;
		}
		else if (warrior.setEmployer(this))
		{
			employee.add(warrior);
			armyStrength += warrior.getStrength();
		}
	}

	/**
	 * Raise battle with other noble <br>
	 * Weaker noble will lose his life and strength of all warrior <br>
	 * Stronger noble will win and reduce the strength of his army
	 * @param noble the enemy noble to fight with
	 */
	public void battle (Noble noble)
	{
		out.println(name + " battles " + noble.getName());
		//if detect either of them is dead, then break the function properly
		if (dead && noble.isDead())
		{
			out.println("Oh, NO! They're both dead! Yuck!");
			return;
		}
		else if (dead)
		{
			out.println("He's dead, " + noble.getName());
			return;
		}

		else if (noble.isDead())
		{
			out.println("He's dead, " + name);
			return;
		}

		//if both of them alive, then judge the winner and make side effects
		else if (armyStrength == noble.getArmyStrength())
		{
			out.println("Mutual Annihilation: " + name + " and " + 
					noble.getName() + " die at each other's hands");
			this.loseBattle();
			noble.loseBattle();
		}
		else if (armyStrength > noble.getArmyStrength())
		{
			out.println(name + " defeats " + noble.getName());
			this.winBattle(noble);
		}

		else
		{
			out.println(noble.getName() + " defeats " + name);
			noble.winBattle(this);
		}
	}
	
	/**
	 * The side effects when a noble lose a battle <br>
	 * Will also be called by winBattle() <br>
	 * Only call directly when Mutual Annihilation <br>
	 */
	public void loseBattle ()
	{
		dead = true;
		for (Warrior warrior : employee)
		{
			warrior.setStrength(0);
		}
		this.updateStrength();
	}
	
	/**
	 *The side effects when a noble win a battle <br>
	 *Will also called loseBattle() method for loser
	 */
	public void winBattle (Noble noble)
	{
		float ratio = noble.getArmyStrength()/armyStrength;
		for (Warrior warrior : employee)
		{
			warrior.setStrength(warrior.getStrength()*(1-ratio));
		}
		noble.loseBattle();
		this.updateStrength();
	}
	
	/**
	 * overriding toString() method to print out Noble infor in good format
	 */
	public String toString()
	{
		StringBuilder string = new StringBuilder(name + " has an army of " + employee.size());
		for (Warrior warrior : employee)
			(string.append("\n\t")).append(warrior.toString());

		return string.toString();	
	}
	
	/**
	 * Side effects to noble when a warrior quit <br>
	 * Should not be called directly
	 */
	public void cancleWarrior(Warrior warrior)
	{
		employee.remove(warrior);
		this.updateStrength();
	}

	/**
	 * Get name of the noble
	 * @return String: name of the noble
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Get death information of noble
	 * @return true: noble is dead. false: noble is alive.
	 */
	public boolean isDead()
	{
		return dead;
	}
	
	/**
	 * Get total army strength of the noble
	 * @return float: value of army strength
	 */
	public float getArmyStrength()
	{
		return armyStrength;
	}

	/**
	 * Update the army strength of the noble <br>
	 * Already called in other method (winBattle, loseBattle) when neccessary
	 */
	public void updateStrength()
	{
		armyStrength = 0;
		for (Warrior warrior : employee)
		armyStrength += warrior.getStrength();
	}

}
