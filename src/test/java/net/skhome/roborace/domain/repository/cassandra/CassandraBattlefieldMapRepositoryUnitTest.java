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
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.scale7.cassandra.pelops.Mutator;
import org.scale7.cassandra.pelops.RowDeletor;
import org.scale7.cassandra.pelops.Selector;
import org.scale7.cassandra.pelops.pool.IThriftPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeThat;
import static org.mockito.BDDMockito.*;

@RunWith(Theories.class)
public class CassandraBattlefieldMapRepositoryUnitTest extends MockitoTestCase {

	private static final String COLUMN_FAMILY_MAPNAME_INDEX = "mapname";

	private static final String COLUMN_FAMILY_MAP = "map";

	private static final String COLUMN_NAME_NAME = "name";

	private static final String COLUMN_NAME_WIDTH = "width";

	private static final String COLUMN_NAME_HEIGHT = "height";

	private static final String COLUMN_NAME_TILES = "tiles";

	private static final String COLUMN_NAME_UUID = "uuid";

	@DataPoint
	public static String MAP_NAME_SAVED = "saved";

	@DataPoint
	public static String MAP_NAME_UNKNOWN = "unsaved";

	@DataPoint
	public static String MAP_ID_SAVED = "2222-2222-2222-2222";

	@DataPoint
	public static String MAP_ID_UNKNOWN = "1111-1111-1111-1111";

	@DataPoint
	public static BattlefieldMap BATTLEFIELD_MAP_UNSAVED = new BattlefieldMap(MAP_NAME_UNKNOWN, 24, 16,
			Arrays.asList(BattlefieldTileEnum.FLOOR, BattlefieldTileEnum.WALL, BattlefieldTileEnum.CONVEYOR));

