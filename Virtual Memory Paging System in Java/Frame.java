import java.io.Serializable;

/**
 * 
 * @author steve.struhar
 *
 */
public class Frame implements Serializable
	{
		private static final long serialVersionUID = 201503L;
		private int[] block = new int[MMUHardware.FRAMESIZE];

		/**
		 * Get the array of data that this frame represents
		 * 
		 * @return an array of [MMUHardware.FRAMESIZE] integers
		 */
		public int[] getBlock()
			{
				return block;
			}

		/**
		 * Set a block of data into this frame
		 * 
		 * @param block
		 */
		public void setBlock(int[] block)
			{
				this.block = block;
			}

		/**
		 * Print out this block of data to the console
		 */
		public void print()
			{
				System.out.println("Start of Block.");
				for (int i = 0; i < block.length; i++)
					{

						System.out.print("[" + block[i] + "]");
						if (i % 64 == 0 && i > 0)
							{
								System.out.println();
							}

					}
				System.out.println("\nEnd of Block.");
			}
	}
