import java.util.ArrayList;

/**
 * 
 * @author steve.struhar
 *
 */
public class MMUHardware implements MMUHardwareInterface
	{
		static final int NUMBEROFPAGES = 16;
		static final int PHYSICALFRAMES = 4;
		static final int FRAMESIZE = 1024;
		static final int MAXOFFSET = 1023;
		static final int MINOFFSET = 0;
		static final int MAXPAGENUM = 15;
		static final int MINPAGENUM = 0;
		private PageTable pt;
		private ArrayList<String> referenceString;
		private int pageFaults;
		private int algorithm;

		/**
		 * Specify the algorithm to use for victim frame determination
		 * 
		 * @param a_algorithm
		 */
		public MMUHardware(int a_algorithm)
			{
				algorithm = a_algorithm;
				System.out.println("algorithm: " + algorithm);
				pt = new PageTable(NUMBEROFPAGES, PHYSICALFRAMES);
				referenceString = new ArrayList<String>();
				pageFaults = 0;

			}

		public MMUHardware()
			{
				this(1);

			}

		/**
		 * Write data to a logical address and return whether it was successful
		 */
		@Override
		public boolean writeMemory(int address, int value)
			{
				referenceString.add(Integer.toString(address));
				if (value > Integer.MAX_VALUE)
					{
						System.out.println("Invalid value");
						return false;
					}
				// Find the frame and offset
				int page = address / FRAMESIZE;
				int offset = address % FRAMESIZE;

				if (page > MAXPAGENUM || offset > MAXOFFSET || page < MINPAGENUM || offset < MINOFFSET)
					{
						System.out.println("Invalid memory write access");
						return false;
					} else
					{
						int frameInMemory = pt.getFrameNumberFromMemory(page);

						if (frameInMemory == -1)
							{
								pageFaults++;
								int victimFrame = PageTable.getVictimFrame(algorithm);
								if (PageTable.pageOnDisk(page))
									{
										Frame f = PageTable.ReadFrameFromDisk(page);
										PageTable.setFrame(f, victimFrame, page);
										PageTable.writeMemory(page, victimFrame, offset, value);
										return true;
									} else
									{
										Frame f = new Frame();
										PageTable.setFrame(f, victimFrame, page);
										PageTable.writeMemory(page, victimFrame, offset, value);
										return true;
									}
							} else
							{
								//this is why we check valid and present bits in setFrame and in write memory.
								PageTable.writeMemory(page, frameInMemory, offset, value);
								return true;
							}

					}
			}

		/**
		 * Read memory at a logical address
		 */
		@Override
		public int readMemory(int address)
			{
				referenceString.add(Integer.toString(address));
				// Find the frame and offset
				int page = address / FRAMESIZE;
				int offset = address % FRAMESIZE;

				if (page > MAXPAGENUM || offset > MAXOFFSET || page < MINPAGENUM || offset < MINOFFSET)
					{
						System.out.println("Invalid memory read access. Exiting...");
						crash();
					} else
					{
						int frameInMemory = pt.getFrameNumberFromMemory(page);

						if (frameInMemory == -1)
							{
								pageFaults++;
								int victimFrame = PageTable.getVictimFrame(algorithm);
								if (PageTable.pageOnDisk(page))
									{
										Frame f = PageTable.ReadFrameFromDisk(page);
										PageTable.setFrame(f, victimFrame, page);
										return PageTable.readMemory(page, victimFrame, offset);
									} else
									{
										/*
										 * The page has never been set or written to so it's not on disk, one option here is to load a page full of zeros, but instead we will let Write() do that later and just return a 0 which is the same as what we would find in this memory location on a new page. Let's save the overhead.
										 */
										return 0;
									}
							} else
							{
								return PageTable.readMemory(page, frameInMemory, offset);
							}

					}
				return -1;
			}

		@Override
		public void startSimulation()
			{

			}

		@Override
		public void stopSimulation()
			{
				pt.cleanFileSystem();
				System.out.println("/.../");
				System.exit(0);

			}

		/**
		 * Exit with an exit code of 1, but clean the pages off of the disk first.
		 */
		public void crash()
			{
				clean();
				System.out.println("/.../");
				System.exit(1);

			}

		/**
		 * Return the string of logical addresses that were referenced in order
		 */
		@Override
		public String getReferenceString()
			{
				String returner = "";
				for (String item : referenceString)
					{
						returner += "[" + item + "]";
					}
				return returner;
			}

		/**
		 * Return the total page faults
		 */
		@Override
		public int getTotalPageFaults()
			{
				return pageFaults;
			}

		/**
		 * \ Clean the filesystem of all old pages that may be lying around
		 */
		public void clean()
			{
				pt.cleanFileSystem();
			}

	}
