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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.validation.constraints.NotNull;

import net.skhome.common.domain.model.CassandraEntity;

/**
 * The battle field is where all the action is happening.
 *
 * @author Sascha Krüger (sascha@skhome.net)
 */
public class Battlefield extends CassandraEntity {

	@NotNull
	private final BattlefieldMap map;

	@NotNull
	private List<Robot> pieces;

	/**
	 * Creates a new battlefield using the given map.
	 *
	 * @param map
	 * 		game area map
	 */
	public Battlefield(final BattlefieldMap map) {
		this.map = map;
	}

	/**
	 * Returns the map for this battlefield.
	 *
	 * @return related map
	 */
	public BattlefieldMap getMap() {
		return map;
	}

	/**
	 * Returns the game pieces for this battlefield.
	 *
	 * @return game pieces
	 */
	public List<Robot> getPieces() {
		if (pieces == null) {
			pieces = new ArrayList<Robot>();
		}
		return Collections.unmodifiableList(pieces);
	}

}
