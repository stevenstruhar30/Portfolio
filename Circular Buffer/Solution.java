import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * Rules for my circular buffer:
 * 1. Always know where the oldest, newest, and next elements are.
 * 2. Always know how many elements are in the array and how many it can hold
 * 3. Always move from the left to the right when inserting elements. It's hard enough to keep track moving in one direction,
 *    So don't even think about going backwards.
 * 4. Only ever write into the index of next. This is always pointing to where the next thing will go
 * 5. If the array is full, next == oldest, but only if the array is full. this is the circular part, we loop back around.
 * 6. If the array is not full, the next spot is the one after wherever it was last
 * 7. You must wrap around the end man, you must always wrap around the end when you get there. This is what makes the buffer circular.
 * 8. No need to block, methinks you can add and remove as fast as you like.
 */
public class Solution
	{
		private Object[] elements;// Datums
		private int oldest = 0;// track the oldest element
		private int newest = 0;// track the newest element
		private int next = 0;// track where the next element will go
		private int occupiedSlots = 0;// track the number of inserted elements
		private static boolean debug = false;
		private final int maximumSize = 10000;
		private final int defaultSize = 128;

		public Solution(int size)
			{
				if (size < 0 || size > maximumSize)// check the input list size
													// request
					{
						elements = new Object[defaultSize];
					} else
					{
						elements = new Object[size];
					}
			}

		public Solution()
			{
				elements = new Object[defaultSize];
			}

		public void add(int num, Object[] o)
			{
				if (num != o.length)// this shouldn't happen because main won't
									// call this unless it has collected the
									// right number of
									// entries, but let's be defensive.
					{
						System.out.println("Not enough elements have been collected to add..");
						return;
					}
				if (elements.length > 0)// if the list is longer than 0 elements
					{
						for (Object item : o)// for each item in the list to add
							{
								Boolean emptySlot = (!isFull());
								// we want to know if we are adding to an empty
								// slot which we will be if, the array is not
								// full
								elements[next] = item;// add the item
								if (!isFull())// increment the count of elements
									{
										occupiedSlots++;// one more item in
														// there now
									} else
									{
										occupiedSlots = elements.length;// if
																		// the
																		// array
																		// just
																		// filled
																		// up...
									}
								newest = next;// move the newest pointer
								if (!emptySlot)// if the array was full when we
												// started
									{
										oldest = increment(oldest); // increment
																	// the
																	// oldest
																	// element
										// pointer cause the oldest one
										// is now the newest one
										next = oldest;// increment the next
														// pointer to be ready
														// to
														// overwrite the oldest
														// element
									} else
									// otherwise there was room for the element,
									// so we don't have to
									// increment the oldest item. It's still
									// here.
									{
										next = increment(next);// increment the
																// next pointer
									}
							}
					}
				if (debug)
					{
						print();
					}

			}

		private int increment(int it)// we only need increment, because we only
										// move
										// to the right, we never ever move
										// back.
			{
				int value = it + 1;
				if (value == elements.length)// wrap it around the end of the
												// array
					{
						return 0;
					}
				return value;
			}

		public void remove()
			{
				if (!isEmpty()) // if the buffer is not empty
					{
						if (elements[oldest] != null)
							{
								elements[oldest] = "*" + elements[oldest] + "*";
							}
						occupiedSlots--;// one fewer element in the list
						if (isEmpty())// it's empty so set the buffer to empty
										// and reset all the pointers
							{
								occupiedSlots = 0;
								next = 0;
								oldest = 0;
								newest = 0;
								if (debug)
									{
										print();
									}
								return;
							} else
							{
								oldest = increment(oldest);// the list still
															// isn't
															// empty, so
															// increment the
															// oldest element
															// pointer so it
															// points to the
															// oldest included
															// element
								if (debug)
									{
										print();
									}
							}
					}

			}

		public void remove(int num)// Remove n items by calling remove n times
			{
				for (int i = 0; i < num; i++)
					{
						remove();
					}

			}

		private boolean isEmpty()
			{
				return (occupiedSlots <= 0);// If there are no used elements,
											// the array
											// is empty

			}

		private boolean isFull()
			{
				return (occupiedSlots == elements.length);// if all the slots
															// are
															// used...

			}

		public void print()// for debugging purposes.
			{
				System.out.println("Array Length: " + elements.length);
				System.out.println("Elements Used: " + occupiedSlots);
				for (Object item : elements)
					{
						String element = (String) item;
						System.out.print("[" + element + "]");
					}
				System.out.println();
				System.out.println("oldest: " + (oldest));
				System.out.println("newest: " + (newest));
				System.out.println("next: " + (next));
			}

		public void out()
			{
				if (!isEmpty())// don't worry about empty lists.
					{
						for (int i = oldest; i != newest; i = increment(i))// start
																			// at
																			// the
						// oldest element
						// and trace to
						// the newest by
						// Incrementing
						// only
							{
								System.out.println(elements[i]);
							}
						System.out.println(elements[newest]);// and don't forget
																// the newest
																// element last
					}
			}

		public int hashCode(String key)
			{
				int code = 0;
				for (int i = 0; i < key.length(); i++)
					{
						code = (code << 4) + key.charAt(i);
						code = (code & 0x0fffffff) ^ ((code & 0xf0000000) >> 24);
					}
				return code;
			}

		public static void main(String[] args)
			{
				String command = "";
				Solution solution = null;
				String inputLine = null;
				System.out.println("Enter H for help");
				while (!command.equalsIgnoreCase("Q"))
					{
						command = "";
						inputLine = null;
						BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
						try
							{
								inputLine = br.readLine();
							} catch (IOException e)
							{
								e.printStackTrace();
							}
						if (inputLine != null)
							{
								int numItems = 0;
								if (inputLine.indexOf(" ") != -1)
									{
										String[] inputArray = inputLine.split(" ");
										command = inputArray[0].toUpperCase();
										if (inputArray.length > 1)
											{

												try
													{
														numItems = Integer.parseInt(inputArray[1]);
													} catch (NumberFormatException e)
													{
														System.out.println("Bad input.");
													}
											}

									} else
									{
										try
											{
												int solSize = Integer.parseInt(inputLine);

												solution = new Solution(solSize);
											} catch (NumberFormatException nfe)
											{
												command = inputLine.toUpperCase();
											}

									}
								switch (command)
									{
									case "H":
										{
											System.out
													.println(" First type a number for array size and press enter. \n A + a number will allow you to add that number of words. \n R + a number removes that number of words oldest first. \n Q to quit \n D to see debug printout. \n L for list of current words.");
											break;
										}
									case "Q":
										{
											System.exit(0);
											break;
										}
									case "A":
										{
											if (solution != null)
												{
													if (!(numItems < 0))
														{
															String[] insertItems = new String[numItems];
															for (int i = 0; i < numItems; i++)
																{
																	try
																		{
																			insertItems[i] = (br.readLine());
																		} catch (IOException e)
																		{
																			// TODO
																			// Auto-generated
																			// catch
																			// block
																			e.printStackTrace();
																		}
																}
															solution.add(numItems, insertItems);
															break;
														}
												}
										}
									case "R":
										{
											if (solution != null)
												{
													solution.remove(numItems);
													break;
												}
										}
									case "L":
										{
											if (solution != null)
												{
													solution.out();
													break;
												}
										}
									case "D":
										{
											if (solution != null)
												{
													// debug = true;
													solution.print();
													break;
												}
										}
									}
							}
					}
			}
	}
