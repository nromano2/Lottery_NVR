import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * @author nicholasromano
 * @version 1.02
 * Final Project
 * CS131 Spring 2023
 */
public class MegaMillions extends JackpotLottery{
	private int[] userNumbers; //int array used to store the user played numbers
	private int[] winningNumbers; //int array used to store the randomly generated winning drawn numbers
	private long payout; //long variable is used to store the payout for each Megamillions ticket.
	
	Scanner scan = new Scanner(System.in);
	Random generator = new Random();
	NumberFormat currency = NumberFormat.getCurrencyInstance();
	DuplicateValueException duplicateException = new DuplicateValueException("A duplicate value has been enetered for this ticket. Make sure the non-Bonus balls are unique.");
	RangeValueException rangeValueException = new RangeValueException("A value has been enter that is outside the acceptable range of acceptable values.");
	
	/**
	 * Default constructor sets the userNumbers and winningNumbers int arrays to size 6, and the payout to 0.
	 * The default constructor also uses the super keyword to build off of the default constructor defined in the JackpotLottery class to 
	 * initialize the inherited mulitplierStatus, multiplier, jackpot variables.
	 */
	public MegaMillions()
	{
		super();
		this.userNumbers = new int[6];
		this.winningNumbers = new int[6];
		this.payout = 0;
	}//end default constructor
		
	/**
	 * The displayRules method briefly outlines the rules that the Mega Millions Lottery follows.
	 * @return the rules of the Mega Millions lottery
	 */
	public String displayRules()
	{
		StringBuilder rules = new StringBuilder("**************************************************************************************************************************************************************************************************************\n");
		rules.append("Mega Millions Lottery Rules \n");
		rules.append("Ticket Costs: \n");
		rules.append("\t * The standard cost of a single Mega Millions Lottery ticket is " + currency.format(2) + "\n");
		rules.append("\t * For an additional " + currency.format(1) + " for each ticket, there is an additional multiplier option that can be purchased which can multiply the winnings of the payout.\n\n");
		
		rules.append("Number Selection: \n");
		rules.append("\t * For a MegaMillions lottery ticket, 5 non-Megaball numbers are selected and a single Megaball bonus number. \n");
		rules.append("\t * For the 5 non-Megaball numbers, the range of valid numbers to play are the numbers 1-70. \n");
		rules.append("\t * For the single Megaball number, the range of valid numbers to play are the numbers 1-25. \n");
		rules.append("\t * Note: For each MegaMillions play, 5 different non-Megaball numbers must be played. \n\n");
		
		rules.append("Method Number Selection: \n");
		rules.append("* When selecting numbers, there are two options. \n");
		rules.append("\t 1) Manual Selection - the player manually selects the 6 numbers for the given ticket. \n");
		rules.append("\t 2) Quick Pick Selection - the 6 numbers for the given ticket is randomly generated. \n\n");
		
		rules.append("Payout Scenarios: \n");
		rules.append("Mega Millions Lottery Payouts are based on the number of matching numbers of the 5 non-Megaball numbers and whether the Megaball numbers match between the winning drawn numbers and the user played numbers. \n");
		rules.append("\t* 5 non-Megaball Number match and the Megaball numbers match: " + currency.format(getJackpotValue()) + "\n");
		rules.append("\t* 4 non-Megaball Number match and the Megaball numbers match: " + currency.format(10_000) + "\n");
		rules.append("\t* 3 non-Megaball Number match and the Megaball numbers match: " + currency.format(200) + "\n");
		rules.append("\t* 2 non-Megaball Number match and the Megaball numbers match: " + currency.format(10) + "\n");
		rules.append("\t* 1 non-Megaball Number match and the Megaball numbers match: " + currency.format(4) + "\n");
		rules.append("\t* 0 non-Megaball Number match and the Megaball numbers match: " + currency.format(2) + "\n\n");
		
		rules.append("\t* 5 non-Megaball Number match and the Megaball numbers don't match: " + currency.format(1_000_000) + "\n");
		rules.append("\t* 4 non-Megaball Number match and the Megaball numbers don't match: " + currency.format(500) + "\n");
		rules.append("\t* 3 non-Megaball Number match and the Megaball numbers don't match: " + currency.format(10) + "\n");
		rules.append("\t* 2 non-Megaball Number match and the Megaball numbers don't match: " + currency.format(0) + "\n");
		rules.append("\t* 1 non-Megaball Number match and the Megaball numbers don't match: " + currency.format(0) + "\n");
		rules.append("\t* 0 non-Megaball Number match and the Megaball numbers don't match: " + currency.format(0) + "\n");
		rules.append("**************************************************************************************************************************************************************************************************************\n");
		
		return rules.toString();
	}//end displayRules
	
