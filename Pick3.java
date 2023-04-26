import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * @author nicholasromano
 * @version 1.01
 * Final Project
 * CS131 Spring 2023
 */

public class Pick3 extends SelectionLottery{
	private int[] userNumber; //used to store the digits of the current number that the user is playing
	private int[] winningNumber; //used to store the digits of the winning number 
	private ArrayList<int[]> permutationsList; //used to store the permutations of the user number
	private int payout; //used to store the payout that the user receives from their ticket.
	
	Scanner scan = new Scanner(System.in);
	Random generator = new Random();
	NumberFormat currency = NumberFormat.getCurrencyInstance();
	RangeValueException rangeValueException = new RangeValueException("Invalid Input. The value entered appears to be outside the range of acceptable values.");
	
	/**
	 * Default constructor sets userNumber and winningNumber arrays to size 3, initializes the permutationsList ArrayList,
	 * and initializes the payout to 0.
	 */
	public Pick3()
	{
		super();
		this.userNumber = new int[3];
		this.winningNumber = new int[3];
		this.permutationsList = new ArrayList<int[]>();
		this.payout = 0;
	}//end default constructor
	
	/**
	 * The displayRules method displays the rules of the Pick 3 Lottery allowing the user to understand to the rules of the lottery.
	 * @return rules - a string containing the rules to the Pick 3 lottery
	 */
	public String displayRules()
	{
		StringBuilder rules = new StringBuilder("**************************************************************************************************************************************************************************************************************\n");
		rules.append("Pcik 3 Lottery Rules: \n\n");
		rules.append("Betting Types: \n");
		rules.append("* Choose the type of bet you are playing. Betting Type options include: \n\n");
		
		for(BettingType type : BettingType.values())
		{
			rules.append("\t* " + type + "\n");
		}
		
		rules.append("\n* Straight Bets: requires that the 3-digit winning number that is drawn must excatly match the 3-digit number you play.");
		rules.append("\n* Box Bets: requires that the winning 3-digit number match any one of the permutations of the 3-digit number that you play.");
		rules.append("\n* StraightBox Bets: requires that the 3-digit winning number that is drawn must excatly match the 3-digit number you play or match one of the possible permutations of the 3-digit number that you play.");
		rules.append("\n* Front Pair Bets: requires that the 3-digit winning number that is drawn must excatly match the first two digits of the 3-digit number you play.");
		rules.append("\n* Back Pair Bets: requires that the 3-digit winning number that is drawn must excatly match the last two digits of the 3-digit number you play.");
		rules.append("\n* Split Pair Bets: requires that the 3-digit winning number that is drawn must excatly match the first and last digit of the 3-digit number you play.");
		
		rules.append("\n\nWager Amount: \n");
		rules.append("* Choose the amount you want to wager. Valid wager amounts include $1, $2, $3, $4, $5.");
		
		rules.append("\n\nNumber Selection: \n");
		rules.append("* You will enter the digits of the number that you are playing. \n");
		rules.append("* Valid digits include any number between 0 and 9 inclusive.");
		rules.append("* Note: for Box and StraightBox bets, the number you select must have more than 1 unique digits (ex. 111 is not valid) \n");
		
		rules.append("\n\nPayout Scenarios: \n");
		rules.append("*Winning Striaght Bet Payout: " + currency.format(600.00) + "\n");
		
		rules.append("\nBox Bet Payout: \n");
		rules.append("* Number played has 3 different digits: " + currency.format(100.00) + "\n");
		rules.append("* Number played has 2 of the same digits alongside 1 other different digit (ex. 334): " + currency.format(200.00) + "\n");
		
		rules.append("\n\nStraightBox Bet Payout \n");
		rules.append("* If number played matches the drawn number in the exact order. \n");
		rules.append("\t* Number played has 3 different digits: " + currency.format(350.00) + "\n");
		rules.append("\t* Number played has 2 of the same digits alongside 1 other different digit (ex. 334): " + currency.format(400.00) + "\n");
		
		rules.append("\n\n* If number played doesn't match the drawn number in the exact order. \n");
		rules.append("\t* Number played has 3 different digits:: " + currency.format(50.00) + "\n");
		rules.append("\t* Number played has 2 of the same digits alongside 1 other different digit (ex. 334): " + currency.format(100.00) + "\n");
		
		rules.append("\nPair Bet Payouts: \n");
		rules.append("* FrontPair, SplitPair, and BackPair Payouts: " + currency.format(60.00) + "\n");
		rules.append("**************************************************************************************************************************************************************************************************************\n");

		
		return rules.toString();
	
	}//end displayRules
	
