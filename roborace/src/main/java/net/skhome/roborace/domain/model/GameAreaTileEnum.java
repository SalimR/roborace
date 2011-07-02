package net.skhome.roborace.domain.model;

/**
 * A tile represents one square of the {@link GameAreaMap}.
 * 
 * @author Sascha Krueger (sascha@skhome.net}
 */
public enum GameAreaTileEnum implements GameAreaTile {

	/**
	 * A standard tile that robots may cross on the battle field and that applies no special actions
	 * to robots.
	 */
	FLOOR {

		@Override
		public boolean isEnterable() {
			return true;
		}

		@Override
		public void applyAction() {
			// TODO Auto-generated method stub
		}

	},

	WALL {
		
		@Override
		public boolean isEnterable() {
			return false;
		}

		@Override
		public void applyAction() {
			// TODO Auto-generated method stub
		}
		
	},

	CONVEYOR_BELT{

		@Override
		public boolean isEnterable() {
			return true;
		}

		@Override
		public void applyAction() {
			// TODO Auto-generated method stub
		}
		
	},

	OIL {

		@Override
		public boolean isEnterable() {
			return true;
		}

		@Override
		public void applyAction() {
			// TODO Auto-generated method stub
		}
		
	},

	HOLE {

		@Override
		public boolean isEnterable() {
			return true;
		}

		@Override
		public void applyAction() {
			// TODO Auto-generated method stub
		}
		
	},

	TURRET {
		
		@Override
		public boolean isEnterable() {
			return true;
		}

		@Override
		public void applyAction() {
			// TODO Auto-generated method stub
		}
		
	},

	CHECK_POINT {
		
		@Override
		public boolean isEnterable() {
			return true;
		}

		@Override
		public void applyAction() {
			// TODO Auto-generated method stub
		}
		
	},

	SAVE_POINT {
		
		@Override
		public boolean isEnterable() {
			return true;
		}

		@Override
		public void applyAction() {
			// TODO Auto-generated method stub
		}
		
	}

}
