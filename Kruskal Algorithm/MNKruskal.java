
public class MNKruskal
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		LibFactory myGrid;
		if(args.length > 0)
		{
			myGrid = new LibFactory(args[0]);
		}
		else 
		{
			myGrid = new LibFactory();
		}
		View myView = new View(myGrid);
		myView.setVisible(true);
	}

}
