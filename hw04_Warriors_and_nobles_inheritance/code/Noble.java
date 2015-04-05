import static java.lang.System.out;
import java.util.ArrayList;

/**class definition of Noble <br>
 * parent class of Lord and PersonWithStrength
 */
public abstract class Noble
{
	private String name;
	private float strength;
	private boolean dead;
	
	public abstract void loseBattle ();
	public abstract void winBattle (Noble noble);
	public abstract void fight();

	/**
	 * Noble object constructor
	 * @param name set name of the Noble
	 */
	public  Noble (String name)
	{
		this.name = name;	
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
	 * Polymophism method to raise battle with other noble <br>
	 * Weaker noble will lose his life or strength of all warrior depends on his type <br>
	 * Stronger noble will win and reduce the strength in his own way
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
		
		this.fight();
		noble.fight();

		//if both of them alive, then judge the winner and make side effects
		if (strength == noble.getStrength())
		{
			out.println("Mutual Annihilation: " + name + " and " + 
					noble.getName() + " die at each other's hands");
			this.loseBattle();
			noble.loseBattle();
		}
		else if (strength > noble.getStrength())
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
	 * Get death information of noble
	 * @return true: noble is dead. false: noble is alive.
	 */
	public boolean isDead()
	{
		return dead;
	}

	public void setStrength(float strength)
	{
		this.strength = strength;
		if (strength <= 0)
			dead = true;
	}

	public float getStrength()
	{
		return strength;
	}

		
}
