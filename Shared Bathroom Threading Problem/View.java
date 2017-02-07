import java.awt.Color;
import java.util.Enumeration;
import java.util.List;
import java.util.Queue;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;

/**
 * UI layout for the test runner
 * 
 * @author sstruhar
 *
 */
public class View extends JFrame
	{

		/**
	 * 
	 */
		private static final long serialVersionUID = 1L;
		private JPanel contentPane;
		private String filePath;
		private JButton btnExecute;
		private ButtonGroup group;
		public JCheckBox chckbxDebug;
		private JLabel runningLabel;
		public static JProgressBar progressBar;

		/**
		 * Create the frame.
		 */
		public View()
			{
				setResizable(false);
				setTitle(Strings.TITLE);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setBounds(100, 100, 541, 324);
				contentPane = new JPanel();
				contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
				setContentPane(contentPane);
				contentPane.setLayout(null);

				JRadioButton rdbtnQueueWithCutting = new JRadioButton(Strings.TEST1);
				rdbtnQueueWithCutting.setBounds(16, 73, 290, 23);
				contentPane.add(rdbtnQueueWithCutting);

				JRadioButton rdbtnFifoWithWait = new JRadioButton(Strings.TEST2);
				rdbtnFifoWithWait.setBounds(16, 99, 302, 23);
				contentPane.add(rdbtnFifoWithWait);

				JRadioButton rdbtnFifoWithPark = new JRadioButton(Strings.TEST3);
				rdbtnFifoWithPark.setBounds(16, 125, 302, 23);
				contentPane.add(rdbtnFifoWithPark);

				JRadioButton rdbtnFifoWithAwait = new JRadioButton(Strings.TEST4);
				rdbtnFifoWithAwait.setSelected(true);
				rdbtnFifoWithAwait.setBounds(16, 151, 302, 23);
				contentPane.add(rdbtnFifoWithAwait);

				group = new ButtonGroup();
				group.add(rdbtnQueueWithCutting);
				group.add(rdbtnFifoWithWait);
				group.add(rdbtnFifoWithPark);
				group.add(rdbtnFifoWithAwait);

				final JLabel lblNoFileSelected = new JLabel(Strings.NOFILESELECTED);
				lblNoFileSelected.setHorizontalAlignment(SwingConstants.CENTER);
				lblNoFileSelected.setBounds(10, 11, 525, 14);
				contentPane.add(lblNoFileSelected);

				JButton btnSelectInputFile = new JButton(Strings.SELECTINPUTFILE);
				btnSelectInputFile.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
							{
								try
									{
										openFilePicker();
										lblNoFileSelected.setText(filePath);
									} catch (FileNotFoundException e1)
									{
										sendError(e1.getMessage());
									}
							}
					});
				btnSelectInputFile.setBounds(341, 37, 142, 38);
				contentPane.add(btnSelectInputFile);

				btnExecute = new JButton(Strings.EXECUTEBUTTON);
				btnExecute.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
							{
								execute();
							}
					});
				btnExecute.setBounds(353, 151, 119, 43);
				contentPane.add(btnExecute);

				JPanel panel = new JPanel();
				panel.setBorder(new TitledBorder(null, Strings.SELECTTEST, TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel.setBounds(6, 33, 323, 161);
				contentPane.add(panel);

				chckbxDebug = new JCheckBox(Strings.DEBUGPANELNAME);
				chckbxDebug.setBounds(363, 206, 142, 23);
				contentPane.add(chckbxDebug);

				runningLabel = new JLabel("");
				runningLabel.setBounds(16, 225, 310, 16);
				contentPane.add(runningLabel);

				contentPane.setDropTarget(new DropTarget()
					{

						/**
						 * Add drag and drop to the interface for ease of testing
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unchecked")
						public synchronized void drop(DropTargetDropEvent evt)
							{
								try
									{
										evt.acceptDrop(DnDConstants.ACTION_COPY);
										List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

										filePath = droppedFiles.get(0).getAbsolutePath();
										btnExecute.setEnabled(true);
										lblNoFileSelected.setText(filePath);
									} catch (Exception ex)
									{
										ex.printStackTrace();
									}
							}
					});

				JLabel lblNewLabel = new JLabel(Strings.DROPMESSAGE);
				lblNewLabel.setBounds(373, 86, 132, 14);
				contentPane.add(lblNewLabel);

				progressBar = new JProgressBar();
				progressBar.setBounds(10, 252, 505, 14);
				contentPane.add(progressBar);

			}

		/**
		 * Create a FileGetter and then retrieve it's queue of users. 
		 * Requires that the user select an input file. 
		 * It creates a new Run (test run) with this list and the test type selected by the user.
		 */
		private void execute()
			{
				btnExecute.setEnabled(false);
				String selectedTest = getSelectedButtonText(group).substring(0, 1);
				if (!Strings.TESTARRAY.contains(selectedTest))
					{
						sendError(Strings.TESTNOTFOUND);
						return;
					}
				FileGetter f = new FileGetter(this);
				if (filePath == null)
					{
						sendError(Strings.SELECTINPUTFILE);//uh, select a file please
						return;
					}

				if (f.read(filePath))
					{
						Queue<User> q = f.getUl();
						if (q.size() > 0)
							{
								TestRun t = new TestRun(q, selectedTest, this);
								sendMessage(Strings.executingTestString(q.size(), selectedTest));
								Thread thread = new Thread(t);
								thread.setName(Strings.TESTNAME);
								thread.start();
							} else
							{
								sendError(Strings.EMPTYUSERLIST);
								return;
							}
					}
			}

		private String getSelectedButtonText(ButtonGroup buttonGroup)
			{
				for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();)
					{
						AbstractButton button = buttons.nextElement();

						if (button.isSelected())
							{
								return button.getText();
							}
					}
				return "4 ";//if all else, fails, we'll run the 'await()' test
			}

		/**
		 * Send a happy message to the user (green color)
		 * 
		 * @param message
		 */
		public void sendMessage(String message)
			{
				System.out.println(message);
				Growler g = new Growler(message, Color.green.darker().darker(), Color.green);
				g.setLocationRelativeTo(this);
				g.setVisible(true);
				runningLabel.setText(message);
			}

		/**
		 * Send a sad message to the user (red color)
		 * 
		 * @param message
		 */
		public void sendError(String message)
			{
				System.err.println(message);
				Growler g = new Growler(message, Color.red.darker().darker(), Color.red);
				g.setLocationRelativeTo(this);
				g.setVisible(true);
				btnExecute.setEnabled(true);
			}

		/**
		 * For diagnostic purposes, opens a window containing output in service order that is formatted exactly like the input is supposed to be. Use for comparisons of FIFO order
		 * 
		 * @param output
		 */
		public void debugWindow(String output)
			{
				DebugWindow o = new DebugWindow();
				o.setMessage(output);

			}

		/**
		 * Open a file chooser that the user can use to select the input file from program 0
		 * 
		 * @throws FileNotFoundException
		 */
		private void openFilePicker() throws FileNotFoundException
			{
				btnExecute.setEnabled(true);
				JFileChooser fileChooser = new JFileChooser(".");
				//System.out.println("Working Directory: " + fileChooser.getCurrentDirectory());
				/* We will allow multiple file selection */
				fileChooser.setMultiSelectionEnabled(true);
				int returnValue = fileChooser.showOpenDialog(null);
				/* We don't filter the list to text files, but that is an option */
				if ((returnValue == JFileChooser.APPROVE_OPTION) && (fileChooser != null))
					{
						//System.out.println("Selected Directory: " + fileChooser.getCurrentDirectory());
						File[] files = fileChooser.getSelectedFiles();
						/* get all the files you selected into a list */
						if (files != null)
							{
								File file = files[0];//but we will only pick the first one
								filePath = file.getAbsolutePath();
							}
					} else
					{
						filePath = null;
						sendError(Strings.NOFILESELECTED);
					}

			}

		/**
		 * A label to indicate the status of the run, so you don't feel like nothing is happening
		 * 
		 * @param string
		 */
		public void setRunningLabel(String string)
			{
				runningLabel.setText(string);
			}

		/**
		 * Set the progress on the progress bar
		 * 
		 * @param n
		 *            is the integer that indicates the progress
		 */
		public static void setProgressBarPosition(int n)
			{
				if (progressBar != null)
					{
						if (n > progressBar.getMaximum())
							{
								progressBar.setValue(progressBar.getMaximum());
								progressBar.repaint();
								return;
							}
						if (n < progressBar.getMinimum())
							{
								progressBar.setValue(progressBar.getMinimum());
								progressBar.repaint();
								return;

							}
						progressBar.setValue(n);
						progressBar.repaint();
					}
			}

		public void setEnableExecuteButton(boolean b)
			{
				btnExecute.setEnabled(b);
			}
	}
