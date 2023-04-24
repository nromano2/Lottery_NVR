/**
 * @author nicholasromano
 * @version 1.0
 * Final Project
 * CS131 Spring 2023
 */
public class SelectionLottery {
	protected int wagerAmount; //used to store the wager amount the user is going to wager for each ticket played
	protected BettingType betType; //used to store the corresponding enumerated BettingType value representing the betting type that the user is playing.
	
	/**
	 * Default constructor sets the wagerAmount to 1 and the bettingType variable to Straight
	 */
	public SelectionLottery()
	{
		this.wagerAmount = 1;
		this.betType = BettingType.Straight;
	}//end default constructor
	
	/**
	 * Accessor method returns the current wagerAmount that the user is wagering
	 * @return wagerAmount - current wager that the user is playing
	 */
	public int getWagerAmount()
	{
		return wagerAmount;
	}//end getWagerAmount
	
	/**
	 * Mutator method allows for the modification of the wagerAmount to the value passed in the parameter list
	 * @param wagerAmt - the new wager amount that the user is wanting to wager
	 */
	public void setWagerAmount(int wagerAmt)
	{
		this.wagerAmount = wagerAmt;
	}//end setWagerAmount
	
	/**
	 * Accessor method returns the current bettingType value representing the betting type that the user is currently playing
	 * @return betType - the current betting type that the user is playing
	 */
	public BettingType getBetType()
	{
		return betType;
	}//end getBetType
	
	/**
	 * Mutator method allows for the modification of the betType to the value passed in the parameter list
	 * @param bettingType - the new bet type that the user is wanting to play.
	 */
	public void setBetType(BettingType bettingType)
	{
		this.betType = bettingType;
	}//end setBetType
	
}//end class
