public class Test
{

	/**
	 * @author sstruhar
	 * @param args is the string from command line
	 */
	public static void main(String[] args)
	{

		String letters = "" ;
		
		View frame = new View();
		if(args.length == 0)
		{
			frame = new View();
		}
		else
		{
			letters = args[0];
			frame = new View(letters);
		}
		frame.setVisible(true);
	}
}


