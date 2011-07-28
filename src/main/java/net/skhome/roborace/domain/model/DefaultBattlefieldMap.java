/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.skhome.roborace.domain.model;

import java.util.List;

import static net.skhome.roborace.domain.model.BattlefieldTileEnum.*;

/**
 * A map which defines the tiles and size of a battlefield.
 *
 * @author Sascha Krueger (sascha@skhome.net)
 */
public class DefaultBattlefieldMap extends BattlefieldMap {

	/**
	 * The width of this battlefield map as number of tiles.
	 */
	private static final int WIDTH = 24;

	/**
	 * The height of this battlefield map as number of tiles.
	 */
	private static final int HEIGHT = 16;

	/**
	 * The name of this battlefield map.
	 */
	private static final String MAP_NAME = "Estrado";

	/**
	 * The tiles of this map.
	 */
	private static final List<BattlefieldTile> MAP_TILEs = new BattlefieldTile[][]{{WALL, WALL, WALL, WALL, WALL, WALL, WALL,
	WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL}, {WALL, FLOOR,
	FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR,
	FLOOR, FLOOR, FLOOR, FLOOR, WALL}, {WALL, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR,
	FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, WALL}, {WALL, FLOOR, FLOOR, FLOOR, FLOOR,
	FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR,
	FLOOR, WALL}, {WALL, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, SAVE, FLOOR,
	FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, WALL}, {WALL, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR,
	FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, WALL}, {WALL,
	FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR,
	FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, WALL}, {WALL, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR,
	FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, WALL}, {WALL, FLOOR, FLOOR, FLOOR,
	FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR,
	FLOOR, FLOOR, WALL}, {WALL, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR,
	FLOOR, FLOOR, FLOOR, FLOOR, CONVEYOR, FLOOR, FLOOR, FLOOR, FLOOR, WALL}, {WALL, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR,
	FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, CONVEYOR, FLOOR, FLOOR, FLOOR, FLOOR, WALL},
	{WALL, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR,
	FLOOR, CONVEYOR, FLOOR, FLOOR, FLOOR, FLOOR, WALL}, {WALL, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR,
	FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, CONVEYOR, FLOOR, FLOOR, FLOOR, FLOOR, WALL}, {WALL, FLOOR, FLOOR,
	FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR,
	FLOOR, FLOOR, FLOOR, WALL}, {WALL, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR,
	FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, START, FLOOR, FLOOR, FLOOR, WALL}, {WALL, WALL, WALL, WALL, WALL, WALL, WALL,
	WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL}};

	@Override
	public String getName() {
		return MAP_NAME;
	}

	@Override
	public int getWidth() {
		return WIDTH;
	}

	@Override
	public int getHeight() {
		return HEIGHT;
	}

	@Override
	public List<BattlefieldTile> getTiles() {
		return MAP_TILEs; // NOPMD
	}

}