	@DataPoint
	public static final BattlefieldMap BATTLEFIELD_MAP_SAVED = new BattlefieldMap(MAP_ID_SAVED, MAP_NAME_SAVED, 24, 16,
			Arrays.asList(BattlefieldTileEnum.FLOOR, BattlefieldTileEnum.WALL, BattlefieldTileEnum.CONVEYOR));

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
		repository = new CassandraBattlefieldMapRepository(connectionPool);
	}


	@Theory
	public void shouldFindBattlefieldMapByName(final String name) {

		// assume
		assumeNotNull(name);
		assumeThat(name, is(equalTo(MAP_NAME_SAVED)));

		// given
		final List<Column> mapIndexColumnList = buildBattlefieldNameIndexMapColumns(BATTLEFIELD_MAP_SAVED.getId());
		final List<Column> mapColumnList = buildBattlefieldMapColumns(BATTLEFIELD_MAP_SAVED.getName(),
				BATTLEFIELD_MAP_SAVED.getWidth(), BATTLEFIELD_MAP_SAVED.getHeight(), BATTLEFIELD_MAP_SAVED.getTiles());

		given(connectionPool.createSelector()).willReturn(selector);
		given(selector.getColumnsFromRow(eq(COLUMN_FAMILY_MAPNAME_INDEX), eq(name), anyBoolean(),
				isA(ConsistencyLevel.class))).willReturn(mapIndexColumnList);
		given(selector.getColumnsFromRow(eq(COLUMN_FAMILY_MAP), eq(BATTLEFIELD_MAP_SAVED.getId()), anyBoolean(),
				isA(ConsistencyLevel.class))).willReturn(mapColumnList);

		// when
		final BattlefieldMap battlefieldMap = repository.findBattlefieldMapByName(name);

		// then
		assertNotNull("battlefield map", battlefieldMap);
		assertThat("map id", battlefieldMap.getId(), is(equalTo(BATTLEFIELD_MAP_SAVED.getId())));
		assertThat("map name", battlefieldMap.getName(), is(equalTo(BATTLEFIELD_MAP_SAVED.getName())));
		assertThat("map width", battlefieldMap.getWidth(), is(equalTo(BATTLEFIELD_MAP_SAVED.getWidth())));
		assertThat("map height", battlefieldMap.getHeight(), is(equalTo(BATTLEFIELD_MAP_SAVED.getHeight())));
		assertThat("map tiles", battlefieldMap.getTiles(), is(equalTo(BATTLEFIELD_MAP_SAVED.getTiles())));

	}

	@Theory
	public void shouldNotFindBattlefieldMapByName(final String name) {

		// assume
		assumeNotNull(name);
		assumeThat(name, is(equalTo(MAP_NAME_UNKNOWN)));

		// given
		final List<Column> mapIndexColumnList = Collections.emptyList();

		given(connectionPool.createSelector()).willReturn(selector);
		given(selector.getColumnsFromRow(eq(COLUMN_FAMILY_MAPNAME_INDEX), eq(name), anyBoolean(),
				isA(ConsistencyLevel.class))).willReturn(mapIndexColumnList);

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

	@Theory
	public void shouldFindBattlefieldMapById(final String uuid) {

		// assume
		assumeNotNull(uuid);
		assumeThat(uuid, is(equalTo(MAP_ID_SAVED)));

		// given
		final List<Column> mapColumnList = buildBattlefieldMapColumns(BATTLEFIELD_MAP_SAVED.getName(),
				BATTLEFIELD_MAP_SAVED.getWidth(), BATTLEFIELD_MAP_SAVED.getHeight(), BATTLEFIELD_MAP_SAVED.getTiles());

		given(connectionPool.createSelector()).willReturn(selector);
		given(selector.getColumnsFromRow(eq(COLUMN_FAMILY_MAP), eq(uuid), anyBoolean(),
				isA(ConsistencyLevel.class))).willReturn(mapColumnList);

		// when
		final BattlefieldMap battlefieldMap = repository.findBattlefieldMapById(uuid);

		// then
		assertNotNull("battlefield map", battlefieldMap);
		assertThat("map id", battlefieldMap.getId(), is(equalTo(BATTLEFIELD_MAP_SAVED.getId())));
		assertThat("map name", battlefieldMap.getName(), is(equalTo(BATTLEFIELD_MAP_SAVED.getName())));
		assertThat("map width", battlefieldMap.getWidth(), is(equalTo(BATTLEFIELD_MAP_SAVED.getWidth())));
		assertThat("map height", battlefieldMap.getHeight(), is(equalTo(BATTLEFIELD_MAP_SAVED.getHeight())));
		assertThat("map tiles", battlefieldMap.getTiles(), is(equalTo(BATTLEFIELD_MAP_SAVED.getTiles())));

	}

	@Theory
	public void shouldNotFindBattlefieldMapById(final String uuid) {

		// assume
		assumeNotNull(uuid);
		assumeThat(uuid, is(equalTo(MAP_ID_UNKNOWN)));

		// given
		final List<Column> mapColumnList = Collections.emptyList();

		given(connectionPool.createSelector()).willReturn(selector);
		given(selector.getColumnsFromRow(eq(COLUMN_FAMILY_MAP), eq(uuid), anyBoolean(),
				isA(ConsistencyLevel.class))).willReturn(mapColumnList);

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
	public void shouldCreateBattlefieldMap() throws Exception {

		// given

		// when


		// then

	}

	@Test
	public void shouldUpdateBattlefieldMap() throws Exception {

		// given

		// when


		// then

	}

	@Test
	public void shouldIsNameAvailable() throws Exception {

		// given

		// when


		// then

	}


	private static List<Column> buildBattlefieldMapColumns(final String name, final int width, final int height,
	                                                       final List<? extends BattlefieldTile> tiles) {
		final List<Column> columns = new ArrayList<Column>();
		columns.add(buildNameColumn(name));
		columns.add(buildWidthColumn(width));
		columns.add(buildHeightColumn(height));
		columns.add(buildTilesColumn(tiles));
		return columns;
	}

	private static List<Column> buildBattlefieldNameIndexMapColumns(final String uuid) {
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
