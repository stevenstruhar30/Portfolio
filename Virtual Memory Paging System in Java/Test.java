import java.util.*;

/**
 * 
 * @author steve.struhar
 *
 */
public class Test
	{

		public static void main(String[] args)
			{
				@SuppressWarnings("unused")
				FileSystem fs = new FileSystem(16);
				Scanner sys = new Scanner(System.in);
				int alg = 0;
				while (alg != 1 && alg != 2)
					{
						System.out.println("Enter 1 for FIFO, and 2 for LRU.");
						alg = sys.nextInt();
					}

				MMUHardware mmu = new MMUHardware(alg);
				for (int j = 0; j < 500; j++)
					{
						int address = (int) (Math.random() * 16384);
						int w = (int) (Math.random() * 2000);
						System.out.println("Writing value of: " + w + " to logical address: " + address);
						mmu.writeMemory(address, w);
						System.out.println("Done Writing.\n********");
						System.out.println("Reading value back from logical address: " + address);
						int r = mmu.readMemory(address);
						System.out.println("Value is: " + r);
						if (w != r)
							{
								System.out.println("Error on read or write.");
								System.exit(1);
							}
						System.out.println("Done Reading.");
						System.out.println("**************************************");

					}
				for (int j = 0; j < 400; j++)
					{
						int address = (int) (Math.random() * 16384);

						System.out.println("Reading value from logical address: " + address);
						int r = mmu.readMemory(address);
						System.out.println("Value is: " + r);
						System.out.println("Done Reading.");
						System.out.println("**************************************");

					}
				System.out.println("Page Faults: " + mmu.getTotalPageFaults());
				sys.close();
				int r = mmu.readMemory(17000);
				System.out.println(r);
			}
	}
