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
package net.skhome.roborace.domain.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.skhome.roborace.MockitoTestCase;
import net.skhome.roborace.domain.model.UserAccount;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ConsistencyLevel;
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

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;

@RunWith(Theories.class)
public class CassandraUserRepositoryUnitTest extends MockitoTestCase {

	private static final String COLUMN_FAMILY_USER = "user";

	private static final String COLUMN_FAMILY_USERNAME = "username";

	private static final String COLUMN_USERNAME = "username";

	@DataPoint
	public static String usernameValid = "john.doe";

	@DataPoint
	public static String usernameNull = null;

	@DataPoint
	public static String passwordValid = "foo";

	@DataPoint
	public static String passwordNull = null;

	@DataPoint
	public static UserAccount account = new UserAccount(null, usernameValid, passwordValid);

	@DataPoint
	public static UserAccount accountExisting = new UserAccount(UUID.randomUUID().toString(), usernameValid, passwordValid);

	@DataPoint
	public static UserAccount accountInvalid = null;

	@DataPoint
	public static UUID validUUID = UUID.randomUUID();

	@Mock
	private IThriftPool pool;

	@Mock
	private Selector selector;

	@Mock
	private Mutator mutator;

	@Mock
	private RowDeletor rowDeletor;

	// class to be tested
	private CassandraUserRepository repository;

	@Before
	public void prepare() {
		repository = new CassandraUserRepository(pool);
	}

	@Theory
	public void shouldFindUserAccountByUsername(final UUID uuid, final String username, final String password) {

		// assume
		assumeNotNull(uuid);
		assumeNotNull(username);
		assumeNotNull(password);

		// given
		final List<Column> usernameColumn = buildUsernameColumn(uuid);
		final List<Column> userColumn = buildUserColumn(username, password);

		given(pool.createSelector()).willReturn(selector);
		given(selector.getColumnsFromRow(eq(COLUMN_FAMILY_USERNAME), eq(username), anyBoolean(), isA(ConsistencyLevel.class))).willReturn(usernameColumn);
		given(selector.getColumnsFromRow(eq(COLUMN_FAMILY_USER), eq(uuid.toString()), anyBoolean(), isA(ConsistencyLevel.class))).willReturn(userColumn);

		// when
		final UserAccount userAccount = repository.findUserAccountByUsername(username);

		// then
		assertThat("user account was expected", userAccount, is(notNullValue()));
		assertThat("userid was wrong", userAccount.getId(), is(uuid.toString()));
		assertThat("username was wrong", userAccount.getUsername(), is(username));
		assertThat("password was wrong", userAccount.getPassword(), is(password));

	}

	@Theory
	public void shouldNotFindUserAccountByUsernameWithMissingUserEntry(final String username) {

		// assume
		assumeThat(username, is(notNullValue()));

		// given
		final UUID uuid = UUID.randomUUID();
		final List<Column> usernameColumn = buildUsernameColumn(uuid);

		given(pool.createSelector()).willReturn(selector);
		given(selector.getColumnsFromRow(eq(COLUMN_USERNAME), eq(username), anyBoolean(), isA(ConsistencyLevel.class))).willReturn(usernameColumn);

		// when
		final UserAccount userAccount = repository.findUserAccountByUsername(username);

		// then
		assertThat("user account was not expected", userAccount, is(nullValue()));

	}

