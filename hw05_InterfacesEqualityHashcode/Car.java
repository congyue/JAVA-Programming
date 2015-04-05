import java.util.Arrays;
/**
 * class definition of Wheel
 */
public class Car implements Comparable<Car>, Cloneable
{
	private Wheel[] wheelList;
	private int wheelNum;
	private Engine engine;

	/**
	 * constructor of Car object
	 * @param engine The engine of the car
	 * @param wheelNum The wheel number of the car
	 * @param wheel The wheel of the car
	 */
	public Car (Engine engine, int wheelNum, Wheel wheel) throws CloneNotSupportedException
	{
		this.engine = engine;
		this.wheelNum = wheelNum;
		wheelList = new Wheel[wheelNum];
		for (int i=0; i<wheelNum; i++)
			wheelList[i] = wheel.clone();
	}

	/**
	 * Replace wheel in specific position
	 * @param position The position in which the wheel is replaced
	 * @param wheel The new wheel to be installed
	 */
	public void replaceWheel(int position, Wheel wheel)
	{
		if (position > wheelNum)
			System.out.println("Position larger than total wheel number!");	
		else if (position < 1)
			System.out.println("Invalid position number!");
		else
			wheelList[position - 1] = wheel;
	}

	/**
	 * Remove wheel in specific position
	 * @param position The position in which the wheel is removed 
	 */
	public void removeWheel(int position)
	{
		if (position > wheelNum)
			System.out.println("Position larger than total wheel number!");	
		else if (position < 1)
			System.out.println("Invalid position number!");
		else
			wheelList[position - 1] = null;
	}

	/**
	 * Replace engine of the car
	 * @param engine The new engine to be installed
	 */
	public void replaceEngine(Engine engine)
	{
		this.engine = engine;
	}

	/**
	 * Remove engine of the car
	 */
	public void removeEngine()
	{
		engine = null;
	}

	/**
	 * override toString() for printing out car info
	 * @return The string to display
	 */
	@Override
	public String toString()
	{
		String text = "This is a " + wheelList.length + " wheel car:\n";
		if (engine == null)
			text += "\tNo engine so far";
		else
			text += "\t" + engine.toString();
		for (int i=0; i<wheelList.length; i++)
			if (wheelList[i]!= null)
				text += "\n\twheel_"+(i+1)+": " + wheelList[i].toString();

		return text;
	}

	/**
	 * override clone() for copying car object
	 * @return The new wheel object with same property
	 */
	@Override
	public Car clone() throws CloneNotSupportedException
	{
		Car newCar = (Car) super.clone();
		newCar.engine = engine.clone();
		newCar.wheelList = wheelList.clone();
		for (int i=0; i<wheelNum; i++)
			newCar.wheelList[i] = wheelList[i].clone();
		return newCar;
	}

	/**
	 * override compareTo() for sorting car objects
	 * @return compare result in int: 
	 * 0 when equal; 
	 * positive when large than; 
	 * negative when less than
	 */
	@Override
	public int compareTo(Car car)
	{
		int result = this.engine.compareTo(car.engine);
		if (result != 0)
			return result;
		else
			if (wheelList.length != car.wheelList.length)
				return car.wheelList.length - wheelList.length;
			else return 0;
		
	}

	/**
	 * override equals() for comparing two cars (consider wheelList, engine and wheelNum)
	 * @param otherObject another object to be compared to
	 * @return compare result in boolean: true for equal, false for not.
	 */
	@Override
	public boolean equals(Object otherObject)
	{
		if (this == otherObject)
			return true;
		if (!(otherObject instanceof Car))
			return false;

		Car car = (Car) otherObject;
		return (Arrays.equals(wheelList,car.wheelList) && engine.equals(car.engine) && wheelNum == car.wheelNum);
	}

	/**
	 * override hashCode() in accordance with equals()
	 * @return hash code in int
	 */
	@Override
	public int hashCode()
	{
		 int hash = 1;
		 if (engine!=null)
			hash = hash*13 + engine.hashCode();
		 for (Wheel wheel : wheelList)
			 if(wheel!=null)
				hash = hash*3 + wheel.hashCode();
		 hash = hash*15 + wheelNum;
		 return hash;
	}
}


