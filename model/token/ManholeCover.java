package model.token;
import model.tile.*;
public class ManholeCover extends Token
{
	public ManholeCover(Manhole initialTile)
	{
		super(initialTile);
		initialTile.placeCover(this);
	}
}
