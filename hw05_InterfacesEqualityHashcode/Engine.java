/**
 * class definition of Engine
 */
public class Engine implements Comparable<Engine>, Cloneable
{
	private String engineType;
	private double horsepower;

	/**
	 * constructor of Engine object
	 * @param type The type of the engine
	 * @param horsepower The performance of engine in horsepower
	 */
	public Engine (String type, double horsepower)
	{
		this.engineType = type;
		this.horsepower = horsepower;
	}

	/**
	 * override toString() for printing out engine info
	 * @return The string to display
	 */
	@Override
	public String toString()
	{
		return "Engine Info: " + engineType + " type with " + horsepower + " horsepower";
	}

	/**
	 * override clone() for copying engine object
	 * @return The new engine object with same property
	 */
	@Override
	public Engine clone() throws CloneNotSupportedException
	{
		return (Engine) super.clone();
	}

	/**
	 * override compareTo() for sorting engine objects
	 * @return compare result in int: 
	 * 0 when equal; 
	 * positive when large than; 
	 * negative when less than
	 */
	@Override
	public int compareTo(Engine engine)
	{
		Float thisHorsepower = new Float(horsepower);
		Float thatHorsepower = new Float(engine.horsepower);
		int result = thisHorsepower.compareTo(thatHorsepower);
		return (result == 0 ? engineType.compareTo(engine.engineType) : result);
	}

	/**
	 * override equals() for comparing two engines
	 * @param otherObject another object to be compared to
	 * @return compare result in boolean: true for equal, false for not.
	 */
	@Override
	public boolean equals(Object otherObject)
	{
		if (this == otherObject)
			return true;
		if (!(otherObject instanceof Engine))
			return false;

		Engine other = (Engine) otherObject;
		return (engineType == other.engineType && horsepower == other.horsepower);

	}

	/**
	 * override hashCode() in accordance with equals()
	 * @return hash code in int
	 */
	@Override
	public int hashCode()
	{
		int hash = 1;
		hash = (int) (hash * 17 + horsepower);
		hash = hash * 31 + engineType.hashCode();
		return hash;
	}

}
