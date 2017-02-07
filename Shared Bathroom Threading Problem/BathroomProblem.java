import java.awt.EventQueue;

/**
 * Runner for bathroom problem
 * 
 * @author sstruhar
 *
 */
public class BathroomProblem
	{
		/**
		 * This is the main program. Run this to see the whole thing operate in all of it's glory.
		 * 
		 * @param args
		 *            ignored
		 */
		public static void main(String[] args)
			{

				EventQueue.invokeLater(new Runnable()
					{
						public void run()
							{
								try
									{
										View frame = new View();
										frame.setVisible(true);
									} catch (Exception e)
									{
										e.printStackTrace();
									}
							}
					});

			}

	}
