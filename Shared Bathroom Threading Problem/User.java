/**
 * User as defined in the bathroom problem can be of type Female, Male or Zombie. 
 * Immediately enters the bathroom, so it's arrival time is managed externally.
 * 
 * @author sstruhar
 *
 */

public class User extends Thread
	{
		public Type type;
		public final Long arrival;
		public final Long service;
		private int enumerator;
		private BathroomInterface b;

		
		/**
		 * Accept an array of strings and then parse them into the user using ENUMs
		 * 
		 * @param o
		 */
		public User(String[] o)
			{
				switch (o[0])
					{
					case Strings.FEMALETYPE:
						this.type = Type.FEMALE;
						break;
					case Strings.MALETYPE:
						this.type = Type.MALE;
						break;
					case Strings.ZOMBIETYPE:
						this.type = Type.ZOMBIE;
						break;

					}
				this.arrival = Long.parseLong(o[1]);
				this.service = Long.parseLong(o[2]);
			}
		/**
		 * Sets this User's bathroom
		 * 
		 * @param a_b
		 */
		public void setB(BathroomInterface a_b)
			{
				this.b = a_b;
			}


		/**
		 * initial arrival time is handled by the Run.java, so all we need to do here is enter the bathroom and tinkle.
		 */
		public void run()
			{
				b.enter(this);
				try
					{
						Thread.sleep(service);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				b.leave(this);

			}

		/**
		 * Return the type enum for this User
		 * 
		 * @return
		 */
		public int getEnumerator()
			{
				return enumerator;
			}

		/**
		 * set the index order which this user was created.
		 * 
		 * @param enumerator
		 */
		public void setEnumerator(int enumerator)
			{
				this.enumerator = enumerator;
			}

	}
