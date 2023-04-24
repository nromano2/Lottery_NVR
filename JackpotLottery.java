import java.util.Random;

/**
 * JackpotLottery class is the parent class of the Powerball and MegaMillions classes. 
 * The purpose of the JackpotLottery class is to represent a lottery game in which the goal is for the user to match a set of numbers with those of another set that is drawn to have the chance to win a large monetary value (jackpot).
 */

/**
 * @author nicholasromano
 * @version 1.0
 * Final Project
 * CS131 Spring 2023
 */

public class JackpotLottery {
	protected boolean multiplierStatus; //Variable used to represents whether or not the user is playing the multiplier.
	protected int multiplier; //Variable used to store a randomly generated multiplier value.
	protected long jackpot; //Variable used to store a randomly generated jackpot value.

	Random generator = new Random(); 
	
	/**
	 * Default Constructor sets mutliplierStatus to false, generates a random multiplier value based on wheter the user is playing the multiplier option,
	 * generates a jackpot value, and sets the ticket cost.
	 */
	public JackpotLottery()
	{
		setMultiplierStatus(false);
		generateMultiplierValue();
		generateJackpotValue();
		getTicketCost();
	}//end constructor 
	
	/**
	 * Method returns the cost of a lottery ticket. The standard ticket cost is $2, if the user is not playing either Megaplier or Powerplay. If the user is playing the Megaplier or PowerPlay, the ticket costs is $3. 
	 * Therefore the method, returns 2 if the user is not playing the multiplier and returns 3 if the user is playing the multiplier.
	 * @return the numerical value representing the cost of a ticket played.
	 */
	public int getTicketCost()
	{
		if(getMultiplierStatus())
		{
			return 3;
		}
		
		else
		{
			return 2;
		}
	}//end getTicketCost
	
	/**
	 * Accesor method that returns the multiplier value 
	 * @return the value of the multiplier 
	 */
	public int getMultiplierValue()
	{
		return multiplier;
	}//end getMultiplierValue
	
	/**
	 * Methods randomly selects a number between 2 and 5 if the user is playing the optional multiplier to have the chance to multiplier their earnings.
	 * Otherwise, if the user is not playing the optional multiplier, the multiplier is set to 1.
	 */
	public void generateMultiplierValue()
	{
		if(getMultiplierStatus())
		{
			this.multiplier = generator.nextInt(2,6);
		}
		else
		{
			this.multiplier = 1;
		}
	}//end generateMultiplierValue
	
	/**
	 * Accessor method returns a boolean value that represents whether or not the user is playing the optional multiplier.
	 * @return a boolean value that represents whether or not the user is playing the optional multiplier.
	 */
	public boolean getMultiplierStatus()
	{
		return multiplierStatus;
	}//end getMultiplierStatus
	
	/**
	 * Mutation method that allows the modification of whether or not the user is playing the optional multiplier.
	 * @param status - the new boolean value representing whether or not the user is playing the optional multiplier.
	 */
	public void setMultiplierStatus(boolean status)
	{
		this.multiplierStatus = status;
	}//end setMultiplierStatus
	
	/**
	 * Accessor method returns the randomly generated jackpot value.
	 * @return jackpot value
	 */
	public long getJackpotValue()
	{
		return jackpot;
	}//end getJackpotValue
	
	/**
	 * Method stores a randomly generated long datatype value between 20_000_000 and 675_000_000 to the jackpot variable.
	 */
	public void generateJackpotValue()
	{
		this.jackpot = generator.nextLong(20_000_000, 675_000_000);
	}//end generateJackpotValue
	
}//end class
