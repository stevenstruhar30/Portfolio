import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;


public class LibFactory
{
	private static final String LIB_OBJ_SPLIT_REGEX = ",";
	private static final String MAC_SRC_LIBS_TXT = "./libraries.txt";//MAC PATH
	private static final String WIN_SRC_LIBS_TXT = "./libraries.txt";//WIN PATH
	private static final String MAC_SRC_DIST_TXT = "./distances.txt";//MAC PATH
	private static final String WIN_SRC_DIST_TXT = "./distances.txt";//WIN PATH
	private ArrayList<Library> libList;
	private LibraryGraph libGraph;
	private Collection<Edge> myEdges;

	/**
	 * Use the default path to chain the constructors
	 */
	public LibFactory()
	{
		this(getEnvironmentLibrariesPath());
	}

	/**
	 * Constructor
	 * @param a_Path
	 */
	public LibFactory(String a_Path)
	{
		BufferedReader reader = null;
		libList = new ArrayList<Library>();
		try
		{
			reader = new BufferedReader(new FileReader(a_Path));
		} catch (FileNotFoundException e)
		{
			System.out.println("Exiting.  File not found.");
			System.exit(0);		
		}
		String line = null;
		try
		{
			while ((line = reader.readLine()) != null) {
				String[] libraryInfo = line.split(LIB_OBJ_SPLIT_REGEX);
				int x = Integer.parseInt(libraryInfo[3]);
				int y = Integer.parseInt(libraryInfo[4]);
				Library temp = new Library(libraryInfo[0] , libraryInfo[1] , libraryInfo[2] , x , y);
				libList.add(temp);

			}
			getConnections(getEnvironmentDistancesPath());
		} catch (IOException e)
		{
			System.out.println("Exiting.  File not found or is malformed.");
			System.exit(0);
		} catch (NumberFormatException e)
		{
			System.out.println("Exiting.  File is malformed.");
			System.exit(0);
		}
	}
	/**
	 * Create connections for the libraries
	 * @param a_Path
	 */
	private void getConnections(String a_Path)
	{
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(a_Path));
		} catch (FileNotFoundException e)
		{
			System.out.println("Exiting.  File not found.");
			System.exit(0);		
		}
		String line = null;
		try
		{
			while ((line = reader.readLine()) != null) {
				String[] distanceInfo = line.split(LIB_OBJ_SPLIT_REGEX);
				int weight = Integer.parseInt(distanceInfo[2]);
				Library tempLibrary = getLibrary(distanceInfo[0]);
				String connectionString = distanceInfo[1];
				Connection tempConnection = new Connection(connectionString, weight);
				ArrayList<Connection> connectionList = tempLibrary.getConnectionList();
				if(connectionList == null)
				{
					connectionList = new ArrayList<Connection>();
				}
				connectionList.add(tempConnection);
				tempLibrary.setConnectionList(connectionList);

			}
		} catch (IOException e)
		{
			System.out.println("Exiting.  File not found or is malformed.");
			System.exit(0);
		} catch (NumberFormatException e)
		{
			System.out.println("Exiting.  File is malformed.");
			System.exit(0);
		}
	}
	/**
	 * Get a library object from it's ID
	 * @param a_Id
	 * @return Library
	 */
	private Library getLibrary(String a_Id)
	{
		for(Library item : libList) {
			if(item.getId().equals(a_Id))
			{
				return item;
			}
		}
		return null;
	}
	/**
	 * Solve for the MSP
	 */
	public void solve()
	{
		libGraph = new LibraryGraph(libList);
		setEdges(kruskals(libGraph));
	}


	/**
	 * Execute Kruskal's algorithm on the supplied LibraryGraph by adding all of the edges to a priority
	 * queue which sorts them by weight (smallest on top) and then merges the edges into a list using a Disjoint set until
	 * all of the vertices in the original list are accounted for.  The the array list of edges is returned as the MST
	 * 
	 * @param a_LibraryGraph is the graph of libraries that you pass into the algorithm
	 * @return the Minimum Spanning Tree of the graph, which might be null
	 */
	private ArrayList<Edge> kruskals(LibraryGraph a_LibraryGraph) 
	{
		DisjointSets<Vertex> myDisjointSet = new DisjointSets<Vertex>(a_LibraryGraph.getVertices());
		PriorityQueue<Edge> myPriorityQueue = new PriorityQueue<Edge>();
		ArrayList<Edge> returner = new ArrayList<Edge>();  
		for (Edge item : a_LibraryGraph.getEdgeList()) 
		{
			myPriorityQueue.add(item);//add each one to the queue which sorts them by weight
		}
		while ((a_LibraryGraph.getVertices().size() - 1) > (returner.size() ) && !myPriorityQueue.isEmpty() )//while the list doesn't have all the libraries and the queue isn't empty
		{
			Edge tempEdge = myPriorityQueue.poll();//get the smallest edge from the queue
			if(tempEdge != null)
			{
				Vertex v = tempEdge.getV();
				Vertex u = tempEdge.getU();
				if (!myDisjointSet.areInSameSet(u, v))//if the vertices aren't in the same set,
				{
					myDisjointSet.mergeSets(v, u);// merge them together
					returner.add(tempEdge);//and add them to the list
				}	
				if((a_LibraryGraph.getVertices().size() - 1) == (returner.size()))//if the list has the right number of edges, then it has the shorted path through all libraries
				{
					return returner;//so return the list (this is a MSP)
				}
			}
		}
		return null;//else return null
	}
	/**
	 * Get the list of libraries
	 * @return an Array List of Libraries
	 */
	public ArrayList<Library> getLibraryList()
	{
		return libList;
	}
	/**
	 * Get a collection of edges
	 * @return a collection of edges
	 */
	public Collection<Edge> getEdges()
	{
		return myEdges;
	}
	/**
	 * Set the edges
	 * @param a_MyEdges
	 */
	private void setEdges(Collection<Edge> a_MyEdges)
	{
		this.myEdges = a_MyEdges;
	}
	/**
	 * Get the path to the library file based on your machine type
	 * @return string path
	 */
	private static String getEnvironmentLibrariesPath()
	{
		String OS = System.getProperty("os.name").toLowerCase();
		if((OS.indexOf("win") >= 0))
		{
			return WIN_SRC_LIBS_TXT;
		}
		else return MAC_SRC_LIBS_TXT;
	}
	/**
	 * Get the path to the distances file based on your machine type
	 * @return string path
	 */
	private static String getEnvironmentDistancesPath()
	{
		String OS = System.getProperty("os.name").toLowerCase();
		if((OS.indexOf("win") >= 0))
		{
			return WIN_SRC_DIST_TXT;
		}
		else return MAC_SRC_DIST_TXT;
	}

}
