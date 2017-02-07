import java.util.HashMap;

import java.util.Set;

public class DisjointSets<E> 
{
	private HashMap<E,Node> setMap;

	/**
	 * @param 
	 * Constructor
	 */
	public DisjointSets(Set<E> a_SetElements) 
	{
		setMap =  new HashMap<E,Node>();
		for(E item : a_SetElements)
		{
			setMap.put(item, new Node(item,0));//this will place the item in a hash map using it's hash function
		}		
	}

	/**
	 * @param a_U
	 * @param a_V
	 * @return true if the items are in the same set
	 */
	public boolean areInSameSet(E a_U, E a_V) 
	{
		if(find(a_U).equals(find(a_V)))
		{
			return true;
		}
		else return false;
	}
	/**
	 * merge together the sets u and v
	 * @param a_U
	 * @param a_V
	 */
	public void mergeSets(E a_U, E a_V) 
	{
		final E uRoot = find(a_U);
		final E vRoot = find(a_V);

		if(!uRoot.equals(vRoot))//if the nodes aren't equal
		{
			final Node uNode = setMap.get(uRoot);
			final Node vNode = setMap.get(vRoot);
			if(uNode.getRank() < vNode.getRank())//if the rank of V is larger than U
			{
				uNode.setParent(vRoot);//set the parent of the uNode to the V Root
			}
			else if(uNode.getRank() > vNode.getRank())//if the rank of U is larger than V
			{
				vNode.setParent(uRoot);//set the parent of the vNode to the uRoot
			}
			else//do something in this case
			{
				vNode.setParent(uRoot);//Set the parent of the vNode to the uRoot
				uNode.ranking++;//push the rank of the uNode up
			}
		}
	}
	/**
	 * Find an element in this set
	 * @param a_Search
	 * @return the element that is found
	 */
	private E find(E a_Search)
	{
		final Node node = setMap.get(a_Search);
		if(node == null)
		{
			return null;//we didn't find it in the setMap
		}
		if(a_Search.equals(node.getParent()))
		{
			return a_Search;//it''s here so return it
		}
		node.setParent(find(node.getParent()));//set the parent of the node
		return node.getParent();// and return it.
	}

	/**
	 * A node class for use in the disjoint sets
	 *
	 */
	private class Node
	{
		private E myParent;
		private int ranking;
		/**
		 * Create a new node with a parent
		 * @param a_Parent
		 * @param a_Rank
		 */
		public Node(E a_Parent, int a_Rank)
		{
			myParent = a_Parent;
			ranking = a_Rank;
		}
		/**
		 * Get this node's parent
		 * @return an E
		 */
		private E getParent()
		{
			return myParent;
		}
		/**
		 * Set the parent
		 * @param a_Parent
		 */
		private void setParent(E a_Parent)
		{
			myParent = a_Parent;
		}
		/**
		 * Get the rank of this node
		 * @return
		 */
		private int getRank()
		{
			return ranking;
		}	
	}
}