	/**
	 * distinctNumbers method accepts an array in through the parameter list to see how many distinct numbers are within that array.
	 * distinctNumners method loops through the array and adds the unique numbers to a local arraylist
	 * 
	 * @param array - the array to determine the number of distinct numbers
	 * @return size of arraylist used to hold the distinct numbers.
	 */
	public int distinctNumbers(int[] array)
	{
		ArrayList<Integer> distinct = new ArrayList<>();
		
		for(int i = 0; i < array.length; i++)
		{
			if(!distinct.contains(array[i]))
			{
				distinct.add(array[i]);
			}
		}
		
		return distinct.size();
	}//end distinctNumbers
	
	/**
	 * Accessor method, the getUserNumber method returns the array storing the digits making up the number that the user is playing
	 * @return userNumber - the array storing the values of the users current play
	 */
	public int[] getUserNumber()
	{
		return userNumber;
	}//end getUserNumber
	
	/**
	 * SelectUserNumber method allows for the user to manually enter the 3 digits of the number that they are wanting to play.
	 * 
	 * A RangeValueException are thrown and caught if the user tries to input a digit that is outside the range of 0 to 9 when prompted for a digit.
	 * A NumberFormatException if the user tries to enter a non-integer value when prompted for a digit.
	 */
	public void selectUserNumber()
	{
		String numStr; //used to store the user inputed values.
		int number; //used to convert and store the numberStr String as a integer.
		
		//Betting type has to be accounted for, especially box and straight box bets since for these two bets the user cannot enter a number with the same 3 digits. If this happens, the user will be essentially playing a straight bet instead of a box or straighbox bet.
		if(betType.equals(BettingType.Box) || betType.equals(BettingType.StraightBox))
		{
			while(true)
			{
				for(int i = 0; i < 3; i++)
				{
					while(true)
					{
						System.out.print("Enter digit number " + (i+1) + " (0-9): ");
						numStr = scan.nextLine();
						
						try
						{
							number = Integer.valueOf(numStr);
							
							if(0 <= number && number <= 9)
							{
								userNumber[i] = number;
								break;
							}//end if statement
							
							else
							{
								throw rangeValueException;
							}//end else statement
							
						}//end try block
						
						catch(NumberFormatException e)
						{
							System.out.println("Invalid input! \'" + numStr + "\' is not an integer value. Make sure you are entering an integer value between 0 and 9.");
						}//end catch statement
						
						catch(RangeValueException exception)
						{
							System.out.println("Invalid input. It appears that the number you have played is outside the range of playable numbers. Make sure that the digits you are entering are numbers between 0 and 9.\n");
						}//end catch statement
						
					}//end while loop
					
				}//end for loop
				
				if(distinctNumbers(userNumber) > 1)
				{
					break;
				}//end if statement
				
				else
				{
					System.out.println("For " + betType + " bets, the number you play must have more than 1 distinct number. " + userNumber[0] + "" + userNumber[1] + "" + userNumber[2] + " doesn't meet this condition. Please enter a new number.\n");
				}//end else statement
				
			}//end while loop
			
		}//end if statement
		
		else
		{
			for(int i = 0; i < 3; i++)
			{
				while(true)
				{
					System.out.print("Enter digit number " + (i+1) + " (0-9): ");
					numStr = scan.nextLine();
					
					try
					{
						number = Integer.valueOf(numStr);
						
						if(0 <= number && number <= 9)
						{
							userNumber[i] = number;
							break;
						}//end if statement
						
						else
						{
							throw rangeValueException;
						}//end else statement
						
					}//end try block
					
					catch(NumberFormatException exception)
					{
						System.out.println("Invalid input! \'" + numStr + "\' is not an integer value. Make sure you are entering an integer value between 0 and 9.");
					}//end catch statement
					
					catch(RangeValueException exception)
					{
						System.out.println("Invalid input. It appears that the number you have played is outside the range of playable numbers. Make sure that the digits you are entering are numbers between 0 and 9.\n");
					}//end catch statement
					
				}//end while loop
				
			}//end for loop
			
		}//end else statement
		
	}//end selectUserNumbers
	
