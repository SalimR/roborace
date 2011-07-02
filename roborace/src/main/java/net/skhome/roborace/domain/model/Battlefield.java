package net.skhome.roborace.domain.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The battle field is where all the action will happen.
 * 
 * @author Sascha Krueger (sascha@skhome.net)
 */
public class Battlefield {

	private final GameAreaMap map;
	
	private List<Robot> pieces;

	public Battlefield(final GameAreaMap map) {
		this.map = map;
	}
	
	public GameAreaMap getMap() {
		return map;
	}
	
	public List<Robot> getPieces() {
		if (pieces == null) {
			pieces = new ArrayList<Robot>();
		}
		return pieces;
	}
	
	
	
	
}
