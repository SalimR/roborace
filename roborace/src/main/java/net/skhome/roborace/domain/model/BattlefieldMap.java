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

/**
 * A map that defines the tiles, name and size of a battlefield.
 * 
 * @author Sascha Krueger (sascha@skhome.net)
 */
public interface BattlefieldMap {

	/**
	 * Returns the name of this map.
	 * 
	 * @return name of the map
	 */
	public String getName();

	/**
	 * Returns the width of this map as number of tiles.
	 * 
	 * @return width of this map
	 */
	public int getWidth();
	
	/**
	 * Returns the height of this map as number of tiles.
	 * 
	 * @return height of this map
	 */
	public int getHeight();

	/**
	 * Returns the tiles of this map.
	 * 
	 * @return tiles of this map
	 */
	public GameAreaTile[][] getTiles();

}