	/**
	 * The isDuplicate method checks to see whether or not if a given number already belongs within an array.
	 * The isDuplicate method initializes a local variable called isDuplicateValue to false, then iterates through the array passed in the parameter list to check if the number in question already is in the array.
	 * If the number in question matches one of the values within the array, the method stops iterating over the array and changes the local isDuplicateValue to true.
	 * 
	 * @param numberArray - the array that the number is looking to be stored in
	 * @param number - the number that is looking to be added to the array.
	 * @return isDuplicateValue - local variable used to determine if the number in question already exists within the array. true means the number already is in the array, false the number is not within the array.
	 */
	public boolean isDuplicate(int[] numberArray, int number)
	{
		boolean isDuplicateValue = false;
		
		for(int i = 0; i < numberArray.length; i++)
		{
			if(numberArray[i] == number)
			{
				isDuplicateValue = true;
				break;
			}
		}
		
		return isDuplicateValue;
	}
	
	
	/**
	 * Accessor method that returns the userNumber array.
	 * @return userNumbers - the array used to store the user played numbers
	 */
	public int[] getUserNumbers()
	{
		return userNumbers;
	}//end getUserNumbers
	
	/**
	 * selectUserNumbers method allows for the user to manually enter the user played numbers.
	 * 
	 * @throws DuplicateValueException - exception if one of the 5 non-Megaball numbers have already been played in current set of 5 non-Megamilions numbers.
	 * @throws RangeValueException - exception if the user enters a non-Megaball number or Megaball number lies outside their respective ranges of playable numbers.
	 */
	public void selectUserNumbers()
	{	
		String numberStr; //used to store the user inputed values and allows for the catching of NumberFormatException.
		int number; //used to convert and store the numberStr String as a integer.
		
		//If the user is wanting to play the Mega Millions lottery multiple times, the proceeding line ensures that the set of 5 non-Megaball numbers on the next play doesn't throw a DuplicateValueException
		Arrays.fill(userNumbers, 0);
		
		
		//For loop used to iterate over the entry of 5 non-Megaball numbers by the user and body of the foor loop ensures that the input of the user meets the needed requirements of being non-duplicate and between 1 and 70.
		for(int i = 0; i < 5; i++)
		{
			while(true)
			{
				System.out.print("Enter non-Megaball number " + (i+1) + " (1-70): ");
				numberStr = scan.nextLine();
				
				try
				{
					number = Integer.valueOf(numberStr);
					
					if(1 <= number && number <= 70)
					{
						if(isDuplicate(userNumbers, number) == true)
						{
							throw duplicateException;
						}//end nested if statement
						
						else
						{
							userNumbers[i] = number;
							break;
						}//end nested else statement
						
					}//end if statement
					
					else
					{
						throw rangeValueException;
					}//end else statement
					
				}//end try block
				
				catch(NumberFormatException exception)
				{
					System.out.println("Invalid Input! \'" + numberStr + "\' is not an acceptable value. Make sure that you are selecting a unique integer that is between 1 and 70. \n");
				}//end catch block
				
				catch(DuplicateValueException exception)
				{
					System.out.println("Invalid input. It appears that this number has already been played for this ticket. Please enter a new unique non-Megaball number.");
				}//end catch block
				
				catch(RangeValueException exception)
				{
					System.out.println("Invalid input. It appears that the number you have played is outside the range of playable numbers. Make sure that you are entering numbers between 1 and 70.");
				}//end catch block
				
			}//end while statement
			
		}//end for loop
		
		//Following line of code, sorts the five numbers that had just been added in ascending order. Used to make the printed ticket look a little cleaner.
		Arrays.sort(userNumbers, 0, 5);
	
		
		/*
		 * The following while loop ensures that the user input of the Megaball number follows the proper requirements (user input is an integer and said integer falls between 1 ans 25 inclusive),
		 * The while loop is broken once the user input for the single Megaball number meets said requirements.
		 */
		while(true)
		{
			System.out.print("Enter the Megaball number (1-25): ");
			numberStr = scan.nextLine();
			try
			{
				number = Integer.valueOf(numberStr);
				
				if(1 <= number && number <= 25)
				{
					userNumbers[5] = number;
					break;
				}//end if statement
				
				else
				{
					throw rangeValueException;
				}//end else statement
				
			}//end try block
			
			catch(NumberFormatException exception)
			{
				System.out.println("Invalid Input! \'" + numberStr + "\' is not an acceptable value. Make sure that you are entering an integer value that is between 1 and 25. \n");
			}//end catch block
			
			catch(RangeValueException exception)
			{
				System.out.println("Invalid input. It appears that the number you have played is outside the range of playable numbers. Make sure that you are entering numbers between 1 and 25.");
			}//end catch block
			
		}//end while loop
		
	}//end selectUserNumbers
	
