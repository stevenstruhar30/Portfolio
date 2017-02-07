import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.UIManager;


public class View extends JFrame
{

	private static final long serialVersionUID = 1392132539523174868L;
	private static final String UNITS = "miles";
	private JPanel contentPane;
	private JPanel myEmptyPath;
	private LibraryMapPanel myGraph;
	private LibFactory myLibFactory;
	private LibraryPathPanel myPathMap;
	private JButton btnReset;
	private JScrollPane pathScroll;
	private JScrollPane mapScrollPane;
	private JButton btnSolve;
	private static JLabel weightLabel;
	private JTextPane legendTextPane;
	private JPanel legendPanel;
	private JScrollPane legendScrollPane;

	/**
	 * Create the frame.
	 */
	public View(LibFactory a_LibraryFactory)
	{
		myLibFactory = a_LibraryFactory;
		if(myLibFactory == null) {
			System.out.println("Null Input.");
			System.exit(0);
		}
		setResizable(false);
		setTitle("Minimum Spanning Tree");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1376, 831);

		contentPane = new JPanel();
		contentPane.setBorder(new TitledBorder(null, "Library Minimum Spanning Tree", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		mapScrollPane = new JScrollPane();
		mapScrollPane.setBounds(16, 29, 665, 617);
		contentPane.add(mapScrollPane);
		myGraph = new LibraryMapPanel(myLibFactory.getLibraryList());
		mapScrollPane.setViewportView(myGraph);
		myGraph.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		myGraph.setLayout(null);
		myGraph.setPreferredSize(new Dimension(2000,2000));

		pathScroll = new JScrollPane();
		pathScroll.setBounds(694, 29, 665, 617);
		contentPane.add(pathScroll);
		myEmptyPath = new JPanel();
		pathScroll.setViewportView(myEmptyPath);
		myEmptyPath.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));	

		btnSolve = new JButton("Solve");
		btnSolve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.remove(pathScroll);
				myLibFactory.solve();
				pathScroll = new JScrollPane();
				pathScroll.setBounds(694, 29, 665, 617);
				contentPane.add(pathScroll);
				myPathMap = new LibraryPathPanel(myLibFactory.getEdges(), myLibFactory.getLibraryList());
				myPathMap.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
				myPathMap.setLayout(null);
				myPathMap.setPreferredSize(new Dimension(2000,2000));
				pathScroll.setViewportView(myPathMap);
			}
		});
		btnSolve.setBounds(24, 675, 117, 29);
		contentPane.add(btnSolve);
		btnReset = new JButton("Reset");
		btnReset.setBounds(141, 675, 117, 29);
		contentPane.add(btnReset);
		weightLabel = new JLabel("");
		weightLabel.setBounds(704, 649, 238, 16);
		contentPane.add(weightLabel);
		
		legendPanel = new JPanel();
		legendPanel.setBorder(new TitledBorder(null, "Legend", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		legendPanel.setBounds(275, 658, 406, 145);
		contentPane.add(legendPanel);
		legendPanel.setLayout(null);
		
		legendScrollPane = new JScrollPane();
		legendScrollPane.setBounds(6, 18, 394, 121);
		legendPanel.add(legendScrollPane);
		
		legendTextPane = new JTextPane();
		legendTextPane.setEditable(false);
		legendScrollPane.setViewportView(legendTextPane);
		legendTextPane.setBackground(UIManager.getColor("Button.background"));
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.remove(pathScroll);
				pathScroll = new JScrollPane();
				pathScroll.setBounds(694, 29, 665, 617);
				contentPane.add(pathScroll);
				myEmptyPath = new JPanel();
				pathScroll.setViewportView(myEmptyPath);
				myEmptyPath.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
				weightLabel.setText("");
			}
		});
		setLegend();
	}
	/**
	 * Provide a way for the inner panel to set the total weight
	 * @param weight
	 */
	public static void setTotalWeight(int weight)
	{
		weightLabel.setText("Total Distance: " + weight + " " + UNITS);
	}
	/**
	 * Get the string that represents the element ids and names
	 * @return string
	 */
	private String getLegend()
	{
		ArrayList<Library> temp = myLibFactory.getLibraryList();
		String returner = "";
		for(Library item : temp)
		{
			returner += item.getId() + " = " + item.getName() + "\n";
		}
		return returner;
	}
	/**
	 * Set the legend string into the legend panel
	 */
	private void setLegend()
	{
		legendTextPane.setText(getLegend());
	}
}
