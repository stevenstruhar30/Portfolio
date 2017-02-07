import java.util.Queue;

/**
 * Runs the requested bathroom experiment
 * 
 * @author sstruhar
 *
 */
public class TestRun implements Runnable
	{

		private Queue<User> uq;
		private static BathroomInterface b;
		private View view;

		public TestRun(Queue<User> a_q, String test, View v)
			{
				this.uq = a_q;
				this.view = v;
				switch (test)
					{
					case "1":
						b = new Bathroom1(uq.size(), v);//cutting
						break;
					case "2":
						b = new Bathroom2(uq.size(), v);//park
						break;
					case "3":
						b = new Bathroom3(uq.size(), v);//wait
						break;
					case "4":
						b = new Bathroom4(uq.size(), v);//await
						break;
					default:
						b = new Bathroom4(uq.size(), v);//await
						break;
					}

			}

		@Override
		public void run()
			{
				for (User u : uq)
					{
						u.setB(b);//set the bathroom for this user to use
					}
				User u;
				long sleepTime;
				long prev = 0;
				View.progressBar.setMinimum(0);
				View.progressBar.setMaximum(uq.size());
				int i = 1;
				View.setProgressBarPosition(0);
				while (uq.size() != 0)
					{
						try
							{
								/*
								 * this approach alleviates some of the race conditions that are inherent to starting the threads 
								 * at the same time. This will wait for each threads arrival time to come, then start the thread. 
								 * The thread will immediately call enter() on it's bathroom. This does not resolve simultaneous arrival times.
								 */
								u = uq.remove();
								sleepTime = u.arrival - prev;//calculate the duration between prev and this
								if (sleepTime < 0)//check for negative sleep times here. can occur if non FIFO output is used as input
									{
										view.sendError(Strings.NEGATIVESLEEPTIME);
										Thread.sleep(2500);
										System.exit(0);
									}
								prev = u.arrival;//set prev
								Thread.sleep(sleepTime);//sleep until arrival time
								View.setProgressBarPosition(i++);
								u.start();//start the thread
							} catch (Exception e)
							{
								e.printStackTrace();
							}
					}

			}

	}