	/**
	 * If the user is wanting to play the quick pick option, the generateUserNumbers method randomly selects the userNumbers for the player and stores the numbers into the userNumbers array.
	 */
	public void generateUserNumbers()
	{
		//If the user is wanting to play the Mega Millions lottery multiple times, the proceeding line ensures that the set of 5 non-Megaball numbers on the next play doesn't throw a DuplicateValueException
		Arrays.fill(userNumbers, 0);
		
		int number; //local variable used to store the generated number.
		
		for(int i = 0; i < 5; i++)
		{
			while(true)
			{
				number = generator.nextInt(1, 71); 
				
				if(isDuplicate(userNumbers, number) == false)
				{
					userNumbers[i] = number;
					break;
				}//end if statement
				
			}//end while loop
			
		}//end for loop
		
		//Following line of code, sorts the five numbers that had just been added in ascending order. Used to make the printed ticket look a little cleaner.
		Arrays.sort(userNumbers, 0, 5);
		
		//A Megaball number is generated and added to the end of the userNumbers array.
		userNumbers[5] = generator.nextInt(1, 26);
		
	}//end generateUserNumbers
	
	/**
	 * Accessor method that returns the array containing the winning numbers
	 * @return winningNumbers
	 */
	public int[] getWinningNumbers()
	{
		return winningNumbers;
	}//end getWinningNumbers
	
	/**
	 * The generateWinningNumbers method pseudoranomly generates the winning numbers and stores them in the winningNumber array. 
	 */
	public void generateWinningNumbers()
	{	
		int number; //local variable used to store the generated number.
		
		for(int i = 0; i < 5; i++)
		{	
			while(true)
			{
				number = generator.nextInt(1, 71);
				
				if(isDuplicate(winningNumbers, number) == false)
				{
					winningNumbers[i] = number;
					break;
				}//end if statement
				
			}//end while loop
			
		}//end for loop
		
		//Following line of code, sorts the five numbers that had just been added in ascending order. Used to make the printed ticket look a little cleaner.
		Arrays.sort(winningNumbers, 0, 5);
		
		//A Megaball number is generated and added to the end of the winningNumbers array.
		winningNumbers[5] = generator.nextInt(1, 26);
		
	}//end gnerateWinningNumbers

	
	/**
	 * The matchingNumbers method accepts two int arrays, compares the first 5 elements of each array, and returns the number of matching elements.
	 * The matchingNumber will be used to compare the non-Megaball numbers of the arrays passed through the parameter list.
	 * 
	 * @param userNumArray - array containing the user numbers 
	 * @param winningNumArray - array containing the winning numbers 
	 * @return numMatches - local variable that stores the number of matching non-Megaball numbers 
	 */
	public int matchingNumbers(int[] userNumArray, int[] winningNumArray)
	{
		int numMatches = 0;
		
		for(int userIndex = 0; userIndex < 5; userIndex++)
		{
			for(int winningIndex = 0; winningIndex < 5; winningIndex++)
			{
				if(userNumArray[userIndex] == winningNumArray[winningIndex])
				{
					numMatches++;
				}//end if statement
				
			}//end nested for loop
			
		}//end for loop
		
		return numMatches;
		
	}//end matchingNumbers
	
