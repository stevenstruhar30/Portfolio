import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JPanel;


public class LibraryPathPanel extends JPanel
{

	private static final long serialVersionUID = 8432030981449842619L;
	private static final int RADIUS = 20;
	private static final int CIRCUMFERENCE = RADIUS*2;
	private Collection<Edge> myEdges;
	private ArrayList<Library> myLibraries;
	private int totalWeight;

	/**
	 * Constructor
	 */
	public LibraryPathPanel()
	{
		this(null, null);
	}
	/**
	 * Constructor with a parameter
	 * @param edges
	 * @param a_LList
	 */
	public LibraryPathPanel(Collection<Edge> edges, ArrayList<Library> a_LList)
	{
		if(a_LList != null && edges != null)
		{
			myLibraries = a_LList;
			myEdges = edges;
		}
		else
		{
			throw new IllegalArgumentException("Libraries are null.");
		}
	}
	@Override
	public void paintComponent(Graphics g)
	{
		totalWeight = 0;

		super.paintComponent(g);

		if(myLibraries != null)
		{
			ArrayList<String> drawnItems = new ArrayList<String>();
			for(Edge item : myEdges)//once through to draw the lines and weights
			{
				String itemOne = item.getU().getName();
				String itemTwo = item.getV().getName();
				int weight = item.getWeight();
				Library libOne = myLibraries.get(getIndex(itemOne));
				Library libTwo = myLibraries.get(getIndex(itemTwo));

				g.setColor(Color.green.darker());
				g.drawLine((libOne.getX() + RADIUS), (libOne.getY() + RADIUS), (libTwo.getX() + RADIUS), (libTwo.getY() + RADIUS - 8));
				g.setColor(Color.BLACK);
				long weightX = (long) ((libOne.getX() + libTwo.getX())/2.0);
				long weightY = (long) ((libOne.getY() + libTwo.getY())/2.0);
				totalWeight += weight;
				g.drawString(String.valueOf(weight), ((int)weightX + RADIUS + 10) ,((int)weightY + RADIUS -5));
			}
			for(Edge item : myEdges)//once more to draw the ovals and ids
			{
				String itemOne = item.getU().getName();
				String itemTwo = item.getV().getName();
				Library libOne = myLibraries.get(getIndex(itemOne));
				Library libTwo = myLibraries.get(getIndex(itemTwo));
				if(!drawnItems.contains(libOne.getId()))
				{
					g.setColor(Color.LIGHT_GRAY);
					g.fillOval(libOne.getX(), libOne.getY(), CIRCUMFERENCE, CIRCUMFERENCE);
					g.setColor(Color.BLACK);
					g.drawString(libOne.getId(), libOne.getX()+12, libOne.getY()+RADIUS+5);
					drawnItems.add(libOne.getId());
				}
				if(!drawnItems.contains(libTwo.getId()))
				{
					g.setColor(Color.LIGHT_GRAY);
					g.fillOval(libTwo.getX(), libTwo.getY(), CIRCUMFERENCE, CIRCUMFERENCE);
					g.setColor(Color.BLACK);
					g.drawString(libTwo.getId(), libTwo.getX()+12, libTwo.getY()+RADIUS+5);
					drawnItems.add(libTwo.getId());
				}
			}
		}
		View.setTotalWeight(totalWeight);
	}
	/**
	 * Get the index of the library we are looking for
	 * @param a_Lookup
	 * @return the index of the library in question
	 */
	private int getIndex(String a_Lookup) 
	{
		for (int i = 0; i < myLibraries.size(); i++) 
		{
			if (myLibraries.get(i).equals(a_Lookup)) 
			{
				return i;
			}
		}
		return -1;
	}
}
