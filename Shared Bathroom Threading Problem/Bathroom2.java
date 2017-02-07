import java.util.concurrent.locks.LockSupport;

/**
 * A Class to control gender access to a unisex bathroom This implementation uses park and unpark from locksupport to control individual threads
 * 
 * @author sstruhar
 *
 */
class Bathroom2 extends Bathroom implements BathroomInterface
	{

		/**
		 * Everything we need is in the Bathroom Super Class
		 * 
		 * @param size
		 * @param v
		 */
		public Bathroom2(int size, View v)
			{
				super(size, v);
			}

		/**
		 * Allow a user to enter the bathroom if they are not restricted from doing so.
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
				//Test if we can enter
				while (entranceQueue.peek() != user || getDissimilarTypeCount(user.type).get() > 0)
					{
						LockSupport.park(user);

					}
				synchronized (this)
					{
						//we made it in, so we go about out business
						getSameTypeCount(user.type).getAndIncrement();
						servedQueue.add(entranceQueue.remove());
						log(Strings.enterTimeString(getTime(),l,t));
						outputData();
						User nextUser = entranceQueue.peek();//check the next person in line
						//if there is one, we unleash them to check the bathroom
						if (nextUser != null)
							{
								//We must notify the next person both when we enter and when we leave
								LockSupport.unpark(nextUser);
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
				User nextUser = entranceQueue.peek();
				synchronized (this)
					{
						getSameTypeCount(user.type).getAndDecrement();
						occupantsServed.incrementAndGet();
						log(Strings.exitTimeString(getTime(), l, t));
						outputData();
						if (nextUser != null)
							{
								LockSupport.unpark(nextUser);
							}
					}
				checkForCompletion(getTime());
			}

	}