import java.util.Arrays;

public class Strings
	{
		public final static String OUTPUTPATH = "BathroomOutput.txt";
		public final static String DEBUGOUTPUTPATH = "debugout.txt";
		public final static String LINESEPARATOR = "line.separator";
		public final static String DATASPLITTER = "\\s+";
		public final static String FEMALETYPE = "F";
		public final static String MALETYPE = "M";
		public final static String ZOMBIETYPE = "Z";
		public final static String QHEAD = "Q[head|";
		public final static String QTAIL = "|tail]";
		public final static String BHEAD = "B[";
		public final static String OPENBRACK = "[";
		public final static String CLOSEBRACK = "]";
		public final static String PIPE = " | ";
		public final static char TAB = '\t';
		public final static String TIME = "Time ";
		public final static String ENTER = " Enter ";
		public final static String ARRIVE = " Arrive ";
		public final static String EXIT = " Exit ";
		public final static String DASH = "-";

		public final static String DROPMESSAGE = "Or drop file here";
		public final static String DEBUGPANELNAME = "Debug Panel";
		public final static String SELECTTEST = "Select test";
		public final static String EXECUTEBUTTON = "Execute";
		public final static String TEST1 = "1 Near FIFO with cutting";
		public final static String TEST2 = "2 FIFO with park( ) and unpark( )";
		public final static String TEST3 = "3 FIFO with wait( ) and notify( )";
		public final static String TEST4 = "4 FIFO with await( ) and signal( )";
		public final static String TITLE = "Bathroom Problem";
		public final static String TESTNAME = "Test";

		public final static String EMPTYFILE = "The file is empty";
		public final static String BADFILEWRITE = "Error writing to file";
		public final static String STATEERROR = "Illegal state at queue index ";
		public final static String NOFILESELECTED = "No file selected";
		public final static String SELECTINPUTFILE = "Select input file";
		public final static String TESTNOTFOUND = "Test not found";
		public final static String EMPTYUSERLIST = "The list of users is empty.";
		public final static String BADFILELINE = "Bad file on line ";
		public final static String NEGATIVESLEEPTIME = "Negative SleepTime Encountered. **EXITING**";

		public final static java.util.List<String> TYPEARRAY = Arrays.asList("F", "M", "Z");
		public final static java.util.List<String> TESTARRAY = Arrays.asList("1", "2", "3", "4");

		public static String getRuntimeString(long time)
			{
				return ("Runtime: " + time + " ms");
			}

		public static String getOccupantsServedString(long time, int occupantsServed)
			{
				return (occupantsServed + " users have been processed in " + time + " ms");
			}

		public static String needToProcessString(int users)
			{
				return ("Need to process " + users + " users.");
			}

		public static String executingTestString(int users, String test)
			{
				return ("Executing test option " + test + " with " + users + " users.");
			}

		public static String bathroomString(int sameTypeCountFemale, int sameTypeCountMale, int sameTypeCountZombie)
			{
				return (BHEAD + sameTypeCountFemale + FEMALETYPE + PIPE + sameTypeCountMale + MALETYPE + PIPE + sameTypeCountZombie + ZOMBIETYPE + CLOSEBRACK);

			}

		public static String arrivalTimeString(long l, Type t)
			{
				return (TIME + l + ARRIVE + t + DASH + l);
			}

		public static String enterTimeString(long time, long l, Type t)
			{
				return (TIME + time + ENTER + t + DASH + l);
			}

		public static String exitTimeString(long time, long l, Type t)
			{
				return (TIME + time + EXIT + t + DASH + l);
			}

	}
