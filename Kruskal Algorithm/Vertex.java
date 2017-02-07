public class Vertex {

	private String myName;

	/**
	 * A vertex class
	 * @param a_Name at the vertex
	 */
	public Vertex(String a_Name) 
	{
		myName = a_Name;
	}
	/**
	 * Get the name of this vertex
	 * @return
	 */
	public String getName() 
	{
		return myName;
	}
	@Override
	public boolean equals(Object a_Object) 
	{
		if (a_Object != null && a_Object instanceof Vertex) 
		{
			if(myName.equals(((Vertex) a_Object).getName()))
			{
				return true;
			}
		} 
		return false;
	}
	@Override
	public int hashCode() 
	{
		int hashVal = 0;
		for (int i = 0; i< myName.length(); i++) 
		{
			hashVal = (127 * hashVal + myName.charAt(i)) % 16908799;
		}
		return hashVal;
	}
}