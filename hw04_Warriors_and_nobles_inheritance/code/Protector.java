import static java.lang.System.out;

public abstract class Protector
{
	private String name;
	private float strength;
	private boolean dead;
	private Lord employer;
	
	/**
	 * abstract method to describe how protector fight <br>
	 * Derived class will implement it in its own way
	 */
	public abstract void fight();

	/**
	 * Protector class constructor
	 * @param name The name of the protector
	 * @param strength The initial strength of the protector
	 */
	public Protector (String name, float strength)
	{
		this.name = name;
		this.setStrength (strength);
	}

	/**
	 * Set strength for a protector
	 * @param strength strength value to be set 
	 */
	public void setStrength (float strength)
	{
		this.strength = strength;
		if (this.strength <= 0)
			dead = true;
	}

	/**
	 * Get hiring status of a protector.
	 * @return true: already hired. false: still looking for job
	 */
	public boolean isHired ()
	{
		return employer == null? false : true;
	}

	/**
	 * Set employer for a protector <br>
	 * Already called by hire() method of nobles
	 * @param lord the employer to be added
	 * @return true: set successful. false: fail to set employer
	 */
	public boolean setEmployer (Lord lord)
	{
		if (dead == false && employer == null)
		{
			employer = lord;
			return true;
		}

		return false;
	}
	
	/**
	 * Get employer of a protector
	 * @return an Lord object which is the employer
	 */
	public Lord getEmployer()
	{
		return employer;
	}

	/**
	 *quit from current employer<br>
	 *will also trigger cancleWarrior method of noble
	 */
	public void runsaway()
	{
		if (!dead)
		{
			if (employer == null)
				out.println("Can't runaway before getting a job!");
			else
			{
				out.println("So long " + employer.getName() + 
						". I'm out'a here! -- " + name);
				employer.cancleProtector(this);
				employer = null;
			}
		}
		else
			out.println("A dead man cannot quit!");
	}


	/**
	 * Get the name of the protector
	 * @return String: name of warrior
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Get the strength of a protector
	 * @return float: value of strength
	 */
	public float getStrength()
	{
		return strength;
	}

	
	/**
	 * Overriding the toString function in order to print out protector object in a properate format
	 * @return String: the string ready to print
	 */
	public String toString()
	{
		return this.getName()+ ": " + this.getStrength();
	}

}
