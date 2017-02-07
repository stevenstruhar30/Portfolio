
public class UnitTest {

	void myMain()
	{
		MMUHardware myMmu = new MMUHardware();
		for (int i=0;i<16384;i++)
			myMmu.writeMemory(i, i);
				
		for (int i=0;i<16384;i++)
			if (myMmu.readMemory(i) != i)
				System.out.println("Oh oh\n");
		
		if (myMmu.writeMemory(16385, 1))
			System.out.println("Shoot, that should not have worked");
		else
			System.out.println("Good, that write error was caught \n");
		
		System.out.println("Reference string "+myMmu.getReferenceString());
		System.out.println("Total page faults "+myMmu.getTotalPageFaults());
		
		System.out.println("Now going to try to crash \n");
		myMmu.readMemory(16385);
		
		
		System.out.println("This should never print");
	}
	public static void main(String args[]) {
	UnitTest ut = new UnitTest();
	ut.myMain();
	}
}