	/**
	 * The areBonusNumbersMatching method accepts two int arrays, compares the last element of each array, and returns a boolean value representing whether the last elements are matching.
	 * The areBonusNumbersMatching will be used to compare the Megaball numbers of the arrays passed through the parameter list.
	 * 
	 * @param userNumArray - array containing the user numbers 
	 * @param winningNumArray - array containing the winning numbers 
	 * @return areBonusNumbersMatching - local boolean variable representing whether the last elements are matching.
	 */
	public boolean areBonusNumbersMatching(int[] userNumArray, int[] winningNumArray)
	{
		boolean areBonusNumbersMatching;
		
		if(userNumArray[5] == winningNumArray[5])
		{
			areBonusNumbersMatching = true;
		}//end if statement
		
		else
		{
			areBonusNumbersMatching = false;
		}//end else statement
		
		return areBonusNumbersMatching;
		
	}//end areBonusNumbersMatching
	
	/**
	 * Accesor method that returns the value of the payout that the user has earned.
	 * Note: Multipliers cannot be used if the user has hit the jackpot. Therefore the multiplier is only used to multiplier the users payout if they did not hit the jackpot.
	 * @return the value of the payout that the user has earned.
	 */
	public long getPayout()
	{
		if(payout != getJackpotValue())
		{
			return getMultiplierValue() * payout;
		}//end if statement
		
		else
		{
			return payout;
		}//end else statement
		
	}//end getPayout
	
	/**
	 * The calcPayout method accepts the arrays storing the user played numbers and winning drawn numbers, 
	 * and sets the payout that the user receives by invoking the areMegaballsMatching and matchingNumbers method.
	 * 
	 * @param userNumArray - the current userNumbers array
	 * @param winningNumArray - the winningNumbers array
	 */
	public void calcPayout(int[] userNumArray, int[] winningNumArray)
	{
		if(areBonusNumbersMatching(userNumArray, winningNumArray) == true)
		{
			if(matchingNumbers(userNumArray, winningNumArray) == 5)
			{
				payout = getJackpotValue();
			}//end if statement
			
			else if(matchingNumbers(userNumArray, winningNumArray) == 4)
			{
				payout = 10_000;
			}//end else if statement
			
			else if(matchingNumbers(userNumArray, winningNumArray) == 3)
			{
				payout = 200;
			}//end else if statement
			
			else if(matchingNumbers(userNumArray, winningNumArray) == 2)
			{
				payout = 10;
			}//end else if statement
			
			else if(matchingNumbers(userNumArray, winningNumArray) == 1)
			{
				payout = 4;
			}//end else if statement
			
			else
			{
				payout = 2;
			}//end else statement
			
		}//end if statement
		
		else
		{
			if(matchingNumbers(userNumArray, winningNumArray) == 5)
			{
				payout = 1_000_000;
			}//end if statement
			
			else if(matchingNumbers(userNumArray, winningNumArray) == 4)
			{
				payout = 500;
			}//end else if statement
			
			else if(matchingNumbers(userNumArray, winningNumArray) == 3)
			{
				payout = 10;
			}//end else if statement
			
			else if(matchingNumbers(userNumArray, winningNumArray) == 2)
			{
				payout = 0;
			}//end else if statement
			
			else if(matchingNumbers(userNumArray, winningNumArray) == 1)
			{
				payout = 0;
			}//end else if statement
			
			else
			{
				payout = 0;
			}//end else statement
			
		}//end else statement
		
	}//end calcPayout
	