	/**
	 * The generateUserNumbers allows for the quick pick option of selection where the digits of the user number is to be generated for the user
	 */
	public void generateUserNumber()
	{
		if(betType.equals(BettingType.Box) || betType.equals(BettingType.StraightBox))
		{
			while(true)
			{
				for(int i = 0; i < 3; i++)
				{
					userNumber[i] = generator.nextInt(0,10);
				}//end for loop
				
				if(distinctNumbers(userNumber) > 1)
				{
					break;
				}//end if statement
				
			}//end while loop
			
		}//end if statement
		
		else
		{
			for(int i = 0; i < 3; i++)
			{
				userNumber[i] = generator.nextInt(0,10);
			}//end for loop
			
		}//end else statement
		
	}//end generateUserNumber
	
	/**
	 * The getWinningNumber method is an accessor method that returns the winningNumber array storing the digits of the winning number that's generated
	 * @return winningNumber - the array storing the generated digits of the winning number
	 */
	public int[] getWinningNumber()
	{
		return winningNumber;
	}//end getWinningNumber
	
	/**
	 * The generateWinningNumber method generates the digits of the winning number, so that the user number can be compared to and evaluated.
	 * The winning number that is generated is stored into the winningNumber array.
	 */
	public void generateWinningNumber()
	{
		if(betType.equals(BettingType.Box) || betType.equals(BettingType.StraightBox))
		{
			while(true)
			{
				for(int i = 0; i < 3; i++)
				{
					winningNumber[i] = generator.nextInt(0, 10);
				}
				
				if(distinctNumbers(winningNumber) > 1)
				{
					break;
				}//end if statement
				
			}//end while loop
			
		}//end if statement
		
		else
		{
			for(int i = 0; i < 3; i++)
			{
				winningNumber[i] = generator.nextInt(0, 10);
			}//end for loop
			
		}//end else statement
		
	}//end generateWinningNumber
	
	/**
	 * The hasArrayMatch method tests to see whether an array exists within the arraylist storing the permutations of the user number
	 * 
	 * @param arrayTesting - array being evaluated 
	 * @param arrayList - arraylist containing the permutations of the user number array
	 * @return isMatching - a boolean value representing whether the array matches one of the arrays in the arraylist containing the permutations of the user number
	 */
	public boolean hasArrayMatch(int[] arrayTesting, ArrayList<int[]> arrayList)
	{
		boolean isMatching = false;
		
		for(int[] array : arrayList)
		{
			if(Arrays.equals(array, arrayTesting))
			{
				isMatching = true;
				break;
			}
		}
		
		return isMatching;
	}//end hasArrayMatch
	
	/**
	 * The permutations method generates the permutations of the array passed in the parameter list based on the bettingType that the user is playing.
	 * The permutations are stored in the permuationsList arrayList.
	 * 
	 * @param userNum - the array containing the digits of the user number whose permutations are needed to be generated
	 */
	public void permutations(int[] userNum)
	{
		permutationsList.clear();
		
		//Straight: 1 winning permutation - user number played
		if(betType.equals(BettingType.Straight))
		{
			permutationsList.add(userNum);
		}//end if statement
		
		//Box and StraightBox: 6 winning permutations (if number played has 0 repeating digits) or 3 winning permutations (if number played has a pair of repeating digits
		else if(betType.equals(BettingType.Box) || betType.equals(BettingType.StraightBox))
		{
			for(int index1 = 0; index1 < 3; index1 ++)
			{
				for(int index2 = 0; index2 < 3; index2 ++)
				{
					for(int index3 = 0; index3 < 3; index3 ++)
					{
						if(index1 == index2 || index1 == index3 || index2 == index3)
						{
							continue;
						}//end if statement
						
						int[] permutatedArray = new int[3];
						
						permutatedArray[0] = userNum[index1];
						permutatedArray[1] = userNum[index2];
						permutatedArray[2] = userNum[index3];
						
						
						if(!hasArrayMatch(permutatedArray, permutationsList))
						{
							permutationsList.add(permutatedArray);
						}
						
					}//end for loop
					
				}//end for loop
				
			}//end for loop
			
		}//end else if statement
		
		//FrontPair: 10 winning permutations - first two digits played with the last digit being either 0, 1, 2, 3, 4, 5, 6, 7, 8, or 9
		else if(betType.equals(BettingType.FrontPair))
		{
			for(int i = 0; i < 10; i++)
			{
				int[] permutatedArray = {userNum[0], userNum[1], i};
				permutationsList.add(permutatedArray);
			}//end for loop
				
		}//end else if statement
		
		//BackPair: 10 winning permutations - last two digits played with the first digit being 0, 1, 2, 3, 4, 5, 6, 7, 8, or 9
		else if(betType.equals(BettingType.BackPair))
		{
			for(int i = 0; i < 10; i++)
			{
				int[] permutatedArray = {i, userNum[1], userNum[2]};
				permutationsList.add(permutatedArray);
			}//end for loop
				
		}//end else if statement
		
		//SplitPair: 10 winning permutations - first and last two digits played with the middle digit being 0, 1, 2, 3, 4, 5, 6, 7, 8, or 9
		else if(betType.equals(BettingType.SplitPair))
		{
			for(int i = 0; i < 10; i++)
			{
				int[] permutatedArray = {userNum[0], i, userNum[2]};
				permutationsList.add(permutatedArray);
			}//end for loop
				
		}//end else if statement
		
	}//end permutations
	
