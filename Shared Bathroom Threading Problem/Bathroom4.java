import java.util.concurrent.locks.Condition;

/**
 * Control access to a unisex bathroom based on gender. Preserves FIFO order and uses await() and signal() to coordinate threads
 * 
 * @author sstruhar
 *
 */
class Bathroom4 extends Bathroom implements BathroomInterface
	{

		private Condition[] conditions;

		/**
		 * Everything we need is in the Bathroom Super Class except the conditions array, which is special
		 * 
		 * @param size
		 * @param v
		 */
		public Bathroom4(int size, View v)
			{
				super(size, v);
				conditions = new Condition[size];
				for (int i = 0; i < conditions.length; i++)//each user will get their own condition
					{
						conditions[i] = lock.newCondition();
					}

			}

		/**
		 * Allow a user to enter the bathroom if they are not restricted from doing so.
		 */
		public void enter(User user)
			{
				try
					{
						lock.lock();
						Type t = user.type;
						long l = user.arrival;
						entranceQueue.add(user);
						synchronized (this)
							{
								log(Strings.arrivalTimeString(l,t));
								outputData();
							}

						while (entranceQueue.peek() != user || getDissimilarTypeCount(user.type).get() > 0)
							{

								try
									{
										conditions[user.getEnumerator()].await();
									} catch (InterruptedException e)
									{
										e.printStackTrace();
									}

							}
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
										synchronized (conditions[nextUser.getEnumerator()])
											{
												conditions[nextUser.getEnumerator()].signal();
											}
									}
							}

					} finally
					{
						lock.unlock();
					}

			}

		/**
		 * Decrement the counters and check the next person in line
		 */
		public void leave(User user)
			{
				try
					{
						lock.lock();
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
										synchronized (conditions[nextUser.getEnumerator()])
											{
												conditions[nextUser.getEnumerator()].signal();
											}
									}
							}

						checkForCompletion(getTime());
					} finally
					{
						lock.unlock();
					}
			}
	}