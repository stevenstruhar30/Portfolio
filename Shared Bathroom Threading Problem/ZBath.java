import java.util.*;
import javax.swing.*;
import java.io.*;

// Generate Males, Females, and Zombies going to the bathroom.
// Write into a file that you name as you wish.

public class ZBath
	{

		public static void main(String[] args) throws FileNotFoundException
			{

				String outputFileName = JOptionPane.showInputDialog("Enter output file name");
				if (outputFileName != null)
					{
						java.io.File outputFile = new java.io.File(outputFileName);
						if (outputFile.exists())
							{ // For retests
								outputFile.delete();
							}
						// Create output file.
						java.io.PrintWriter output = new java.io.PrintWriter(outputFile);

						final int endTime = 500000; // end time for arrivals
						int arv = 0; // Starting arrival time

						// Must intiialize service time and type because they are only done within a switch statement,
						// and not default case.  Compiler doesn't like it if I don't do this.
						int svc = 0;
						char type = 'X';

						Random r = new Random();

						// Generate arrivals.
						while (arv <= endTime)
							{

								// Next arrival time, Poisson arrivals, mean = 200
								arv += Math.rint((-1.0 * Math.log(r.nextDouble())) * 200);

								int randomType = r.nextInt(3); // 0 = F, 1 = M, 2 = Z

								// Random standard normal Gaussian for calculating service time.
								double svctemp = r.nextGaussian();

								switch (randomType)
									{
									case 0: // female, scale service time to be N(160,40)
										type = 'F';
										svc = (int) Math.round(svctemp * 40 + 160);
										break;
									case 1: // male, scale service time to be N(100,25)
										type = 'M';
										svc = (int) Math.round(svctemp * 25 + 100);
										break;
									case 2: // zombie, scale service time to be N(200,50)
										type = 'Z';
										svc = (int) Math.round(svctemp * 50 + 200);
										break;
									default:
										System.out.println("Semething is seriously wrong.");
										System.exit(0);
									}

								// Very outside chance that service time will be 0 or negative, 4 standard deviations below mean.
								// In this case, make the service time 1.
								svc = Math.max(svc, 1);
								if (arv <= endTime)
									{
										output.print(type + " ");
										output.format("%5d  %3d%n", arv, svc);
									}
							}
						output.close();
					}
				System.exit(0);
			}
	}
