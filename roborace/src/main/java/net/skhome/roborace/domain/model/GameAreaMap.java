package net.skhome.roborace.domain.model;

/**
 * A map which defines the tiles and size of a battlefield.
 * 
 * @author Sascha Krueger (sascha@skhome.net)
 */
public class GameAreaMap {

	private static final int DEFAULT_WIDTH = 20;

	private static final int DEFAULT_HEIGHT = 16;

	private final String name;

	private final GameAreaTileEnum[][] tiles;

	public GameAreaMap(final String name) {
		this.name = name;
		this.tiles = new GameAreaTileEnum[DEFAULT_WIDTH][DEFAULT_HEIGHT];
	}

	public String getName() {
		return name;
	}

	public GameAreaTileEnum getTile(final int column, final int row) {
		return tiles[column][row];
	}

}
