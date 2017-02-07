import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 
 * @author steve.struhar
 *
 */
public class FileSystem
	{
		private int numberOfPages;

		public FileSystem(int numPages)
			{
				numberOfPages = numPages;
				for (int i = 0; i < numberOfPages; i++)
					{
						File file = new File("data/disk/" + i);
						file.mkdirs();
					}
				clean();

			}

		public FileSystem()
			{
				this(MMUHardware.NUMBEROFPAGES);
			}

		/**
		 * Write the frame out to a particular page on disk
		 * 
		 * @param f
		 * @param i
		 */
		public void WriteFrameToDisk(Frame f, int i)
			{
				synchronized (this)
					{
						try
							{
								FileOutputStream fileOut = new FileOutputStream("data/disk/" + i + "/page.ser");
								ObjectOutputStream out = new ObjectOutputStream(fileOut);
								out.writeObject(f);
								out.close();
								fileOut.close();
							} catch (Exception e)
							{
								e.printStackTrace();
							}
					}
			}

		/**
		 * Read a particular page in from disk
		 * 
		 * @param i
		 * @return a Frame (page) as read from disk
		 */
		public Frame ReadFrameFromDisk(int i)
			{
				Frame f = null;
				String path = "data/disk/" + i + "/page.ser";
				try
					{
						FileInputStream fileIn = new FileInputStream(path);
						ObjectInputStream in = new ObjectInputStream(fileIn);
						f = (Frame) in.readObject();
						in.close();
						fileIn.close();
					} catch (IOException e)
					{
						e.printStackTrace();
						return null;
					} catch (ClassNotFoundException c)
					{
						System.out.println("Frame class not found");
						c.printStackTrace();
						return null;
					}
				File fd = new File(path);
				fd.delete();
				return f;
			}

		/**
		 * Detect a page number's existence on the disk
		 * 
		 * @param pageNum
		 * @return true if a page is resident on disk
		 */
		public boolean pageOnDisk(int pageNum)
			{
				File f = new File("data/disk/" + pageNum + "/page.ser");
				if (f.exists())
					{
						return true;
					}
				return false;
			}

		/**
		 * Delete a particular page on the disk
		 * 
		 * @param pageNum
		 */
		public void deletePageOnDisk(int pageNum)
			{
				File f = new File("data/disk/" + pageNum + "/page.ser");
				if (f.exists())
					{
						f.delete();
					}
			}

		/**
		 * Clean all the pages off the disk
		 */
		public void clean()
			{
				for (int k = 0; k < numberOfPages; k++)
					{
						deletePageOnDisk(k);
					}
			}

	}
