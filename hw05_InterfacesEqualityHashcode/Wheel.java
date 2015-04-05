/**
 * class definition of Wheel
 */
public class Wheel implements Comparable<Wheel>, Cloneable
{
	private String wheelBrand;
	private double wheelRadius;
	
	/**
	 * constructor of Wheel object
	 * @param wheelBrand The brand of the wheel
	 * @param wheelRadius The radius of the wheel in mm
	 */
	public Wheel(String wheelBrand, double wheelRadius)
	{
		this.wheelBrand = wheelBrand;
		this.wheelRadius = wheelRadius;
	}
	
	/**
	 * override toString() for printing out wheel info
	 * @return The string to display
	 */
	@Override
	public String toString()
	{
		return "Wheel Info: " + wheelBrand + " brand with radius of " + wheelRadius + " mm";
	}

	/**
	 * override clone() for copying wheel object
	 * @return The new wheel object with same property
	 */
	@Override
	public Wheel clone() throws CloneNotSupportedException
	{
		return (Wheel) super.clone();
	}

	/**
	 * override compareTo() for sorting wheel objects
	 * @return compare result in int: 
	 * 0 when equal; 
	 * positive when large than; 
	 * negative when less than
	 */
	@Override
	public int compareTo(Wheel wheel)
	{
		Float thisRadius = new Float(wheelRadius);
		Float thatRadius = new Float(wheel.wheelRadius);
		int result = thisRadius.compareTo(thatRadius);
		return (result == 0 ? wheelBrand.compareTo(wheel.wheelBrand) : result);
	}

	/**
	 * override equals() for comparing two wheels
	 * @param otherObject another object to be compared to
	 * @return compare result in boolean: true for equal, false for not.
	 */
	@Override
	public boolean equals(Object otherObject)
	{
		if (this == otherObject)
			return true;
		if (!(otherObject instanceof Wheel))
			return false;

		Wheel other = (Wheel) otherObject;
		return (wheelBrand == other.wheelBrand && wheelRadius == other.wheelRadius);
	}

	/**
	 * override hashCode() in accordance with equals()
	 * @return hash code in int
	 */
	@Override
	public int hashCode()
	{
		int hash = 1;
		hash = (int) (hash * 17 + wheelRadius);
		hash = hash * 31 + wheelBrand.hashCode();
		return hash;
	}

}