	/**
	 * The generateTicket method creates a ticket using a stringBuilder object.
	 * The ticket includes the multiplier value, jackpot value, the costs of the ticket, the numbers played by the user, 
	 * the winning numbers generated, the payout of the ticket, and the return based on the ticket costs and payout.
	 * 
	 * @param userNum - the current userNumbers array
	 * @param winningNum - the winningNumbers array
	 * 
	 * @return A string representing a ticket.
	 */
	public String generateTicket(int[] userNum, int[] winningNum)
	{
		StringBuilder ticket = new StringBuilder("============================================= \n");
		ticket.append("Mega Millions Lottery Ticket \n\n");
		ticket.append("Multiplier: " + getMultiplierValue() + "x \n");
		ticket.append("Estimated Jackpot: " + currency.format(getJackpotValue()) + "\n");
		ticket.append("Ticket Cost: " + currency.format(getTicketCost()) + "\n\n");
		ticket.append("Numbers Played: " + "\n");
		
		//Displaying the numbers the user played
		for(int i = 0; i < 6; i++)
		{
			ticket.append(userNum[i] + " ");
		}
		
		ticket.append("\nWinning Numbers Drawn: \n");
		
		//Displaying the winning numbers
		for(int i = 0; i < 6; i++)
		{
			ticket.append(winningNum[i] + " ");
		}
		
		calcPayout(userNum, winningNum);
		
		ticket.append("\n\nPayout: " + currency.format(getPayout()) + "\n");
		ticket.append("Return: " + (currency.format(getPayout() - getTicketCost())) + "\n");
		ticket.append("=============================================");
		
		return ticket.toString();
	}//end generateTicket
	
	/**
	 * numPlays method prompts the user for how many tickets that they are looking to play.
	 * Method returns the number of tickets that they are going to play
	 * @return numberOfPlays - the number of tickets that the user is going to play
	 */
	public int numPlays()
	{
		String numPlays;
		int numberOfPlays;
		
		while(true)
		{
			System.out.print("How many MegaMillions tickets are you looking to play?: ");
			numPlays = scan.nextLine();
			
			try 
			{
				numberOfPlays = Integer.valueOf(numPlays);
				
				if(numberOfPlays < 0)
				{
					throw rangeValueException;
				}//end if statement
				
				else 
				{
					break;
				}//end else statement
				
			}//end try block
			
			catch(NumberFormatException e)
			{
				System.out.println("Invalid input: " + numPlays + ". The response needed is a number greater than or equal to 0.");
			}//end catch block
			
			catch(RangeValueException e)
			{
				System.out.println("Invalid input: " + numPlays + ". The response needed is a number greater than or equal to 0.");
			}//end catch block
			
		}//end while loop
		
		return numberOfPlays;
		
	}//end numPlays
	
