/**
 * 
 * @author steve.struhar
 *
 */
public interface MMUHardwareInterface
	{
		boolean writeMemory(int address, int value);

		int readMemory(int address);

		void startSimulation();

		void stopSimulation();

		String getReferenceString();

		int getTotalPageFaults();

	}
