import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class LibraryGraph {

	private Set<Vertex> vertices = new HashSet<Vertex>();
	private Collection<Edge> edges = new HashSet<Edge>();


	/**
	 * A class that builds a graph of library vertices from an array list of Libraries
	 * each with a list of connections to other libraries. The vertices are are combined
	 * into edges with weights.
	 * @param a_Input is the array list of Libraries
	 */

	public LibraryGraph(ArrayList<Library> a_Input) 
	{
		for (Library item : a_Input) {
			ArrayList<Connection> tempConnections = item.getConnectionList();
			if(tempConnections != null)
			{
				Vertex u = new Vertex(item.getId());
				vertices.add(u);
				for(Connection connectItem : tempConnections)
				{
					Vertex v = new Vertex(connectItem.getConnectionName());
					int weight = connectItem.getDistance();
					vertices.add(v);
					edges.add(new Edge(u, v, weight));
				}
			}
		}
	}

	/**
	 * Get the list of edges
	 * @return
	 */
	public Collection<Edge> getEdgeList() 
	{
		return edges;
	}

	/**
	 * Get the list of vertices
	 * @return
	 */
	public Set<Vertex> getVertices() 
	{
		return vertices;
	}

}