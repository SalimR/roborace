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
 * A tile that represents one square of the {@link DefaultBattlefieldMap}. Tiles define and implement the behavior that should be
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
