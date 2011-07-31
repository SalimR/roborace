package net.skhome.roborace.domain.repository.cassandra;

import net.skhome.roborace.MockitoTestCase;
import net.skhome.roborace.domain.model.BattlefieldMap;
import net.skhome.roborace.domain.model.BattlefieldTile;
import net.skhome.roborace.domain.model.BattlefieldTileEnum;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.scale7.cassandra.pelops.Mutator;
import org.scale7.cassandra.pelops.RowDeletor;
import org.scale7.cassandra.pelops.Selector;
import org.scale7.cassandra.pelops.pool.IThriftPool;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Matchers.eq;

@RunWith(Theories.class)
public class CassandraBattlefieldMapRepositoryUnitTest extends MockitoTestCase {

	private static final String COLUMN_FAMILY_MAPNAME_INDEX = "mapname";

	private static final String COLUMN_FAMILY_MAP = "map";

	private static final String COLUMN_NAME_NAME = "name";

	private static final String COLUMN_NAME_WIDTH = "width";

	private static final String COLUMN_NAME_HEIGHT = "height";

	private static final String COLUMN_NAME_TILES = "tiles";

	private static final String COLUMN_NAME_UUID = "uuid";

	private static final String MAP_NAME_SAVED = "saved";

	private static final String MAP_NAME_UNKNOWN = "unsaved";

	private static final String MAP_NAME_OTHER = "other";

	private static final String MAP_ID_SAVED = "2222-2222-2222-2222";

	private static final String MAP_ID_OTHER = "1111-1111-1111-1111";

	private BattlefieldMap battlefieldMapUnsaved;

	private BattlefieldMap battlefieldMapUnnamed;

	private BattlefieldMap battlefieldMapSaved;

	@Mock
	private IThriftPool connectionPool;

	@Mock
	private Selector selector;

	@Mock
	private Mutator mutator;

	@Mock
	private RowDeletor rowDeletor;

	// class to be tested
	private CassandraBattlefieldMapRepository repository;


	@Before
	public void prepare() {

		// reset class under test
		repository = new CassandraBattlefieldMapRepository(connectionPool);

		// reset test data
		battlefieldMapUnsaved = new BattlefieldMap(MAP_NAME_UNKNOWN, 24, 16, Arrays.asList(BattlefieldTileEnum.FLOOR, BattlefieldTileEnum.WALL, BattlefieldTileEnum.CONVEYOR));
		battlefieldMapUnnamed = new BattlefieldMap(null, 24, 16, Arrays.asList(BattlefieldTileEnum.FLOOR, BattlefieldTileEnum.WALL, BattlefieldTileEnum.CONVEYOR));
		battlefieldMapSaved = new BattlefieldMap(MAP_ID_SAVED, MAP_NAME_SAVED, 24, 16, Arrays.asList(BattlefieldTileEnum.FLOOR, BattlefieldTileEnum.WALL, BattlefieldTileEnum.CONVEYOR));
		
	}


	@Test
	public void shouldFindBattlefieldMapByName() {

		// assume
		final String name = MAP_NAME_SAVED;
		final List<Column> mapIndexColumnList = buildBattlefieldMapIndexColumns(battlefieldMapSaved.getId());
		final List<Column> mapColumnList = buildBattlefieldMapColumns(battlefieldMapSaved.getName(), battlefieldMapSaved.getWidth(), battlefieldMapSaved.getHeight(), battlefieldMapSaved.getTiles());

		// given
		given(connectionPool.createSelector()).willReturn(selector);
		given(selector.getColumnsFromRow(eq(COLUMN_FAMILY_MAPNAME_INDEX), eq(name), anyBoolean(), isA(ConsistencyLevel.class))).willReturn(mapIndexColumnList);
		given(selector.getColumnsFromRow(eq(COLUMN_FAMILY_MAP), eq(battlefieldMapSaved.getId()), anyBoolean(), isA(ConsistencyLevel.class))).willReturn(mapColumnList);

		// when
		final BattlefieldMap battlefieldMap = repository.findBattlefieldMapByName(name);

		// then
		assertNotNull("battlefield map", battlefieldMap);
		assertThat("map id", battlefieldMap.getId(), is(equalTo(battlefieldMapSaved.getId())));
		assertThat("map name", battlefieldMap.getName(), is(equalTo(battlefieldMapSaved.getName())));
		assertThat("map width", battlefieldMap.getWidth(), is(equalTo(battlefieldMapSaved.getWidth())));
		assertThat("map height", battlefieldMap.getHeight(), is(equalTo(battlefieldMapSaved.getHeight())));
		assertThat("map tiles", battlefieldMap.getTiles(), is(equalTo(battlefieldMapSaved.getTiles())));

	}

