
public class Application {
	
	public static void main(String[] args){
		// TODO Auto-generated method stub
		MegaMillions MM_Lottery = new MegaMillions();
		Powerball PB_Lottery = new Powerball();
		Pick3 P3_Lottery = new Pick3();
		Pick4 P4_Lottery = new Pick4();
		
		System.out.println(MM_Lottery.displayRules());
		int megamillionsPlays = MM_Lottery.numPlays();
		MM_Lottery.play(megamillionsPlays);
		
		
		System.out.println(PB_Lottery.displayRules());
		int powerballPlays = PB_Lottery.numPlays();
		PB_Lottery.play(powerballPlays);
		
		System.out.println(P3_Lottery.displayRules());
		int pick3Plays = P3_Lottery.numPlays();
		P3_Lottery.play(pick3Plays);
		
		System.out.println(P4_Lottery.displayRules());
		int pick4Plays = P4_Lottery.numPlays();
		P4_Lottery.play(pick4Plays);

	}
}
