package model.tile;

import java.util.HashSet;
import model.player.*;
import model.token.CharacterToken;
import model.witnesscard.WitnessCard;
import model.token.Barricade;

public class Exit extends Tile implements Passable {

	private static Exit[] allExits;
	private static boolean[] barricadedExits;
	private static WitnessCard witnessCard;
	private Barricade barricade;
	private static int numExits;
	public static void setWitnessCard(WitnessCard wc) {
		witnessCard = wc;
	}
	public WitnessCard getWitnessCard()
	{
		return witnessCard;
	}
	
	@Override
	public HashSet<Passable> getAccessibleTiles(int numMoves, CharacterToken character, Player player) {

		if(numMoves < 1) throw new IllegalArgumentException("Cannot get accessible tiles when numMoves is less than 1.");
		
		HashSet<Passable> accessibleTiles = new HashSet<Passable>();
		
		// Can only exit if right player with right character and the witness card
		// shows not witnessed.
		if(player.canExitBoard(character) && !witnessCard.getWitnessed()) {
			accessibleTiles.add(this);
		}
		return accessibleTiles;
	}
	
	public void placeBarricade(Barricade inBarr)
	{
		barricade = inBarr;
		updateBarricaded();
	}
	public void removeBarricade()
	{
		barricade.currExit=null;
		barricade = null;
		updateBarricaded();
	}	
	public boolean isBarricaded()
	{
		return(barricade!=null);
	}
	public void updateBarricaded()
	{
		barricadedExits =  new boolean[numExits];
		for(int i = 0; i<numExits;i++)
		{
			if(allExits[i].isBarricaded())
			{
				barricadedExits[i]=true;
			}
			else{
				barricadedExits[i]=false;
			}
		}
	}
	public static void setExits(Exit... inExits)
	{
		allExits = inExits;
		numExits = allExits.length;
		
	}
}
