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

import net.skhome.common.domain.model.CassandraEntity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

/**
 * A map that defines the tiles, name and size of a battlefield.
 *
 * @author Sascha Krueger (sascha@skhome.net)
 */
public class BattlefieldMap extends CassandraEntity {

	/**
	 * name of this map to help humans distinguish them
	 */
	@NotNull
	private String name;

	/**
	 * width of this map as number of tiles
	 */
	@NotNull
	@Min(value = 10)
	private int width;

	/**
	 * height of this map as number of tiles
	 */
	@NotNull
	@Min(value = 10)
	private int height;

	/**
	 * tiles of this map
	 */
	@NotNull
	private List<BattlefieldTile> tiles;

	/**
	 * Create a new battlefield map with the given information.
	 *
	 * @param name
	 * 		name of the map (must be unique within the repository)
	 * @param width
	 * 		width of the map
	 * @param height
	 * 		height of the map
	 * @param tiles
	 * 		tiles that define the squares
	 */
	public BattlefieldMap(final String name, final int width, final int height, final List<? extends BattlefieldTile> tiles) {
		this.name = name;
		this.width = width;
		this.height = height;
		this.tiles = Collections.unmodifiableList(tiles);
	}

	/**
	 * Create a new battlefield map with the given information.
	 *
	 * @param uuid
	 * 		primary key of this map
	 * @param name
	 * 		name of the map (must be unique within the repository)
	 * @param width
	 * 		width of the map
	 * @param height
	 * 		height of the map
	 * @param tiles
	 * 		tiles that define the squares
	 */
	public BattlefieldMap(final String uuid, final String name, final int width, final int height,
	final List<? extends BattlefieldTile> tiles) {
		super(uuid);
		this.name = name;
		this.width = width;
		this.height = height;
		this.tiles = Collections.unmodifiableList(tiles);
	}

	public String getName() {
		return name;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public List<BattlefieldTile> getTiles() {
		return tiles;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("BattlefieldMap");
		sb.append("{name='").append(name).append('\'');
		sb.append(", width=").append(width);
		sb.append(", height=").append(height);
		sb.append(", tiles=").append(tiles == null ? "null" : tiles.toString());
		sb.append('}');
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof BattlefieldMap)) {
			return false;
		}

		BattlefieldMap that = (BattlefieldMap) o;

		if (height != that.height) {
			return false;
		}
		if (width != that.width) {
			return false;
		}
		if (name != null ? !name.equals(that.name) : that.name != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + width;
		result = 31 * result + height;
		return result;
	}
}
