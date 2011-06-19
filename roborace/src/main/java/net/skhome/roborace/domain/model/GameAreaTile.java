package net.skhome.roborace.domain.model;

/**
 * A tile that represents one square of the {@link GameAreaMap}. Tiles define and implement the behavior that should be
 * triggered when a robot moves unto that tile.
 * 
 * @author Sascha Krueger (sascha@skhome.net)
 */
public interface GameAreaTile {

	/**
	 * Returns if a piece  will be able to enter this tile. All game tiles that a piece should collide with (e.g. walls)
	 * should return <code>true</code> here.
	 * 
	 * @return <code>true</code> if a piece should be prevented from crossing this tile.
	 */
	public boolean isEnterable();
	
	
	/**
	 * Apply an action to the piece that entered this tile.
	 */
	public void applyAction();

}
