package model.tile;

import java.util.HashSet;

import model.player.Player;
import model.token.CharacterToken;
import model.token.GasLight;
import model.token.ManholeCover;

public class Lamppost extends Tile {
	
	private GasLight light;
	
	public Lamppost()
	{
		light = null;
	}
	
	public void placeGasLight(GasLight inGasLight) {
		light = inGasLight;
	}
	
	public void removeGasLight() {
		light.currTile=null;
		light = null;
		
	}
	
	public boolean isLit() {
		return (light != null);
	}

}
