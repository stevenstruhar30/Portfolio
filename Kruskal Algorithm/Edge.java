public class Edge implements Comparable<Edge> 
{

	private Vertex myU;
	private Vertex myV;
	private int myWeight;

	/**
	 * Edge class
	 * 
	 * @param a_U
	 * @param a_V
	 * @param a_Weight
	 */
	public Edge(Vertex a_U, Vertex a_V, int a_Weight) 
	{
		setU(a_U);
		setV(a_V);
		setWeight(a_Weight);
	}

	@Override
	public boolean equals(Object a_Object) 
	{
		if (a_Object instanceof Edge) 
		{
			Edge testEdge = (Edge) a_Object;
			//if the edge has both vertices that the a_object has,...and is the same weight, then we have it
			if (myWeight == testEdge.myWeight && 
					((myU.equals(testEdge.myU) && myV.equals(testEdge.myV)) 
							|| 
							(myU.equals(testEdge.myV) && myV.equals(testEdge.myU))))
			{
				return true;
			}
		} 
		return false;
		
	}
	@Override
	public int hashCode() 
	{
		int one = myWeight;
		int two = myU.hashCode();
		int three = myV.hashCode();
		int returner = 0;
		if(myU == null || myV == null) 
		{
			return returner;//0 is fine here
		}
		else 
		{
			returner = one ^ (two) ^ (three);//fold them bytes together
		}
		return returner;
	}
	/**
	 * Get the weight of the edge
	 * @return
	 */
	public int getWeight() 
	{
		return myWeight;
	}
	/** 
	 * Set the weight of the edge
	 * @param a_Weight
	 */
	public void setWeight(int a_Weight) {
		this.myWeight = a_Weight;
	}
	/**
	 * Compare this edge to another
	 */
	public int compareTo(Edge a_That) 
	{
		return myWeight - a_That.getWeight();
	}
	/**
	 * Get the vertex U
	 * @return
	 */
	public Vertex getU() {
		return myU;
	}
	/**
	 * Set the vertext U
	 * @param a_U
	 */
	public void setU(Vertex a_U) {
		this.myU = a_U;
	}
	/**
	 * Get the vertex V
	 * @return
	 */
	public Vertex getV() {
		return myV;
	}
	/**
	 * Set the vertext V
	 * @param a_V
	 */
	public void setV(Vertex a_V) {
		this.myV = a_V;
	}
}