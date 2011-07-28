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
 * A tile represents one square of the {@link BattlefieldMap}.
 *
 * @author Sascha Krueger (sascha@skhome.net}
 */
public enum BattlefieldTileEnum implements BattlefieldTile {

	/**
	 * A standard tile that robots may cross on the battle field and that applies no special actions to robots.
	 */
	FLOOR {
		@Override
		public boolean isAccessible() {
			return true;
		}

		@Override
		public void applyAction() {
			// TODO Auto-generated method stub
		}

	},

	WALL {
		@Override
		public boolean isAccessible() {
			return false;
		}

		@Override
		public void applyAction() {
			// TODO Auto-generated method stub
		}

	},

	CONVEYOR {
		@Override
		public boolean isAccessible() {
			return true;
		}

		@Override
		public void applyAction() {
			// TODO Auto-generated method stub
		}

	},

	OIL {
		@Override
		public boolean isAccessible() {
			return true;
		}

		@Override
		public void applyAction() {
			// TODO Auto-generated method stub
		}

	},

	HOLE {
		@Override
		public boolean isAccessible() {
			return true;
		}

		@Override
		public void applyAction() {
			// TODO Auto-generated method stub
		}

	},

	TURRET {
		@Override
		public boolean isAccessible() {
			return true;
		}

		@Override
		public void applyAction() {
			// TODO Auto-generated method stub
		}

	},

	CHECK_POINT {
		@Override
		public boolean isAccessible() {
			return true;
		}

		@Override
		public void applyAction() {
			// TODO Auto-generated method stub
		}

	},

	SAVE {
		@Override
		public boolean isAccessible() {
			return true;
		}

		@Override
		public void applyAction() {
			// TODO Auto-generated method stub
		}

	},

	START {
		@Override
		public boolean isAccessible() {
			return true;
		}

		@Override
		public void applyAction() {
			// TODO Auto-generated method stub
		}

	}
}
