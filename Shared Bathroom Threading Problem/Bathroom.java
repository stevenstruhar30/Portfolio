import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * No point in duplicating all of this code everywhere, so we will just extend this class for our various implementations. 
 * This DOES NOT contain the required enter and leave methods, but any class that implements BathroomInterface must, so look for Bathrooms 1-4. 
 * It is not necessary for this class to implement BathroomInterface
 * 
 * @author sstruhar
 *
 */
public class Bathroom
	{
		protected View view;

		protected long startTime;

		protected ReentrantLock lock = new ReentrantLock(true);
		protected Condition womenInBathroom = lock.newCondition();
		protected Condition menInBathroom = lock.newCondition();

		protected AtomicInteger numMale = new AtomicInteger(0);
		protected AtomicInteger numFemale = new AtomicInteger(0);
		protected AtomicInteger numZombie = new AtomicInteger(0);

		protected AtomicInteger occupantsServed = new AtomicInteger(0);
		protected AtomicInteger totalOccupants = new AtomicInteger(0);

		protected Queue<User> entranceQueue;
		protected LinkedBlockingQueue<User> servedQueue;
		protected LinkedBlockingQueue<String> outputQueue;

		/**
		 * Creates a bathroom and set's it's size and relative view (for access to various messages)
		 * 
		 * @param size
		 * @param v
		 */
		public Bathroom(int size, View v)
			{
				this.startTime = System.currentTimeMillis();
				this.totalOccupants.set(size);
				this.servedQueue = new LinkedBlockingQueue<User>();
				this.outputQueue = new LinkedBlockingQueue<String>();
				this.entranceQueue = new ConcurrentLinkedQueue<User>();
				this.view = v;

			}

		/**
		 * Get the count of users that are unlike you in the bathroom
		 * 
		 * @param type
		 * @return
		 */
		protected AtomicInteger getDissimilarTypeCount(Type type)
			{
				switch (type)
					{
					case FEMALE:
						return numMale;
					case MALE:
						return numFemale;
					case ZOMBIE:
						return new AtomicInteger(0);// always return 0 cause zombies don't have a gender
					default:
						return new AtomicInteger(0);
					}
			}

		/**
		 * get the count of users that are like you in the bathroom
		 * 
		 * @param type
		 * @return
		 */
		protected AtomicInteger getSameTypeCount(Type type)
			{
				switch (type)
					{
					case FEMALE:
						return numFemale;
					case MALE:
						return numMale;
					case ZOMBIE:
						return numZombie;
					default:
						return new AtomicInteger(0);
					}
			}

		/**
		 * Check to see if we are done. If so, use the time we finished to close out the operation
		 * 
		 * @param time
		 */
		protected void checkForCompletion(long time)
			{
				if (occupantsServed.get() == totalOccupants.get())
					{
						log(Strings.getRuntimeString(time));
						view.sendMessage(Strings.getOccupantsServedString(time, occupantsServed.get()));
						view.setEnableExecuteButton(true);
						writeFile(outputQueue);
						if (view.chckbxDebug.isSelected())
							{
								String output = "";
								for (User u : servedQueue)
									{
										output += (u.type.toString().substring(0, 1) + " ");
										output += String.format("%5d  %3d%n", u.arrival, u.service);
									}
								view.debugWindow(output);
								writeFile(output);
							}
					}
			}

		/**
		 * Log some data about who is in the bathroom and who is waiting in line.
		 */
		protected void outputData()
			{
				if (getSameTypeCount(Type.FEMALE).get() > 0 && getSameTypeCount(Type.MALE).get() > 0)
					{
						view.sendError(Strings.STATEERROR + servedQueue.size());
					}
				log(Strings.bathroomString(getSameTypeCount(Type.FEMALE).get(),getSameTypeCount(Type.MALE).get(),getSameTypeCount(Type.ZOMBIE).get()));
				String queue = Strings.QHEAD;
				for (User u : entranceQueue)
					{
						switch (u.type)
							{
							case FEMALE:
								queue += Strings.FEMALETYPE;
								break;
							case MALE:
								queue += Strings.MALETYPE;
								break;
							case ZOMBIE:
								queue += Strings.ZOMBIETYPE;
								break;
							}
					}
				queue += Strings.QTAIL;
				log(queue);
			}

		/**
		 * return the run time so far
		 * 
		 * @return
		 */
		protected long getTime()
			{
				return System.currentTimeMillis() - startTime;
			}

		/**
		 * Write the final log out to a file
		 * 
		 * @param output
		 */
		protected void writeFile(LinkedBlockingQueue<String> output)
			{
				BufferedWriter out = null;
				String fileString = "";
				int i = 1;
				while (output.peek() != null)
					{
						try
							{
								fileString += (output.take() + Strings.TAB + Strings.TAB + Strings.TAB);
							} catch (InterruptedException e)
							{
								e.printStackTrace();
							}
						if (i++ % 3 == 0)
							{
								fileString += System.getProperty(Strings.LINESEPARATOR);
							}
					}
				try
					{
						File file = new File(Strings.OUTPUTPATH);
						out = new BufferedWriter(new FileWriter(file));
						out.write(fileString);
					} catch (IOException e)
					{
						view.sendError(Strings.BADFILEWRITE);
						e.printStackTrace();
					} finally
					{
						if (out != null)
							try
								{
									out.close();
								} catch (IOException e)
								{
									e.printStackTrace();
								}
					}
			}

		/**
		 * Write the final log out to a file
		 * 
		 * @param output
		 */
		protected void writeFile(String fileString)
			{
				BufferedWriter out = null;

				try
					{
						File file = new File(Strings.DEBUGOUTPUTPATH);
						out = new BufferedWriter(new FileWriter(file));
						out.write(fileString);
					} catch (IOException e)
					{
						view.sendError(Strings.BADFILEWRITE);
						e.printStackTrace();
					} finally
					{
						if (out != null)
							try
								{
									out.close();
								} catch (IOException e)
								{
									e.printStackTrace();
								}
					}
			}

		/**
		 * Log data to the output queue
		 * 
		 * @param string
		 */
		protected void log(String string)
			{
				synchronized (this)
					{
						try
							{
								outputQueue.put(string);
							} catch (InterruptedException e)
							{
								e.printStackTrace();
							}
					}

			}

		

	}
