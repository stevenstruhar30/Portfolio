import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Simple Jfram that can update it's text area
 * 
 * @author sstruhar
 *
 */
public class DebugWindow extends JFrame
	{

		private static final long serialVersionUID = 1L;
		private JPanel contentPane;
		private JTextArea textArea;

		public DebugWindow()
			{
				setResizable(false);
				setTitle(Strings.DEBUGPANELNAME);
				setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				setBounds(100, 100, 497, 532);
				contentPane = new JPanel();
				contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
				setContentPane(contentPane);
				contentPane.setLayout(null);

				textArea = new JTextArea();
				textArea.setEditable(false);

				JScrollPane scrollPane = new JScrollPane(textArea);
				scrollPane.setBounds(10, 11, 461, 472);
				scrollPane.setViewportView(textArea);
				contentPane.add(scrollPane);

			}

		public void setMessage(String message)
			{
				textArea.setText(message);
				this.setVisible(true);
			}

	}
