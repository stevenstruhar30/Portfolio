import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Read and parse a file into users
 * 
 * @author sstruhar
 *
 */
public class FileGetter
	{
		private Queue<User> ul = new LinkedList<User>();
		private View view;

		

		public FileGetter(View v)
			{
				this.view = v;
			}

		/**
		 * Reads a file in from a particular path and parses it into Users which are stored in a Queue
		 * 
		 * @param path
		 *            the path to load
		 */
		public boolean read(String path)
			{
				BufferedReader reader = null;
				User u;
				int i = 0;
				try
					{

						reader = new BufferedReader(new FileReader(path));
						String line = reader.readLine();
						int lineNumber = 1;
						while (line != null)
							{
								String[] s = line.split(Strings.DATASPLITTER);//split by any spaces in the file on each line
								if (s.length != 3)
									{
										view.sendError(Strings.BADFILELINE + lineNumber);
										reader.close();
										return false;
									}
								if (!(Strings.TYPEARRAY.contains(s[0])))
									{
										view.sendError(Strings.BADFILELINE + lineNumber);
										reader.close();
										return false;
									}
								u = new User(s);
								u.setEnumerator(i++);
								u.setName(u.type.toString() + Strings.DASH + ((Long) u.arrival).toString());
								ul.add(u);
								line = reader.readLine();
								lineNumber++;
								if (line == null)
									{
										break;//must be done
									}

							}
						if (ul.size() == 0)
							{
								view.sendError(Strings.EMPTYFILE);
								reader.close();
								return false;
							}

					} catch (FileNotFoundException e)
					{
						e.printStackTrace();
					} catch (IOException e)
					{
						e.printStackTrace();
					} finally
					{
						// Close the BufferedReader.
						try
							{
								reader.close();
							} catch (IOException e)
							{
								e.printStackTrace();
							}
					}
				return true;
			}
		/**
		 * Returns the queue of users that are created when the input file is read
		 * 
		 * @return Queue<User> queue of users
		 */
		public Queue<User> getUl()
			{
				return ul;
			}
	}