	/**
	 * The getPayout method returns the payout of the lottery ticket.
	 * Payouts are based on $1.00 wagers, so the payout is multiplied by the wager amount to get the proper payout if the user wagers more than $1.00.
	 * 
	 * @return payout - the monetary value won by the user
	 */
	public int getPayout()
	{
		return (getWagerAmount() * payout);
	}//end getPayout
	
	/**
	 * The calcPayout method sets the payout value based on the betType and whether the winning number matches any of the user number permutations 
	 * @param userNum - array containing the user played number
	 * @param winningNum - array containing the winning generated number
	 */
	public void calcPayout(int[] userNum, int[] winningNum)
	{	
		if(betType.equals(BettingType.Straight))
		{
			if(permutationsList.contains(winningNum))
			{
				payout = 6000;
			}//end if statement
			else
			{
				payout = 0;
			}//end else statement
			
		}//end if statement
		
		else if(betType.equals(BettingType.Box))
		{
			if(permutationsList.size() == 6)
			{	
				if(hasArrayMatch(winningNum, permutationsList))
				{
					payout = 100;
				}//end if statement
				else
				{
					payout = 0;
				}//end else statement
			
			}//end if statement
			
			else if(permutationsList.size() == 3)
			{	
				if(hasArrayMatch(winningNum, permutationsList))
				{
					payout = 200;
				}//end if statement
				
				else
				{
					payout = 0;
				}//end else statement
				
			}//end else if statement
			
		}//end else if statement
		
		else if(betType.equals(BettingType.StraightBox))
		{
			if(permutationsList.size() == 6)
			{
				if(Arrays.equals(userNum, winningNum))
				{
					payout = 350;
				}//end if statement
				
				else
				{			
					if(hasArrayMatch(winningNum, permutationsList))
					{
						payout = 200;
					}//end if statement
					
					else
					{
						payout = 0;
					}//end else statement
					
				}//end else statement
				
			}//end if statement
			
			else if(permutationsList.size() == 3)
			{
				if(Arrays.equals(userNum, winningNum))
				{
					payout = 400;
				}//end if statement
				
				else
				{
					if(hasArrayMatch(winningNum, permutationsList))
					{
						payout = 100;
					}//end if statement
					
					else
					{
						payout = 0;
					}//end else statement
					
				}//end else statement
				
			}//end else if statement

		}//end else if statement
			
		else if(betType.equals(BettingType.FrontPair))
		{
			if(hasArrayMatch(winningNum, permutationsList))
			{
				payout = 60;
			}//end if statement
			
			else
			{
				payout = 0;
			}//end else statement
			
		}//end else if statement
		
		else if(betType.equals(BettingType.BackPair))
		{
			if(hasArrayMatch(winningNum, permutationsList))
			{
				payout = 60;
			}//end if statement
			
			else
			{
				payout = 0;
			}//end else statement
			
		}//end else if statement
		
		else if(betType.equals(BettingType.SplitPair))
		{
			if(hasArrayMatch(winningNum, permutationsList))
			{
				payout = 60;
			}//end if statement
			
			else
			{
				payout = 0;
			}//end else statement
			
		}//end else if statement
		
	}//end calcPayout
	
	
	/**
	 * The generateTicket method creates a ticket using a stringBuilder object.
	 * The ticket includes the betting type, wager amount, the numbers played by the user, 
	 * the winning numbers generated, the payout of the ticket, and the return based on the ticket costs and payout.
	 * 
	 * @param userNum - the current userNumbers array
	 * @param winningNum - the winningNumbers array
	 * 
	 * @return A string representing a ticket.
	 */
	public String generateTicket(int[] userNum, int[] winningNum)
	{
		StringBuilder ticket = new StringBuilder("\n============================================= \n");
		ticket.append("Pick 3 Ticket \n");
		ticket.append("Amount Wagered: " + currency.format(getWagerAmount()) + "\n");
		ticket.append("Bet Type: " + getBetType() + "\n");
		ticket.append("Number Played: \n");
		
		for(int i = 0; i < userNum.length; i++)
		{
			ticket.append(userNum[i]);
		}//end for loop
		
		ticket.append("\nNumbers Needed for a Winning Ticket: \n");
		
		permutations(userNum);
		
		for(int[] array: permutationsList)
		{	
			for(int i = 0; i < array.length; i++)
			{
				ticket.append(array[i]);
			}//end for loop
			
			ticket.append(" ");
		}//end for loop
		
		ticket.append("\nWinning Number Drawn: \n");
		
		for(int i = 0; i < winningNum.length; i++)
		{
			ticket.append(winningNum[i]);
		}//end for loop
		
		calcPayout(userNum, winningNum);
		ticket.append("\nPayout: " + currency.format(getPayout()) + "\n");
		ticket.append("Return: " + (currency.format(getPayout() - getWagerAmount())) + "\n");
		ticket.append("=============================================");
		
		return ticket.toString();
	}//end generate ticket
	
