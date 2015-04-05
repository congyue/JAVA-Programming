import static java.lang.System.out;

public class Warrior
{
	private String name;
	private float strength;
	private Noble employer;
	private boolean dead;
	
	/**
	 * Warrior class constructor
	 * @param name The name of the warrior
	 * @param strength The initial strength of the warrior
	 */
	public Warrior (String name, float strength)
	{
		this.name = name;
		this.setStrength (strength);
	}

	/**
	 * Set strength for a warrior
	 * @param strength strength value to be set 
	 */
	public void setStrength (float strength)
	{
		this.strength = strength;
		if (this.strength <= 0)
			dead = true;
	}

	/**
	 * Get hiring status of a warrior.
	 * @return true: already hired. false: still looking for job
	 */
	public boolean isHired ()
	{
		return employer == null? false : true;
	}

	/**
	 * Set employer for a warrior <br>
	 * Already called by hire() method of nobles
	 * @param noble the employer to be added
	 * @return true: set successful. false: fail to set employer
	 */
	public boolean setEmployer (Noble noble)
	{
		if (dead == false && employer == null)
		{
			employer = noble;
			return true;
		}

		return false;
	}

	/**
	 *quit from current employer<br>
	 *will also trigger cancleWarrior method of noble
	 */
	public void runaway()
	{
		if (!dead)
		{
			if (employer == null)
				out.println("Can't runaway before getting a job!");
			else
			{
				out.println("So long " + employer.getName() + 
						". I'm out'a here! -- " + name);
				employer.cancleWarrior(this);
				employer = null;
			}
		}
		else
			out.println("A dead man cannot quit!");
	}

	/**
	 * Get the name of the warrior
	 * @return String: name of warrior
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Get the strength of a warrior
	 * @return float: value of strength
	 */
	public float getStrength()
	{
		return strength;
	}

	/**
	 * Overriding the toString function in order to print out warrior object in a properate format
	 * @return String: the string ready to print
	 */
	public String toString()
	{
		return this.getName()+ ": " + this.getStrength();
	}
}
