package net.skhome.roborace.domain.repository.cassandra;

import net.skhome.roborace.domain.model.BattlefieldMap;
import net.skhome.roborace.domain.model.BattlefieldTile;
import net.skhome.roborace.domain.model.BattlefieldTileEnum;
import net.skhome.roborace.domain.repository.BattlefieldMapRepository;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.commons.lang3.StringUtils;
import org.scale7.cassandra.pelops.Mutator;
import org.scale7.cassandra.pelops.Selector;
import org.scale7.cassandra.pelops.pool.IThriftPool;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Repository to provide access to battlefield maps implemented on top of a Cassandra noSQL repository.
 *
 * @author Sascha Krüger (sascha@skhome.net)
 */
@Repository
public class CassandraBattlefieldMapRepository implements BattlefieldMapRepository {

	private static final String COLUMN_FAMILY_MAPNAME = "mapname";

	private static final String COLUMN_FAMILY_MAP = "map";

	private static final String COLUMN_NAME_NAME = "name";

	private static final String COLUMN_NAME_WIDTH = "width";

	private static final String COLUMN_NAME_HEIGHT = "height";

	private static final String COLUMN_NAME_TILES = "tiles";

	private static final String COLUMN_NAME_UUID = "uuid";

	private static final ConsistencyLevel CONSISTENCY_LEVEL_READ = ConsistencyLevel.ONE;

	private static final ConsistencyLevel CONSISTENCY_LEVEL_WRITE = ConsistencyLevel.ONE;

	private final IThriftPool connectionPool;


	@Inject
	public CassandraBattlefieldMapRepository(final IThriftPool connectionPool) {
		this.connectionPool = connectionPool;
	}

	@Override
	public BattlefieldMap findBattlefieldMapByName(final String name) {

		if (name == null) {
			throw new IllegalArgumentException("The passed map name was null.");
		}

		final Selector selector = connectionPool.createSelector();
		final List<Column> columns = selector.getColumnsFromRow(COLUMN_FAMILY_MAPNAME, name, false, CONSISTENCY_LEVEL_READ);

		if (!columns.isEmpty()) {
			final String uuid = Selector.getColumnStringValue(columns, COLUMN_NAME_UUID);
			return findBattlefieldMapById(uuid);
		}

		return null;

	}

	@Override
	public BattlefieldMap findBattlefieldMapById(final String uuid) {

		if (uuid == null) {
			throw new IllegalArgumentException("The passed map id was null.");
		}

		final Selector selector = connectionPool.createSelector();
		final List<Column> columns = selector.getColumnsFromRow(COLUMN_FAMILY_MAP, uuid, false, CONSISTENCY_LEVEL_READ);

		if (!columns.isEmpty()) {

			final String name = Selector.getColumnStringValue(columns, COLUMN_NAME_NAME);
			final int width = Integer.parseInt(Selector.getColumnStringValue(columns, COLUMN_NAME_WIDTH));
			final int height = Integer.parseInt(Selector.getColumnStringValue(columns, COLUMN_NAME_HEIGHT));
			final List<BattlefieldTile> tiles = new ArrayList<BattlefieldTile>(width * height);
			for (final String tile : Selector.getColumnStringValue(columns, COLUMN_NAME_TILES).split(":")) {
				tiles.add(BattlefieldTileEnum.valueOf(tile));
			}
			return new BattlefieldMap(uuid, name, width, height, tiles);
		}

		return null;

	}

	@Override
	public void createBattlefieldMap(final BattlefieldMap map) {

		if (map == null) {
			throw new IllegalArgumentException("The passed map was null.");
		}

		final String uuid = UUID.randomUUID().toString();
		final String name = map.getName();
		final String width = String.valueOf(map.getWidth());
		final String height = String.valueOf(map.getHeight());
		final String tiles = StringUtils.join(map.getTiles(), ':');

		if (!isNameAvailable(name)) {
			throw new DuplicateKeyException("A battlefield map with the name '" + name + "' already exists.");
		}

		final Mutator mutator = connectionPool.createMutator();

		// first we register the new battlefield map under a new uuid
		mutator.writeColumns(COLUMN_FAMILY_MAP, uuid, mutator.newColumnList(mutator.newColumn(COLUMN_NAME_NAME, name),
				mutator.newColumn(COLUMN_NAME_WIDTH, width), mutator.newColumn(COLUMN_NAME_HEIGHT, height),
				mutator.newColumn(COLUMN_NAME_TILES, tiles)));

		// then we update the inverted index for the uuid
		mutator.writeColumns(COLUMN_FAMILY_MAPNAME, name, mutator.newColumnList(mutator.newColumn(COLUMN_NAME_UUID, uuid)));

		// finally commit both changes
		mutator.execute(CONSISTENCY_LEVEL_WRITE);

		map.setId(uuid);

	}

	@Override
	public void updateBattlefieldMap(final BattlefieldMap map) {

		if (map == null) {
			throw new IllegalArgumentException("The passed map was null.");
		}

		final String uuid = map.getId();
		if (uuid == null) {
			throw new InvalidDataAccessApiUsageException("Can not update a map with an id of null.");
		}

		final Selector selector = connectionPool.createSelector();

		// check that if the name is already in use it belongs to this map to avoid unintentionally overriding wrong map
		final String name = map.getName();
		final BattlefieldMap storedMapForName = findBattlefieldMapByName(name);
		if (storedMapForName != null && !uuid.equals(storedMapForName.getId())) {
			throw new DuplicateKeyException("A battlefield map with the name '" + name + "' already exists.");
		}

		final String width = String.valueOf(map.getWidth());
		final String height = String.valueOf(map.getHeight());
		final String tiles = StringUtils.join(map.getTiles(), ':');

		final Mutator mutator = connectionPool.createMutator();

		// first update the battlefield map under its uuid
		mutator.writeColumns(COLUMN_FAMILY_MAP, uuid, mutator.newColumnList(mutator.newColumn(COLUMN_NAME_NAME, name),
				mutator.newColumn(COLUMN_NAME_WIDTH, width), mutator.newColumn(COLUMN_NAME_HEIGHT, height),
				mutator.newColumn(COLUMN_NAME_TILES, tiles)));

		// now update the inverted index in case the map got a new name
		mutator.writeColumns(COLUMN_FAMILY_MAPNAME, name, mutator.newColumnList(mutator.newColumn(COLUMN_NAME_UUID, uuid)));

		// finally commit both changes
		mutator.execute(CONSISTENCY_LEVEL_WRITE);

	}

	@Override
	public boolean isNameAvailable(final String name) {

		if (name == null) {
			return false;
		}

		final Selector selector = connectionPool.createSelector();
		final List<Column> columns = selector.getColumnsFromRow(COLUMN_FAMILY_MAPNAME, name, false, CONSISTENCY_LEVEL_READ);

		return columns.isEmpty();

	}

}