	/**
	 * The play method prompts the user through playing a given number of Mega Millions Lottery tickets.
	 * 
	 * @param numPlays - the number of tickets that the user is wanting to play.
	 * @throws DuplicateValueException - exception if one of the 5 non-Megaball numbers have already been played in current set of 5 non-Megamilions numbers.
	 * @throws RangeValueException - exception if the user enters a non-Megaball number or Megaball number lies outside their respective ranges of playable numbers.
	 */
	public void play(int numPlays)
	{
		if(numPlays > 0)
		{
			int multiplierPlayed; //used to store whether or not the user has selected to play the optional multiplier encoded as a 0 or 1.
			int selectionMethod; //used to store the selection method chosen by the user encoded as a 0 or 1.
			String input; //used to store the users response to the multiplier and selection method prompts.
			int totalPayout = 0; //used to store the payout of all Mega Millions Lottery tickets. Variable initialized to 0.
			int totalCost = 0; //used to store the Cost of all Mega Millions Lottery tickets. Variable initialized to 0.
			String ticket; //used to store the current ticket  before it is added to the ticket array
			String[] ticketArray = new String[numPlays]; //user to store each Mega Millions ticket played by the user.
			
			/*
			 * 1 set of winning numbers are generated and stored in an int array called winningNumberArray. 
			 * This is the set of numbers that is going to be compared to each of sets of user numbers played.
			 */
			generateWinningNumbers();
			
			//For block iterates through the code that generates a Mega millions lottery ticket for the number of plays that the user is wanting to play.
			for(int i = 0; i < numPlays; i++)
			{
				System.out.println("\nMega Millions Lottery Ticket [" + (i+1) + " out of " + (numPlays) + "]"); //Displays what ticket number that the user is currently on
				
				/*
				 * While loop contains the prompt of whether the user is wanting to play the optional multiplier. 
				 * The while loop is broken once the user enters inputs either a valid input, which is an integer value corresponding to either not playing the multiplier [0] or playing the multiplier [1].
				 */
				while(true)
				{
					System.out.print("Would you like to play the multiplier to have a chance to multiply your payout by playing the optional multiplier? \n{Enter 0 to not play the optional multiplier, otherwise enter 1 to play the optional multiplier}: ");
					input = scan.nextLine(); 
					
					try
					{
						multiplierPlayed = Integer.parseInt(input);
						
						if(multiplierPlayed == 0)
						{
							setMultiplierStatus(false);
							generateMultiplierValue(); //With the user not playing the optional multiplier value that is generated will be 1.
							break;
						}//end if statement
						
						else if(multiplierPlayed == 1)
						{
							setMultiplierStatus(true); 
							generateMultiplierValue(); //With the user playing the optional multiplier value that is generated will be a integer between 2 and 5.
							break;
						}//end else-if statement
						
						else
						{
							throw rangeValueException; 
						}//end else statement
						
					} //end try block
					
					catch(NumberFormatException exception)
					{
						System.out.println("Invalid Input! \'" + input + "\' is not a valid input. Make sure the input that you are entering meet the specific requirements asked of.");
					}//end catch block
					
					catch(RangeValueException exception)
					{
						System.out.println("Invalid Input! Make sure that you are entering 0 if you are choosing not to play the multiplier, or entering 1 if you do choose to play the multiplier.");
					}//end catch block
					
				}//end while loop
				
				/*
				 * While loop contains the prompt of whether the user is wanting to manually select their numbers or have their numbers generated for the current ticket
				 * The while loop is broken once the user enters inputs either a valid input, which is an integer value corresponding to either manual selection of numbers [0] or generated with the quick-pick option [1].
				 */
				while(true)
				{
					System.out.print("Would you like to select your numbers or have them randomly selected for you? \n{Enter 0 for manual selection. Enter 1 for quick-pick selection: ");
					input = scan.nextLine();
					
					try 
					{
						selectionMethod = Integer.parseInt(input);
						
						if(selectionMethod == 0)
						{
							selectUserNumbers();
							break;
						}//end if statement
						
						else if(selectionMethod == 1)
						{
							generateUserNumbers();
							break;
						}//end else-if statement
						
						else
						{
							throw rangeValueException;
						}//end else statement
						
					}//end try block
					
					catch(NumberFormatException exception)
					{
						System.out.println("Invalid Input! \'" + input + "\' is not a valid input. Make sure the input that you are entering meet the specific requirements asked of.\n");
					}//end catch block
					
					catch(RangeValueException exception)
					{
						System.out.println("Invalid Input! Make sure that you are entering 0 if you are wanting to select the Mega Millions numbers yourself or entering 1 if you are wanting to have the your numbers randomly generated for you.");
					}//end catch block
					
				}//end while loop
				
				ticket = generateTicket(getUserNumbers(), getWinningNumbers()); //generateTicket method is invoked and the userNumberArray and winningNumberArray is passed in to create a lottery ticket, which is stored in the ticket variable
				ticketArray[i] = ticket; //The ticket that is just generated is stored into a string array to hold all the Mega Millions tickets that the user is wanting to play.
				totalPayout += getPayout(); //Ticket payout is added to the totalPayout variable used to keep track of the overall payout of all Mega Millions tickets played
				totalCost += getTicketCost(); //Ticket cost is added to the totalCost variable used to keep track of the overall costs of all Mega Millions tickets played
			}//end for loop
			
			//Prints each Mega Millions ticket played
			for(String element : ticketArray)
			{
				System.out.println(element);
			}
			
			System.out.println("Total payout for MegaMillion Tickets: " + currency.format(totalPayout));
			System.out.println("Total return for MegaMillion Tickets: " + currency.format(totalPayout - totalCost) + "\n");
		}//end if statement
		
		else
		{
			System.out.println("Mega Millions Lottery not played.\n");
		}//end else statement
		
	}//end play
	
}//end class