	@Test
	public void shouldNotFindBattlefieldMapByName() {

		// assume
		final String name = MAP_NAME_UNKNOWN;
		final List<Column> mapIndexColumnList = Collections.emptyList();

		// given
		given(connectionPool.createSelector()).willReturn(selector);
		given(selector.getColumnsFromRow(eq(COLUMN_FAMILY_MAPNAME_INDEX), eq(name), anyBoolean(), isA(ConsistencyLevel.class))).willReturn(mapIndexColumnList);

		// when
		final BattlefieldMap battlefieldMap = repository.findBattlefieldMapByName(name);

		// then
		assertNull("battlefield map", battlefieldMap);

	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentOnFindBattlefieldMapByNameNull() {

		// assume
		final String name = null;

		// given

		// when
		repository.findBattlefieldMapByName(name);

		// then

	}

	@Test
	public void shouldFindBattlefieldMapById() {

		// assume
		final String uuid = MAP_ID_SAVED;
		final List<Column> mapColumnList = buildBattlefieldMapColumns(battlefieldMapSaved.getName(), battlefieldMapSaved.getWidth(), battlefieldMapSaved.getHeight(), battlefieldMapSaved.getTiles());

		// given
		given(connectionPool.createSelector()).willReturn(selector);
		given(selector.getColumnsFromRow(eq(COLUMN_FAMILY_MAP), eq(uuid), anyBoolean(), isA(ConsistencyLevel.class))).willReturn(mapColumnList);

		// when
		final BattlefieldMap battlefieldMap = repository.findBattlefieldMapById(uuid);

		// then
		assertNotNull("battlefield map", battlefieldMap);
		assertThat("map id", battlefieldMap.getId(), is(equalTo(battlefieldMapSaved.getId())));
		assertThat("map name", battlefieldMap.getName(), is(equalTo(battlefieldMapSaved.getName())));
		assertThat("map width", battlefieldMap.getWidth(), is(equalTo(battlefieldMapSaved.getWidth())));
		assertThat("map height", battlefieldMap.getHeight(), is(equalTo(battlefieldMapSaved.getHeight())));
		assertThat("map tiles", battlefieldMap.getTiles(), is(equalTo(battlefieldMapSaved.getTiles())));

	}

	@Test
	public void shouldNotFindBattlefieldMapById() {

		// assume
		final String uuid = MAP_ID_OTHER;
		final List<Column> mapColumnList = Collections.emptyList();

		// given
		given(connectionPool.createSelector()).willReturn(selector);
		given(selector.getColumnsFromRow(eq(COLUMN_FAMILY_MAP), eq(uuid), anyBoolean(), isA(ConsistencyLevel.class))).willReturn(mapColumnList);

		// when
		final BattlefieldMap battlefieldMap = repository.findBattlefieldMapById(uuid);

		// then
		assertNull("battlefield map", battlefieldMap);

	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentOnFindBattlefieldMapByIdNull() {

		// assume
		final String uuid = null;

		// given

		// when
		repository.findBattlefieldMapById(uuid);

		// then

	}

	@Test
	public void shouldCreateBattlefieldMap() {

		// assume
		final BattlefieldMap battlefieldMap = battlefieldMapUnsaved; // map has no id yet
		final List<Column> mapIndexColumnList = Collections.emptyList(); // index has no entry for that name

		// given
		given(connectionPool.createSelector()).willReturn(selector);
		given(connectionPool.createMutator()).willReturn(mutator);

		given(selector.getColumnsFromRow(eq(COLUMN_FAMILY_MAPNAME_INDEX), eq(battlefieldMap.getName()), anyBoolean(), isA(ConsistencyLevel.class))).willReturn(mapIndexColumnList);

		// when
		repository.createBattlefieldMap(battlefieldMap);

		// then
		assertNotNull("map id", battlefieldMap.getId());
		verify(mutator).writeColumns(eq(COLUMN_FAMILY_MAP), eq(battlefieldMap.getId()), anyListOf(Column.class));
		verify(mutator).writeColumns(eq(COLUMN_FAMILY_MAPNAME_INDEX), eq(battlefieldMap.getName()), anyListOf(Column.class));
		verify(mutator).execute(isA(ConsistencyLevel.class));

	}

	@Test(expected = DuplicateKeyException.class)
	public void shouldThrowDuplicateKeyExceptionOnCreateBattlefieldMap() {

		// assume
		final BattlefieldMap battlefieldMap = battlefieldMapUnsaved; // map has no id yet
		final List<Column> mapIndexColumnList = buildBattlefieldMapIndexColumns("42"); // index has already an entry for
		// that name

		// given
		given(connectionPool.createSelector()).willReturn(selector);

		given(selector.getColumnsFromRow(eq(COLUMN_FAMILY_MAPNAME_INDEX), eq(battlefieldMap.getName()), anyBoolean(), isA(ConsistencyLevel.class))).willReturn(mapIndexColumnList);

		// when
		repository.createBattlefieldMap(battlefieldMap);

		// then should throw exception

	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionOnCreateBattlefieldMapWithNoName() {

		// assume
		final BattlefieldMap battlefieldMap = battlefieldMapUnnamed; // map has no name

		// given

		// when
		repository.createBattlefieldMap(battlefieldMap);

		// then should throw exception

	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionOnCreateBattlefieldMapWithNoMap() {

		// assume
		final BattlefieldMap battlefieldMap = null;

		// given

		// when
		repository.createBattlefieldMap(battlefieldMap);

		// then should throw exception

	}

	@Test
	public void shouldUpdateBattlefieldMap() {

		// assume
		final BattlefieldMap battlefieldMap = battlefieldMapSaved; // map has id
		final List<Column> mapIndexColumnList = buildBattlefieldMapIndexColumns(MAP_ID_SAVED); // index has entry for saved

		// given
		given(connectionPool.createSelector()).willReturn(selector);
		given(connectionPool.createMutator()).willReturn(mutator);

		given(selector.getColumnsFromRow(eq(COLUMN_FAMILY_MAPNAME_INDEX), eq(battlefieldMap.getName()), anyBoolean(), isA(ConsistencyLevel.class))).willReturn(mapIndexColumnList);

		// when
		repository.updateBattlefieldMap(battlefieldMap);

		// then
		verify(mutator).writeColumns(eq(COLUMN_FAMILY_MAP), eq(battlefieldMap.getId()), anyListOf(Column.class));
		verify(mutator).writeColumns(eq(COLUMN_FAMILY_MAPNAME_INDEX), eq(battlefieldMap.getName()), anyListOf(Column.class));
		verify(mutator).execute(isA(ConsistencyLevel.class));

	}

	@Test(expected = DuplicateKeyException.class)
	public void shouldThrowDuplicateKeyExceptionOnUpdateBattlefieldMap() {

		// assume
		final BattlefieldMap battlefieldMap = battlefieldMapSaved;
		final List<Column> mapIndexColumnList = buildBattlefieldMapIndexColumns(MAP_ID_OTHER);
		final List<Column> mapColumnList = buildBattlefieldMapColumns(MAP_NAME_OTHER, battlefieldMapSaved.getWidth(), battlefieldMapSaved.getHeight(), battlefieldMapSaved.getTiles());

		// given
		given(connectionPool.createSelector()).willReturn(selector);

		given(selector.getColumnsFromRow(eq(COLUMN_FAMILY_MAPNAME_INDEX), eq(battlefieldMap.getName()), anyBoolean(), isA(ConsistencyLevel.class))).willReturn(mapIndexColumnList);
		given(selector.getColumnsFromRow(eq(COLUMN_FAMILY_MAP), eq(MAP_ID_OTHER), anyBoolean(), isA(ConsistencyLevel.class))).willReturn(mapColumnList);

		// when
		repository.updateBattlefieldMap(battlefieldMap);

		// then should throw exception

	}

	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void shouldThrowInvalidDataAccessApiUsageExceptionOnUpdateBattlefieldMap() {

		// assume
		final BattlefieldMap battlefieldMap = battlefieldMapUnsaved;

		// given

		// when
		repository.updateBattlefieldMap(battlefieldMap);

		// then should throw exception

	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldIllegalArgumentExceptionOnUpdateBattlefieldMap() {

		// assume
		final BattlefieldMap battlefieldMap = null;

		// given

		// when
		repository.updateBattlefieldMap(battlefieldMap);

		// then should throw exception

	}


	@Test
	public void shouldReturnTrueOnIsNameAvailable() {

		// assume
		final String name = MAP_NAME_UNKNOWN;
		final List<Column> mapIndexColumnList = Collections.emptyList();

		// given
		given(connectionPool.createSelector()).willReturn(selector);
		given(selector.getColumnsFromRow(eq(COLUMN_FAMILY_MAPNAME_INDEX), eq(name), anyBoolean(), isA(ConsistencyLevel.class))).willReturn(mapIndexColumnList);

		// when
		final boolean available = repository.isNameAvailable(name);

		// then
		assertTrue("available", available);

	}

	@Test
	public void shouldReturnFalseOnIsNameAvailable() {

		// assume
		final String name = MAP_NAME_SAVED;
		final List<Column> mapIndexColumnList = buildBattlefieldMapIndexColumns(MAP_ID_SAVED);

		// given
		given(connectionPool.createSelector()).willReturn(selector);
		given(selector.getColumnsFromRow(eq(COLUMN_FAMILY_MAPNAME_INDEX), eq(name), anyBoolean(), isA(ConsistencyLevel.class))).willReturn(mapIndexColumnList);

		// when
		final boolean available = repository.isNameAvailable(name);

		// then
		assertFalse("available", available);

	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionOnIsNameAvailableWithNull() {

		// assume
		final String name = null;

		// given

		// when
		repository.isNameAvailable(name);

		// then should throw exception

	}

	private static List<Column> buildBattlefieldMapColumns(final String name, final int width, final int height, final List<? extends BattlefieldTile> tiles) {
		final List<Column> columns = new ArrayList<Column>();
		columns.add(buildNameColumn(name));
		columns.add(buildWidthColumn(width));
		columns.add(buildHeightColumn(height));
		columns.add(buildTilesColumn(tiles));
		return columns;
	}

	private static List<Column> buildBattlefieldMapIndexColumns(final String uuid) {
		final List<Column> columns = new ArrayList<Column>();
		columns.add(buildIdColumn(uuid));
		return columns;
	}

	private static Column buildIdColumn(final String uuid) {
		return buildColumn(COLUMN_NAME_UUID, uuid);
	}

	private static Column buildNameColumn(final String name) {
		return buildColumn(COLUMN_NAME_NAME, name);
	}

	private static Column buildWidthColumn(final int width) {
		return buildColumn(COLUMN_NAME_WIDTH, String.valueOf(width));
	}

	private static Column buildHeightColumn(final int height) {
		return buildColumn(COLUMN_NAME_HEIGHT, String.valueOf(height));
	}

	private static Column buildTilesColumn(final List<? extends BattlefieldTile> tiles) {
		return buildColumn(COLUMN_NAME_TILES, StringUtils.join(tiles, ':'));
	}

	private static Column buildColumn(final String name, final String value) {
		final Column column = new Column();
		column.setName(name.getBytes());
		column.setValue(value.getBytes());
		return column;
	}
}
