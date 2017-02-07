import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Color;

/**
 * unobtrusive and vanishing messages for users
 * 
 * @author sstruhar
 *
 */
public class Growler extends JFrame implements ActionListener
	{

		private static final long serialVersionUID = 1L;
		private JPanel contentPane;
		private Timer timer1;

		/**
		 * Create the frame and set the text message
		 * 
		 * @param the
		 *            message to set in the growler
		 */
		public Growler(String message, Color borderColor, Color bodyColor)
			{
				setBackground(bodyColor);
				setResizable(false);
				setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				setBounds(50, 50, 218, 40);
				setUndecorated(true);
				contentPane = new JPanel();
				contentPane.setBorder(new LineBorder(borderColor, 3, true));
				contentPane.setLayout(new BorderLayout(0, 0));
				setContentPane(contentPane);

				JPanel panel = new JPanel();
				panel.setBackground(bodyColor);
				contentPane.add(panel, BorderLayout.CENTER);

				JLabel label = new JLabel("");
				panel.add(label);
				this.setSize(message.length() * 10, this.getHeight() + 10);
				label.setText(message);
				timer1 = new Timer(1800, this);
				timer1.setInitialDelay(1800);
				timer1.start();
			}

		/**
		 * stop the timer and initiate then destroy
		 */
		@Override
		public void actionPerformed(ActionEvent e)
			{
				timer1.stop();
				for (float i = 1.0f; i > 0f; i -= 0.00008f)
					{
						this.setOpacity(i);//doesnt' work on windows... sorry.
					}
				this.setVisible(false);//hide
				this.dispose();//destroy

			}

	}