	/**
	 * The numPlays method prompts the user asking how many tickets that they are looking to purchase and returns the value.
	 * @return numberOfPlays - the number of lottery tickets that the user is playing
	 */
	public int numPlays()
	{
		String numPlays;
		int numberOfPlays;
		
		while(true)
		{
			System.out.print("How many Pick3 lottery tickets are you looking to play?: ");
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
	 * The play method accepts an int value representing the the number of tickets that the user is going to play.
	 * For each play, the user is prompted on the amount they are looking to wager, the betting type and selection type they are looking to play. 
	 * After each play, a ticket is generated and stored in a string array. Once the user is finished playing, all of the tickets played will be printed,
	 * 
	 * @param numPlays
	 */
	public void play(int numPlays)
	{
		if(numPlays > 0)
		{
			int wagerAmount; //used to store the wager amount from the user prompt
			int selectionMethod; //used to store the selection method chosen by the user encoded as a 0 or 1.
			int totalPayout = 0; //used to store the payout of all Pick3 Lottery tickets. Variable initialized to 0.
			int totalWagers = 0; //used to store the wagers of all Pick 3 Lottery tickets. Variable initialized to 0.
			String input; //used to store user responses from the prompts
			String ticket; //used to store the ticket generated from the generateTicketMethod
			String[] ticketsBought = new String[numPlays]; //Array used to store Pick 4 lottery tickets
			
			generateWinningNumber();
			
			//For loop iterates over the body of code guiding the user through the process of filling out a pick 3 lottery ticket for the number of times they are looking to play
			for(int i = 0; i < numPlays; i++)
			{
				System.out.println("\nPick 3 Lottery Ticket " + (i + 1)); 
				
				/*
				 * While loop contains prompt for the user to enter the wager amount for the current ticket. 
				 * Loop is broken once the user enters a valid wager amount of either 1, 2, 3, 4 or 5
				 */
				while(true)
				{
					System.out.print("How much money would you wager for this ticket [Valid wager amounts: $1, $2, $3, $4, $5]?: $");
					input = scan.nextLine();
					
					try
					{
						wagerAmount = Integer.parseInt(input);
						
						if(1 <= wagerAmount && wagerAmount <= 5)
						{
							setWagerAmount(wagerAmount);
							break;
						}//end if statement
						
						else
						{
							throw rangeValueException;
						}//end else statement
						
					}//end try block
					
					catch(NumberFormatException excpetion)
					{
						System.out.println("Invalid input. \'" + input + "\' does not correspond to an numerical value. A valid wager amount input must be a whole dollar amount between $1 and $5.\n");
					}//end catch block
					
					catch(RangeValueException exception)
					{
						System.out.println("Invalid wager amount. The wager amount you have selected is not an acceptable wager value. Wager amounts must be whole dollar increments between $1 and $5.\n");
					}//end catch block
					
				}//end while loop
				
				/*
				 * While loop contains prompt for the user to enter the betting type that they would like to play for the current ticket. 
				 * Loop is broken once the user enters a valid betting type: Straight, Box, StraightBox, FrontPair, BackPair, SplitPair.
				 */
				while(true)
				{
					System.out.print("\nValid Betting Types: Straight, Box, StraightBox, FrontPair, BackPair, SplitPair. Which bet type would you like to play? (Enter Betting Type): ");
					input = scan.nextLine();
					
					if(input.compareToIgnoreCase("Straight") == 0)
					{
						System.out.println("Selected betting type: Straight.\n");
						setBetType(BettingType.Straight);
						break;
					}//end if statement
					
					else if(input.compareToIgnoreCase("Box") == 0)
					{
						System.out.println("Selected betting type: Box.\n");
						setBetType(BettingType.Box);
						break;
					}//end else if statement
					
					else if(input.compareToIgnoreCase("StraightBox") == 0)
					{
						System.out.println("Selected betting type: StraightBox.\n");
						setBetType(BettingType.StraightBox);
						break;
					}//end else if statement
					
					else if(input.compareToIgnoreCase("FrontPair") == 0)
					{
						System.out.println("Selected betting type: FrontPair.\n");
						setBetType(BettingType.FrontPair);
						break;
					}//end else if statement
					
					else if(input.compareToIgnoreCase("BackPair") == 0)
					{
						System.out.println("Selected betting type: BackPair.\n");
						setBetType(BettingType.BackPair);
						break;
					}//end else if statement
					
					else if(input.compareToIgnoreCase("SplitPair") == 0)
					{
						System.out.println("Selected betting type: SplitPair.\n");
						setBetType(BettingType.SplitPair);
						break;
					}//end else if statement
					
					else
					{
						System.out.println("\nInvalid Input: " + input + ". Make sure that either the spelling is correct on the betting type or that you are playing a valid betting type.\n");
					}//end else statement
					
				}//end while loop
				
				/*
				 * While loop contains prompt for the user to enter the selection method that they would like to play for the current ticket. 
				 * Loop is broken once the user enters a valid response: 0 for manual selection or 1 for quick pick (random selection)
				 */
				while(true)
				{
					System.out.println("Would you like to manually select your numbers or have them randomly selected?");
					System.out.print("Enter 0 for manual selection or 1 for quick pick (random selection): ");
					input = scan.nextLine();
					
					try 
					{
						selectionMethod = Integer.parseInt(input);
						
						if(selectionMethod == 0)
						{
							selectUserNumber();
							break;
						}//end if statement
						
						else if(selectionMethod == 1)
						{
							generateUserNumber();
							break;
						}//end else if statement
						
						else
						{
							throw rangeValueException;
						}//end else statement
						
					}//end try block
					
					catch(NumberFormatException exception)
					{
						System.out.println("Invalid Input! " + input + " is not a valid input. Make sure you are entering 0 for manual selection or 1 for quick pick (random selection) \n");
					}//end catch block
					
					catch(RangeValueException exception)
					{
						System.out.println("Invalid Input! " + input + " is not a valid selection. Make sure you are entering 0 for manual selection or 1 for quick pick (random selection) \n");
					}//end catch block
					
				}//end while loop
				
				ticket = generateTicket(getUserNumber(), getWinningNumber());
				ticketsBought[i] = ticket;
				totalPayout += getPayout(); //Ticket payout is added to the totalPayout variable used to keep track of the overall payout of all Pick 3 tickets played
				totalWagers += getWagerAmount(); //Ticket wager is added to the totalWagered variable used to keep track of the overall wagers of all Pick 3 tickets played
			}//end for loop
			
			for(String lotteryTickets : ticketsBought)
			{
				System.out.println(lotteryTickets);
			}//end for each loop
			
			System.out.println("Total payout for Pick3 Tickets: " + currency.format(totalPayout));
			System.out.println("Total return for Pick3 Tickets: " + currency.format(totalPayout - totalWagers) + "\n");
		}//end if statement
		
		else 
		{
			System.out.println("Pick 3 Lottery not played. \n");
		}//end else statement
		
	}//end play
	
}//end class