	@Theory
	public void shouldNotFindUserAccountByUsernameWithMissingUsernameEntry(final String username) {

		// assume
		assumeThat(username, is(notNullValue()));

		// given
		given(pool.createSelector()).willReturn(selector);

		// when
		final UserAccount userAccount = repository.findUserAccountByUsername(username);

		// then
		assertThat("user account was not expected", userAccount, is(nullValue()));

	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionOnFindUserAccountByUsernameWithInvalidArgument() {

		// assumptions don't work with expected exceptions as of JUnit 4.8.2

		// given
		given(pool.createSelector()).willReturn(selector);

		// when
		repository.findUserAccountByUsername(null);

		// then
		fail("An IllegalArgumentException was expected!");

	}

	@Theory
	public void shouldFindUserAccountByPrimaryKey(final UUID uuid, final String username, final String password) {

		// assume
		assumeNotNull(uuid);
		assumeNotNull(username);
		assumeNotNull(password);

		// given
		final List<Column> userColumn = buildUserColumn(username, password);

		given(pool.createSelector()).willReturn(selector);
		given(selector.getColumnsFromRow(eq(COLUMN_FAMILY_USER), eq(uuid.toString()), anyBoolean(), isA(ConsistencyLevel.class))).willReturn(userColumn);

		// when
		final UserAccount userAccount = repository.findUserAccountByPrimaryKey(uuid.toString());

		// then
		assertThat("user account was expected", userAccount, is(notNullValue()));
		assertThat("userid was wrong", userAccount.getId(), is(uuid.toString()));
		assertThat("username was wrong", userAccount.getUsername(), is(username));
		assertThat("password was wrong", userAccount.getPassword(), is(password));

	}

	@Theory
	public void shouldNotFindUserAccountByPrimaryKey(final UUID uuid) {

		// assume
		assumeNotNull(uuid);

		// given
		given(pool.createSelector()).willReturn(selector);

		// when
		final UserAccount userAccount = repository.findUserAccountByPrimaryKey(uuid.toString());

		// then
		assertThat("user account was not expected", userAccount, is(nullValue()));

	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionOnFindUserAccountByPrimaryKeyWithInvalidArgument() {

		// assumptions don't work with expected exceptions as of JUnit 4.8.2

		// given
		given(pool.createSelector()).willReturn(selector);

		// when
		repository.findUserAccountByPrimaryKey(null);

		// then
		fail("An IllegalArgumentException was expected!");

	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionOnSaveUserAccountWithInvalidArgument() {

		// assumptions don't work with expected exceptions as of JUnit 4.8.2

		// given
		given(pool.createMutator()).willReturn(mutator);

		// when
		repository.createOrUpdateUserAccount(null);

		// then
		fail("An IllegalArgumentException was expected!");

	}

	@Theory
	public void shouldSaveUserAccountAndUpdateId(final UserAccount userAccount) {

		// assume
		assumeNotNull(userAccount);
		assumeNotNull(userAccount.getUsername());
		assumeNotNull(userAccount.getPassword());
		assumeThat(userAccount.getId(), is(nullValue()));

		// given
		given(pool.createMutator()).willReturn(mutator);

		// when
		final boolean result = repository.createOrUpdateUserAccount(userAccount);

		// then
		assertThat(result, is(true));
		assertThat(userAccount.getId(), is(notNullValue()));
		verify(mutator).writeColumns(eq(COLUMN_FAMILY_USER), anyString(), anyListOf(Column.class));
		verify(mutator).writeColumns(eq(COLUMN_FAMILY_USERNAME), eq(userAccount.getUsername()), anyListOf(Column.class));

	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionOnDeleteUserAccountWithInvalidArgument() {

		// assumptions don't work with expected exceptions as of JUnit 4.8.2

		// given
		given(pool.createMutator()).willReturn(mutator);

		// when
		repository.deleteUserAccount(null);

		// then
		fail("An IllegalArgumentException was expected!");

	}

	@Theory
	public void shouldDeleteUserAccount(final UserAccount userAccount) {

		// assume
		assumeNotNull(userAccount);
		assumeNotNull(userAccount.getId());
		assumeNotNull(userAccount.getPassword());

		// given
		given(pool.createRowDeletor()).willReturn(rowDeletor);

		// when
		repository.deleteUserAccount(userAccount);

		// then
		verify(rowDeletor).deleteRow(eq(COLUMN_FAMILY_USER), eq(userAccount.getId()), isA(ConsistencyLevel.class));
		verify(rowDeletor).deleteRow(eq(COLUMN_FAMILY_USERNAME), eq(userAccount.getUsername()), isA(ConsistencyLevel.class));

	}

	private static List<Column> buildUsernameColumn(UUID userid) {

		final List<Column> columnList = new ArrayList<Column>();

		final Column column = new Column();
		column.setName("userid".getBytes());
		column.setValue(userid.toString().getBytes());
		columnList.add(column);

		return columnList;

	}

	private static List<Column> buildUserColumn(String username, String password) {

		final List<Column> columnList = new ArrayList<Column>();

		final Column usernameColumn = new Column();
		usernameColumn.setName(COLUMN_USERNAME.getBytes());
		usernameColumn.setValue(username.getBytes());
		columnList.add(usernameColumn);

		final Column passwordColumn = new Column();
		passwordColumn.setName("password".getBytes());
		passwordColumn.setValue(password.getBytes());
		columnList.add(passwordColumn);

		return columnList;

	}

}
