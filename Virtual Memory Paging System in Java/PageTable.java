import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 * @author steve.struhar
 *
 */
public class PageTable
	{
		private static int[][] table;
		private static Queue<Integer> nextFrame = new LinkedList<Integer>();
		private static long[] usage;

		private static Memory m;
		private static FileSystem fs;

		/**
		 * Three columns shall be present, physical address and page valid? You may wonder why I sandwiched the frame with the bits. Bit sandwich... For symmetry .|.
		 */
		public PageTable(int numberOfPages, int numberOfFrames)
			{
				table = new int[numberOfPages][3];
				m = new Memory(numberOfFrames);
				fs = new FileSystem(numberOfPages);
				usage = new long[] { 0, 0, 0, 0 };

				for (int i = 0; i < 15; i++)
					{
						table[i] = new int[] { 0, -1, 0 };
					}
			}

		/**
		 * Three columns shall be present, physical address and page valid? You may wonder why I sandwiched the frame with the bits. Bit sandwich... For symmetry .|.
		 */
		public PageTable()
			{
				this(MMUHardware.NUMBEROFPAGES, MMUHardware.PHYSICALFRAMES);
			}

		/**
		 * Get the frame number in memory that a particular page number resides in.
		 * 
		 * @param pageNum
		 * @return int frame # of a page in physical memory
		 */
		public int getFrameNumberFromMemory(int pageNum)
			{
				//never return a memory frame index for an invalid frame. return -1 in this case
				if (table[pageNum][0] == 1 && table[pageNum][2] == 1)
					{
						return table[pageNum][1];
					} else
					{
						return -1;// the mmu knows what to do with this.
					}
			}

		/**
		 * Set a page as removed from memory and invalid
		 * 
		 * @param page
		 */
		public static void setRemovedFromMemory(int page)
			{
				table[page][0] = 0;
				table[page][2] = 0;
			}

		/**
		 * Get the victim frame index to be replaced in memory. algorithm 1 uses FIFO and 2 uses LRU
		 * 
		 * @param algorithm
		 * @return the physical frame that is next in line to be replaced
		 */
		public static int getVictimFrame(int algorithm)
			{
				if (algorithm != 1 && algorithm != 2)
					{
						System.out.println("Invalid algorithm. Exiting...");
						System.exit(1);
					}
					{
						if (algorithm == 1)//FIFO
							{
								if (nextFrame.size() < MMUHardware.PHYSICALFRAMES)
									{
										return nextFrame.size();
									} else if (nextFrame.peek() != null)
									{
										int returnFrame = nextFrame.remove().intValue();
										for (int i = 0; i < MMUHardware.NUMBEROFPAGES; i++)
											{
												if (table[i][1] == returnFrame && table[i][0] == 1 && table[i][2] == 1)
													{
														fs.WriteFrameToDisk(m.getFrame(table[i][1]), i);
														setRemovedFromMemory(i);
														break;
													}
											}
										//Ensure that no frames can be generated that our out of bounds of memory frame size
										if (returnFrame < 0 || returnFrame > (MMUHardware.PHYSICALFRAMES -1))
											{
												System.out.println("Error calculating victim frame.");
												System.exit(1);
											}
										return returnFrame;
									}

							}
						if (algorithm == 2)//LRU
							{
								long min = usage[0];
								int returner = 0;
								//find the smallest timestamp
								for (int i = 1; i < MMUHardware.PHYSICALFRAMES; i++)
									{
										if (usage[i] < min)
											{
												min = usage[i];
												returner = i;
											}
									}
								for (int i = 0; i < MMUHardware.NUMBEROFPAGES; i++)
									{
										if (table[i][1] == returner && table[i][0] == 1 && table[i][2] == 1)
											{
												fs.WriteFrameToDisk(m.getFrame(table[i][1]), i);
												setRemovedFromMemory(i);
												break;
											}
									}
								//Ensure that no frames can be generated that our out of bounds of memory frame size
								if (returner < 0 || returner > (MMUHardware.PHYSICALFRAMES -1))
									{
										System.out.println("Error calculating victim frame.");
										System.exit(1);
									}
								return returner;

							}
						//otherwise, let's return something that will make the frame setter upset.
						return -1;
					}
			}

		/**
		 * Set a frame into memory at a location
		 * 
		 * @param f
		 * @param i
		 * @param page
		 */
		public static void setFrame(Frame f, int i, int page)
			{
				//never set a frame over a valid frame
				if (table[page][0] == 1 || table[page][2] == 1)
					{
						System.out.println("Memory overwrite error.");
						System.exit(1);
					} else if (i < 0 || i > 3)
					{
						System.out.println("Frame index error");
						System.exit(1);
					} else
					{
						m.setFrame(f, i);
						nextFrame.add(i);
						table[page][0] = 1;
						table[page][2] = 1;
					}

			}

		/**
		 * write data to memory.
		 * 
		 * @param page
		 * @param memoryFrame
		 * @param offset
		 * @param data
		 */
		public static void writeMemory(int page, int memoryFrame, int offset, int data)
			{
				//Never write memory over an invalid frame. This means the frame was not set correctly when read in from disk or when 
				//originally
				if (table[page][0] != 1 || table[page][2] != 1)
					{
						System.out.println("Memory overwrite error.");
						System.exit(1);
					} else
					{
						usage[memoryFrame] = System.currentTimeMillis();
						table[page][1] = memoryFrame;
						m.writeMemory(memoryFrame, offset, data);
					}
			}

		/**
		 * Read the data at a location in memory
		 * 
		 * @param page
		 * @param memoryFrame
		 * @param offset
		 * @return a value that represents a value written to a particular physical location in memory
		 */
		public static int readMemory(int page, int memoryFrame, int offset)
			{
				//never read a frame that is invalid
				if (table[page][0] != 1 || table[page][2] != 1)
					{
						System.out.println("Invalid memory read.");
						System.exit(1);
					} else
					{
						usage[memoryFrame] = System.currentTimeMillis();

					}
				return m.readMemory(memoryFrame, offset);
			}

		/**
		 * Detect if a page is on disk or not
		 * 
		 * @param page
		 * @return true if a page is on disk
		 */
		public static boolean pageOnDisk(int page)
			{
				return fs.pageOnDisk(page);
			}

		/**
		 * Read a page from disk
		 * 
		 * @param pageNum
		 * @return a frame as read from disk
		 */
		public static Frame ReadFrameFromDisk(int pageNum)
			{
				return fs.ReadFrameFromDisk(pageNum);
			}

		/**
		 * Clean all pages off of disk
		 */
		public void cleanFileSystem()
			{
				fs.clean();

			}

	}
