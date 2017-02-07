/**
 * A class to control inter-gender access to a unisex bathroom This implementation uses wait() and notify()
 * 
 * @author sstruhar
 *
 */
class Bathroom3 extends Bathroom implements BathroomInterface
	{

		/**
		 * Everything we need is in the Bathroom Super Class
		 * 
		 * @param size
		 * @param v
		 */
		public Bathroom3(int size, View v)
			{
				super(size, v);
			}

		/**
		 * Allow a user to enter the bathroom if they are not gender restricted from doing so, and if they are the next in the queue
		 */
		public void enter(User user)
			{
				Type t = user.type;
				long l = user.arrival;
				entranceQueue.add(user);
				synchronized (this)
					{
						log(Strings.arrivalTimeString(l,t));
						outputData();
					}
				//check the front of the queue for this user, otherwise wait for the bathrooom to contain no dissimilar genders
				while (entranceQueue.peek() != user || getDissimilarTypeCount(user.type).get() > 0)
					{
						synchronized (user)
							{
								try
									{
										user.wait();
									} catch (InterruptedException e)
									{
										e.printStackTrace();
									}
							}

					}
				//We made it so lets do our business
				synchronized (this)
					{

						getSameTypeCount(user.type).getAndIncrement();
						servedQueue.add(entranceQueue.remove());
						log(Strings.enterTimeString(getTime(), l, t));
						outputData();
						User nextUser = entranceQueue.peek();
						if (nextUser != null)
							{
								//We must notify the next person both when we enter and when we leave
								synchronized (nextUser)
									{
										nextUser.notify();
									}
							}

					}

			}

		/**
		 * Decrement the counters and check the next person in line
		 */
		public void leave(User user)
			{
				Type t = user.type;
				long l = user.arrival;
				synchronized (this)
					{
						getSameTypeCount(user.type).getAndDecrement();
						occupantsServed.incrementAndGet();
						log(Strings.exitTimeString(getTime(), l, t));
						outputData();
						User nextUser = entranceQueue.peek();
						if (nextUser != null)
							{
								synchronized (nextUser)
									{
										//wake them up
										nextUser.notify();
									}
							}

					}

				checkForCompletion(getTime());
			}

	}