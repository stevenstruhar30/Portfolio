import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;


public class LibraryMapPanel extends JPanel
{
	private static final long serialVersionUID = 2633046527423815804L;
	private static final int RADIUS = 20;
	private static final int CIRCUMFERENCE = RADIUS*2;

	private ArrayList<Library> myLibraries;
	/**
	 * Constructor
	 */
	public LibraryMapPanel()
	{
		this(null);
	}
	/*
	 * Constructor with a parameter
	 */
	public LibraryMapPanel(ArrayList<Library> a_LList)
	{
		if(a_LList != null)
		{
			myLibraries = a_LList;
		}
		else
		{
			throw new IllegalArgumentException("Library is null.");
		}
	}
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		ArrayList<String> drawnItems = new ArrayList<String>();
		if(myLibraries != null)
		{

			for(Library item : myLibraries)//once for the lines
			{
				ArrayList<Connection> connectedLibrariesList = item.getConnectionList();
				if(connectedLibrariesList != null)
				{
					for(Connection connection : connectedLibrariesList)
					{
						int index = getIndex(connection.getConnectionName());
						Library tempLibrary = myLibraries.get(index);
						g.setColor(Color.LIGHT_GRAY);
						g.drawLine((item.getX() + RADIUS), (item.getY() + RADIUS), (tempLibrary.getX() + RADIUS), (tempLibrary.getY() + RADIUS));
						g.setColor(Color.BLACK);
						long weightX = (long) ((item.getX() + tempLibrary.getX())/2.0);
						long weightY = (long) ((item.getY() + tempLibrary.getY())/2.0);
						g.drawString(String.valueOf(connection.getDistance()), ((int)weightX + RADIUS + 10) ,((int)weightY + RADIUS - 5)) ;
					}
				}
			}
			for(Library item : myLibraries)//once for the nodes
			{
				if(!drawnItems.contains(item.getId()))
				{
					g.setColor(Color.LIGHT_GRAY);
					g.fillOval(item.getX(), item.getY(), CIRCUMFERENCE, CIRCUMFERENCE);
					g.setColor(Color.BLACK);
					g.drawString(item.getId(), item.getX()+12, item.getY()+RADIUS+5);
					drawnItems.add(item.getId());
				}
			}
		}
	}
	/**
	 * Get the index of the library you are loking for
	 * @param a_Lookup
	 * @return the index of the item in question
	 */
	private int getIndex(String a_Lookup) {
		for (int i = 0; i < myLibraries.size(); i++) {
			if (myLibraries.get(i).equals(a_Lookup)) {
				return i;
			}
		}
		return -1;
	}

}
