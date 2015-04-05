
public class TestCar
{
	public static void main(String[] args) 
		throws CloneNotSupportedException
	{	
		//Engine list
		Engine excellentEngine = new Engine("V6", 300);
		Engine goodEngine = new Engine("V5", 280);
		Engine fairEngine = new Engine("V4", 268.5);
		Engine badEngine = new Engine("V3", 250);
	
		//Wheel List
		Wheel excellentWheel = new Wheel("Michelin", 381);
		Wheel goodWheel = new Wheel("Hankook", 350);
		Wheel fairWheel = new Wheel("Power King", 343);
		Wheel badWheel = new Wheel("Yokohama", 328.5);

		//Car List
		Car Lamborghini = new Car (excellentEngine, 4, excellentWheel);
		Car BMW = new Car (goodEngine, 4, goodWheel);
		Car secondBMW = BMW.clone();
		
		Car bus = new Car (fairEngine, 6, fairWheel);
		Car motor = new Car (badEngine, 2, badWheel);

		//Test toString() method
		System.out.println();
		System.out.println("Lamborghini: "+ Lamborghini);
		System.out.println();
		System.out.println("BMW: " + BMW);
		System.out.println();
		
		//Test equals() and hashCode() method
		System.out.println("Are Lamborghini and BMW same? "+ BMW.equals(Lamborghini));
		System.out.println("Their hashcodes are: Lamborghini = " + Lamborghini.hashCode() 
				+ " BMW = " + BMW.hashCode());
		System.out.println();
		System.out.println("secondBMW: "+ secondBMW);
		System.out.println();
		System.out.println("Are two BMWs the same? "+BMW.equals(secondBMW));
		System.out.println("Their hashcodes are: BMW = " + BMW.hashCode() 
				+ " secondBMW = " + secondBMW.hashCode());
		System.out.println();
		
		System.out.println("Upgrade BMW...");
		BMW.replaceWheel(3,excellentWheel);
		BMW.replaceWheel(4,excellentWheel);
		BMW.replaceEngine(excellentEngine);
		System.out.println("BMW: " + BMW);
		System.out.println();
		System.out.println("BMW Copy should stay as unchanged...");
		System.out.println("secondBMW: "+ secondBMW);
		System.out.println();

		System.out.println("Bus: "+ bus);
		System.out.println();
		System.out.println("Motor: "+ motor);
		System.out.println();

		//Test compareTo() method
		System.out.println("Lamborghini compare with BMW: " + Lamborghini.compareTo(BMW));
		System.out.println("motor compare with BMW: " + motor.compareTo(BMW));
		System.out.println("BMW compare with BMW: " + BMW.compareTo(BMW));
		System.out.println("secondBMW compare with BMW: " + secondBMW.compareTo(BMW));
		System.out.println("bus compare with motor: " + bus.compareTo(motor));
		System.out.println();

		//Test replaceWheel() and removeWheel()
		bus.removeWheel(6);
		bus.removeWheel(5);
		System.out.println("After remove bus wheel 5,6: " + bus);
		System.out.println("Hashcode is " + bus.hashCode());
		System.out.println();
		bus.replaceWheel(1,goodWheel);
		bus.replaceWheel(2,goodWheel);
		bus.replaceWheel(5,excellentWheel);
		bus.replaceWheel(6,excellentWheel);
		System.out.println("After replace bus wheel 1,2,5,6: " + bus);
		System.out.println("Hashcode is " + bus.hashCode());
		System.out.println();

		//Test replaceEngine() and removeEngine()
		motor.replaceEngine(goodEngine);
		System.out.println("After replace engine of motor: " + motor);
		System.out.println("Hashcode is " + motor.hashCode());
		System.out.println();
		motor.removeEngine();
		System.out.println("After remove engine of motor: " + motor);
		System.out.println("Hashcode is " + motor.hashCode());
		System.out.println();

		//Test equals() when remove everything of different cars
		motor.removeWheel(1);
		motor.removeWheel(2);
		System.out.println("Remove everything of motor: " + motor);
		System.out.println("Hashcode is " + motor.hashCode());
		System.out.println();
		Lamborghini.removeWheel(1);
		Lamborghini.removeWheel(2);
		Lamborghini.removeWheel(3);
		Lamborghini.removeWheel(4);
		Lamborghini.removeEngine();
		System.out.println("Remove everything of Lamborghini: " + Lamborghini);
		System.out.println("Hashcode is " + Lamborghini.hashCode());
		System.out.println();
		System.out.println("Is motor same with Lamborghini? "+motor.equals(Lamborghini));

	}
}
