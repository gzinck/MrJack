package model.token;

import java.util.HashSet;

import model.gameboard.TokenFinder;
import model.player.Player;
import model.tile.Lightable;
import model.tile.Passable;

public class CharTokenMover {
	private int[][] tileLocationOptions;
	private int[] selectedTile;
	private CharacterToken character;
	private TokenFinder finder;
	
	public int[][] getTileOptions(CharacterToken inCharacter, Player currPlayer, TokenFinder tokenFinder) {
		character = inCharacter;
		finder = tokenFinder;
		HashSet<Passable> tiles = character.getAccessibleTiles(currPlayer);
		tileLocationOptions = new int[tiles.size()][];
		int i = 0;
		for(Passable tile : tiles) {
			tileLocationOptions[i++] = tile.getTileLocation();
		}
		return tileLocationOptions;
	}
	public boolean selectTile(int[] location) {
		// Must see if it exists in the tokenLocationOptions
		for(int i = 0; i < tileLocationOptions.length; i++) {
			boolean isEqual = true;
			for(int j = 0 ; j < location.length; j++) if(tileLocationOptions[i][j] != location[j]) isEqual = false;
			if(isEqual) {
				selectedTile = location;
				performMove();
				return true;
			}
		}
		return false;
	}
	/**
	 * Performs the move, but if it cannot find the tile, returns true.
	 * It will only do this when it has reached an exit!
	 * 
	 * @return True if the move reached an exit
	 */
	private boolean performMove() {
		Lightable tile = finder.getTile(selectedTile);
		if(tile == null) return true;
		character.moveTo(tile);
		return false;
	}
	public boolean tileSelected() {
		return (selectedTile != null);
	}
}
