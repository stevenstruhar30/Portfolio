import java.util.ArrayList;


public class Library
{
	private String id;
	private String county;
	private int x;
	private int y;
	private String name;
	private ArrayList<Connection> connectionList;


	/**
	 * Constructor (default that chains)
	 */
	public Library()
	{
		this(null, null, null,0,0);
	}
	/**
	 * Create a library with all required input
	 * @param a_ID
	 * @param a_County
	 * @param a_name
	 * @param a_x
	 * @param a_y
	 */
	public Library(String a_ID, String a_County, String a_name, int a_x, int a_y)
	{
		setId(a_ID);
		setCounty(a_County);
		setName(a_name);
		setX(a_x);
		setY(a_y);
	}
	/**
	 * Get the name of this library
	 * @return Name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * Set the name of the library
	 * @param a_Name
	 */
	public void setName(String a_Name)
	{
		this.name = a_Name;
	}
	/**
	 * Get this libraries Y location
	 * @return
	 */
	public int getY()
	{
		return y;
	}
	/**
	 * Set this libraries Y Location
	 * @param a_Y
	 */
	public void setY(int a_Y)
	{
		this.y = a_Y;
	}
	/**
	 * Get the X Value of this library
	 * @return
	 */
	public int getX()
	{
		return x;
	}
	/**
	 * Set this Libraries x Location
	 * @param a_X
	 */
	public void setX(int a_X)
	{
		this.x = a_X;
	}
	@Override
	public String toString()
	{
		return getName() + "," + getX() + "_" + getY();
	}
	/**
	 * Get the list of connections
	 * @return
	 */
	public ArrayList<Connection> getConnectionList()
	{
		return connectionList;
	}
	/**
	 * Set the connection list for this library
	 * @param a_ConnectionList
	 */
	public void setConnectionList(ArrayList<Connection> a_ConnectionList)
	{
		this.connectionList = a_ConnectionList;
	}
	@Override
	public boolean equals(Object a_That) {
		return a_That != null && ((((String)a_That).equals(this.getId())));
	}
	/**
	 * Get the ID id this library
	 * @return a String that is the ID
	 */
	public String getId()
	{
		return id;
	}
	/**
	 * Set the ID of this library
	 * @param id
	 */
	public void setId(String id)
	{
		this.id = id;
	}
	/**
	 * Get the county of this library
	 * @return string county
	 */
	public String getCounty()
	{
		return county;
	}
	/**
	 * Set the county of this library
	 * @param county
	 */
	public void setCounty(String county)
	{
		this.county = county;
	}

}
