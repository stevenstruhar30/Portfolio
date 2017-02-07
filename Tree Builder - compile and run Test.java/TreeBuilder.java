import java.util.Arrays;

/**
 * 
 * @author sstruhar
 *
 */
public class TreeBuilder
{


	private BinaryTree<String> mainTree;
	private char[] lettersList;
	private boolean binaryTree = false;

	/**
	 * Constructor
	 * @param letters the letters to add to the tree
	 */
	public TreeBuilder(String letters , boolean binary)
	{
		binaryTree = binary;
		lettersList = new char[letters.length()];
		for(int i = 0; i < letters.length(); i ++)
		{
			lettersList[i] = (letters.charAt(i));
		}
		System.out.print("Input:  "); System.out.println( lettersList);
		mainTree = (treeIfy(lettersList));
		System.out.print("Output: "); getMainTree().printInOrder();
		System.out.println();
	}
	/**
	 * A recursive function to split the string in half recursively and build trees from the two halves
	 * @param letters A char array of letters to turn into a tree
	 * @return Returns a finished binary tree
	 */
	private BinaryTree<String> treeIfy(char[] letters)
	{
		if(binaryTree)
		{
			Arrays.sort(letters);
		}
		if(letters.length == 0) 
		{
			return new BinaryTree<String>();//create an empty tree
		}
		else if(letters.length == 1)
		{
			return new BinaryTree<String>(String.valueOf(letters[0]));//Create a tree (that is one with only one node)
		}
		else if(letters.length == 2)//Create a tree from the first element, an empty tree, and merge them together with the second element at the root
		{
			BinaryTree<String> tree1 = new BinaryTree<String>(String.valueOf(letters[0]));
			BinaryTree<String> tree2 = new BinaryTree<String>();
			BinaryTree<String> returner = new BinaryTree<String>();
			returner.merge(String.valueOf(letters[1]), tree1, tree2);
			return returner;
		}
		else if(letters.length == 3)//create a nice three element tree. All is well in the world
		{
			BinaryTree<String> tree1 = new BinaryTree<String>(String.valueOf(letters[0]));
			BinaryTree<String> tree2 = new BinaryTree<String>(String.valueOf(letters[2]));
			BinaryTree<String> returner = new BinaryTree<String>();
			returner.merge(String.valueOf(letters[1]), tree1, tree2);
			return returner;
		}
		else//Merge the element in the middle with the left and right trees from recursive calls
		{
			BinaryTree<String> returner = new BinaryTree<String>();
			int middle = (letters.length/2);
			char[] leftArray = Arrays.copyOfRange(letters, 0, middle);
			char[] rightArray = Arrays.copyOfRange(letters, (middle+1), letters.length );
			String root = String.valueOf(letters[middle]);
			returner.merge(root, treeIfy(leftArray), treeIfy(rightArray));
			return returner;
		}
	}
	/**
	 * Get the tree
	 * @return
	 */
	public BinaryTree<String> getMainTree()
	{
		return mainTree;
	}
	/**
	 * Override the toString method
	 */
	public String toString()
	{
		StringBuilder string = new StringBuilder();   
		StringAppender(mainTree.getRoot(), string);    
		return string.toString();
	}
	/**
	 * Help to traverse the tree in order and get the node elements in order (recursive)
	 * @param node (the node to start with)
	 * @param string the string to start with
	 */
	private void StringAppender(BinaryNode<String> node,StringBuilder string)
	{
		if(node ==null)
		{
			return;
		}
		if(node.getLeft() !=null){        
			StringAppender(node.getLeft(), string);        
		}    
		string.append(node.getElement());if(node.getRight() !=null){        
			StringAppender(node.getRight(), string);
		}
	}
}
