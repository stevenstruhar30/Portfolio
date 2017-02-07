/**
 * A class for storing a connection to another library
 *
 */
public class Connection
{
	
	private String connectionName;
	private int distance;

	/**
	 * Constructor
	 * @param a_Name is the name of the connections
	 * @param a_Distance is the weight that the connection hold to the parent class
	 */
	public Connection(String a_Name, int a_Distance)
	{
		setDistance(a_Distance);
		setConnectionName(a_Name);
	}
	/**
	 * Get the name of the connection
	 * @return a string name
	 */
	public String getConnectionName()
	{
		return connectionName;
	}
	/**
	 * Set the name of the connection
	 * @param connectionName
	 */
	public void setConnectionName(String connectionName)
	{
		this.connectionName = connectionName;
	}
	/**
	 * Get the distance away from the holding object that this connection represents
	 * @return an integer that is the distance from the object to this object
	 */
	public int getDistance()
	{
		return distance;
	}
	/**
	 * Set the distance to this connection
	 * @param distance
	 */
	public void setDistance(int distance)
	{
		this.distance = distance;
	}

}
