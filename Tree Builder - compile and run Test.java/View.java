import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

/**
 * 
 * @author sstruhar
 *
 */
public class View extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5050810892839269497L;
	private JPanel contentPane;
	private TreePaintGUI myTreeGraphic = new TreePaintGUI();
	private JTextField textField;
	private JCheckBox chckbxDrawTreeOn;
	private String treeLetters = "";
	private JLabel inOrderString;
	private JPanel drawingPane;
	private JLabel lblInorderTraversal;
	private JButton btnDraw;
	static final String STRING_TOO_LONG = "String length reduced to 50.";
	static final int MAX_LEN = 50;
	static final int ROOT_X_DIV = 2;
	static final int ROOT_Y = 45;
	static final int NODE_RAD = 15;
	static final int VERT = 60;
	static final int HORIZ = 300;
	private JCheckBox chckbxNewCheckBox;


	/**
	 * Constructor
	 */
	public View()
	{
		this("");
	}
	/**
	 * Overridden Constructor
	 * @param letters
	 */
	public View(String letters)
	{
		treeLetters = letters;
		createUI();
	}
	/**
	 * Create the frame.
	 */
	private void createUI()
	{
		setTitle("Binary String Tree");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setBounds(100, 100, 1326, 660);
		setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnDraw = new JButton("Draw");
		btnDraw.setBounds(399, 547, 89, 23);
		btnDraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				drawTree();
			}
		});
		contentPane.add(btnDraw);

		chckbxDrawTreeOn = new JCheckBox("Draw Tree on KeyPress");
		chckbxDrawTreeOn.setBounds(519, 547, 204, 23);
		chckbxDrawTreeOn.setSelected(true);
		contentPane.add(chckbxDrawTreeOn);

		textField = new JTextField();
		textField.setText(treeLetters);
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(chckbxDrawTreeOn.isSelected())
				{
					drawTree();
				}
			}
		});
		textField.setBounds(16, 543, 380, 28);
		contentPane.add(textField);
		textField.setColumns(10);

		lblInorderTraversal = new JLabel("InOrder Traversal:");
		lblInorderTraversal.setBounds(16, 597, 121, 16);
		contentPane.add(lblInorderTraversal);

		inOrderString = new JLabel("");
		inOrderString.setBounds(136, 597, 1166, 16);
		contentPane.add(inOrderString);

		drawingPane = new JPanel();
		drawingPane.setBorder(new TitledBorder(null, "Tree", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		drawingPane.setBounds(6, 0, 1314, 529);
		contentPane.add(drawingPane);
		drawingPane.setPreferredSize(new Dimension(1226, 1000));
		
		chckbxNewCheckBox = new JCheckBox("Build BST");
		chckbxNewCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				drawTree();
			}
		});
		chckbxNewCheckBox.setBounds(519, 573, 97, 23);
		contentPane.add(chckbxNewCheckBox);

		
	}
	/**
	 * Draw the tree
	 */
	public void drawTree() {
		treeLetters  = textField.getText();
		if(treeLetters.length() > MAX_LEN)
		{
			treeLetters = treeLetters.substring(0, MAX_LEN);
			textField.setText(treeLetters);
			showMessage(STRING_TOO_LONG, Color.red, Color.red);
		}
		super.paintComponents(getGraphics());
		Graphics2D g2d = (Graphics2D) drawingPane.getGraphics();
		TreeBuilder myTree = new TreeBuilder(treeLetters , chckbxNewCheckBox.isSelected());
		myTreeGraphic.createTreeGraphic(g2d, myTree.getMainTree().getRoot(), drawingPane.getWidth()/ROOT_X_DIV, ROOT_Y, VERT, HORIZ, NODE_RAD );
		inOrderString.setText(myTree.toString());
	}
	/**
	 * Show a message to the user about some event
	 * @param message the message to show
	 * @param messageColor the color of the inner area
	 * @param borderColor the color of the border
	 */
	public void showMessage(String message, Color messageColor, Color borderColor)
	{
		Growler myGrowler = new Growler(message , messageColor , borderColor);
		myGrowler.setLocationRelativeTo(null);
		myGrowler.setVisible(true);
	}
}
