/**
 * Control access to a unisex bathroom based on gender. Violates FIFO order and uses await() and signal() to coordinate threads Allows cutting to facilitate throughput.
 * 
 * @author sstruhar
 *
 */
class Bathroom1 extends Bathroom implements BathroomInterface
	{

		/**
		 * Everything we need is in the Bathroom Super Class
		 * 
		 * @param size
		 * @param v
		 */
		public Bathroom1(int size, View v)
			{
				super(size, v);
			}

		/**
		 * Allow a user to enter the bathroom if they are not restricted from doing so. Zombies are not restricted so they can cut. Any gender can cut in front of any other gender if the other is waiting, but if you arrive and there is an incompatible gender inside, then you have to wait. i.e. This implementation allows for cutting in line.
		 */
		public void enter(User user)
			{
				try
					{
						lock.lock();//first
						Type t = user.type;
						long l = user.arrival;

						switch (t)
							{
							case ZOMBIE:
								synchronized (this)
									{
										entranceQueue.add(user);
										log(Strings.arrivalTimeString(l,t));
										outputData();
										servedQueue.add(user);
										entranceQueue.remove(user);//this was just for output purposes
									}
								break;
							case MALE:
								entranceQueue.add(user);
								synchronized (this)
									{
										log(Strings.arrivalTimeString(l,t));
										outputData();
									}
								while (getDissimilarTypeCount(user.type).get() > 0)
									{

										try
											{
												womenInBathroom.await();
											} catch (InterruptedException e)
											{
												e.printStackTrace();
											}

									}
								servedQueue.add(entranceQueue.remove());
								break;
							case FEMALE:
								entranceQueue.add(user);
									{
										log(Strings.arrivalTimeString(l,t));
										outputData();
									}
								while (getDissimilarTypeCount(user.type).get() > 0)
									{

										try
											{
												menInBathroom.await();
											} catch (InterruptedException e)
											{
												e.printStackTrace();
											}

									}
								servedQueue.add(entranceQueue.remove());
								break;
							}
						synchronized (this)
							{
								getSameTypeCount(user.type).getAndIncrement();
								log(Strings.enterTimeString(getTime(),l,t));
								outputData();
								User nextUser = entranceQueue.peek();
								if (nextUser != null)
									{
										switch (user.type)
											{
											case ZOMBIE:
												break;
											case FEMALE:
												menInBathroom.signalAll();
												break;
											case MALE:
												womenInBathroom.signalAll();
												break;
											}

									}
							}
					} finally
					{
						lock.unlock();//last
					}

			}

		/**
		 * Decrement the counters and check the next person in line. Signal anyone that may need to know you left
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
										switch (user.type)
											{
											case ZOMBIE:
												break;
											case FEMALE:
												synchronized (menInBathroom)
													{
														womenInBathroom.signalAll();
													}
												break;

											case MALE:
												synchronized (womenInBathroom)
													{
														menInBathroom.signalAll();
													}
												break;

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