/**
 * 
 * @author steve.struhar
 *
 */
public class Memory
	{
		private Frame[] memory;

		public Memory(int numberOfFrames)
			{
				memory = new Frame[numberOfFrames];
			}

		public Memory()
			{
				this(MMUHardware.PHYSICALFRAMES);
			}

		/**
		 * Get the Frame array that is the current memory
		 * 
		 * @return an array of 4 frames representing memory
		 */
		public Frame[] getMemory()
			{
				return memory;
			}

		/**
		 * Set some frame array as memory
		 * 
		 * @param memory
		 */
		public void setMemory(Frame[] memory)
			{
				this.memory = memory;
			}

		/**
		 * get some particular frame from memory
		 * 
		 * @param i
		 * @return a Frame from a memory index
		 */
		public Frame getFrame(int i)
			{
				return memory[i];
			}

		/**
		 * Set some particular frame into memory at a particular index
		 * 
		 * @param f
		 * @param i
		 */
		public void setFrame(Frame f, int i)
			{
				memory[i] = f;
			}

		/**
		 * Write an integer to memory at a specific physical address to be determined by the MMU or page table
		 * 
		 * @param frame
		 * @param offset
		 * @param data
		 */
		public void writeMemory(int frame, int offset, int data)
			{
				memory[frame].getBlock()[offset] = data;
			}

		/**
		 * Read an integer from a specific physical address in memory
		 * 
		 * @param frame
		 * @param offset
		 * @return int at location
		 */
		public int readMemory(int frame, int offset)
			{
				return memory[frame].getBlock()[offset];
			}

	}
