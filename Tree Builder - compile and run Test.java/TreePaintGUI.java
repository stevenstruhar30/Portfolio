import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * 
 * @author sstruhar
 *
 */
public class TreePaintGUI
{
	/**
	 * Paint the tree on the supplied graphics object
	 * @param graphics is the graphics from the component that the tree should be painted on
	 * @param root is the root node of the tree
	 * @param x is the x location to paint the root node
	 * @param y is the y location to paint the root node
	 * @param verticalSpace is the vertical space to start between nodes
	 * @param horizontalSpace is the horizontal spacing between nodes to start
	 * @param nodeRadius the radius of each node to paint
	 * @return returns a Graphics2D object with the painted tree
	 */
	public Graphics2D createTreeGraphic(Graphics2D graphics, BinaryNode<String> root, int x, int y, int verticalSpace,  int horizontalSpace, int nodeRadius) 
	{
		graphics.setColor(Color.lightGray);
		if(root != null)
		{
			graphics.fillOval(x - nodeRadius, y - nodeRadius, nodeRadius * 2, nodeRadius * 2);
			graphics.setColor(Color.black);
			graphics.drawString(root.getElement(), x - 4, y + 4);
			if (root.getLeft() != null) 
			{
				lineToLeftChild(graphics, x - horizontalSpace, y + verticalSpace, x, y, verticalSpace, nodeRadius);
				createTreeGraphic(graphics, root.getLeft(), x - horizontalSpace, y + verticalSpace, verticalSpace, horizontalSpace / 2, nodeRadius);
			}
			if (root.getRight() != null) 
			{
				lineToRightChild(graphics, x + horizontalSpace, y + verticalSpace, x, y, verticalSpace, nodeRadius);
				createTreeGraphic(graphics, root.getRight(), x + horizontalSpace, y + verticalSpace, verticalSpace, horizontalSpace / 2, nodeRadius);
			}
		}
		return graphics;
	}
	/**
	 * Draw a line from a node to the future location of the right child. Calculates the hypotenuse of the triangle, 
	 * and the start x and y and the end x and y
	 * @param graphics
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param verticalSpace
	 * @param radius
	 */
	private void lineToRightChild(Graphics2D graphics, int x1, int y1, int x2, int y2, int verticalSpace, int radius) 
	{
		double hypotenuse = Math.sqrt(verticalSpace * verticalSpace + (x2 - x1) * (x2 - x1));//sgrt of a^2 + b^2
		int firstX = (int)(x1 - radius * (x1 - x2) / hypotenuse);//slant right
		int firstY = (int)(y1 - radius * verticalSpace / hypotenuse);//start height
		int secondX = (int)(x2 + radius * (x1 - x2) / hypotenuse);//slant right
		int secondY = (int)(y2 + radius * verticalSpace / hypotenuse);//end height
		graphics.setStroke(new BasicStroke(2));
		graphics.drawLine(firstX, firstY, secondX, secondY);
	}
	/**
	 * Draw a line from a node to the future location of the left child
	 * @param graphics
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param verticalSpace
	 * @param radius
	 */
	private void lineToLeftChild(Graphics2D graphics, int x1, int y1, int x2, int y2, int verticalSpace, int radius) 
	{
		double hypotenuse = Math.sqrt(verticalSpace * verticalSpace + (x2 - x1) * (x2 - x1));
		int firstX = (int)(x1 + radius * (x2 - x1) / hypotenuse);//slant left
		int firstY = (int)(y1 - radius * verticalSpace / hypotenuse);//Start height
		int secondX = (int)(x2 - radius * (x2 - x1) / hypotenuse);//slant left
		int secondY = (int)(y2 + radius * verticalSpace / hypotenuse);//end height
		graphics.setStroke(new BasicStroke(2));
		graphics.drawLine(firstX, firstY, secondX, secondY);
	}	
}
