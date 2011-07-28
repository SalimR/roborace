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
package net.skhome.roborace.domain.repository.cassandra;

import net.skhome.roborace.domain.model.UserAccount;
import net.skhome.roborace.domain.repository.UserRepository;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.scale7.cassandra.pelops.Mutator;
import org.scale7.cassandra.pelops.RowDeletor;
import org.scale7.cassandra.pelops.Selector;
import org.scale7.cassandra.pelops.pool.IThriftPool;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

/**
 * Repository to provide access to user account information implemented on top of a Cassandra NoSQL store.
 *
 * @author Sascha Krueger (sascha@skhome.net)
 */
@Repository
public class CassandraUserRepository implements UserRepository {

	private static final String COLUMN_FAMILY_USERNAME = "username";

	private static final String COLUMN_FAMILY_USER = "user";

	private static final ConsistencyLevel CONSISTENCY_LEVEL_READ = ConsistencyLevel.ONE;

	private static final ConsistencyLevel CONSISTENCY_LEVEL_WRITE = ConsistencyLevel.ONE;

	private final IThriftPool connectionPool;

	@Inject
	public CassandraUserRepository(final IThriftPool connectionPool) {
		this.connectionPool = connectionPool;
	}

	@Override
	public final UserAccount findUserAccountByUsername(final String username) {

		if (username == null) {
			throw new IllegalArgumentException("The passed username was null.");
		}

		final Selector selector = connectionPool.createSelector();
		final List<Column> columns = selector.getColumnsFromRow(COLUMN_FAMILY_USERNAME, username, false,
				CONSISTENCY_LEVEL_READ);

		if (!columns.isEmpty()) {
			final String userid = Selector.getColumnStringValue(columns, "userid");
			return findUserAccountByPrimaryKey(userid);
		}

		return null;

	}

	@Override
	public final UserAccount findUserAccountByPrimaryKey(final String uuid) {

		if (uuid == null) {
			throw new IllegalArgumentException("The passed uuid was null.");
		}

		final Selector selector = connectionPool.createSelector();
		final List<Column> columns = selector.getColumnsFromRow(COLUMN_FAMILY_USER, uuid, false, CONSISTENCY_LEVEL_READ);

		if (!columns.isEmpty()) {
			final String username = Selector.getColumnStringValue(columns, "username");
			final String password = Selector.getColumnStringValue(columns, "password");
			return new UserAccount(uuid, username, password);
		}

		return null;

	}

	@Override
	public final void createOrUpdateUserAccount(final UserAccount userAccount) {

		if (userAccount == null) {
			throw new IllegalArgumentException("The passed user account was null.");
		}

		final Mutator mutator = connectionPool.createMutator();

		final String uuid = UUID.randomUUID().toString();
		final String username = userAccount.getUsername();
		final String password = userAccount.getPassword();

		// first we register the user user account under a new uuid
		mutator.writeColumns(COLUMN_FAMILY_USER, uuid, mutator.newColumnList(mutator.newColumn("username", username),
				mutator.newColumn("password", password)));

		// then we update the inverted index for the uuid
		mutator.writeColumns(COLUMN_FAMILY_USERNAME, username, mutator.newColumnList(mutator.newColumn("userid", uuid)));

		// finally commit both updates
		mutator.execute(CONSISTENCY_LEVEL_WRITE);

		userAccount.setId(uuid);

	}

	@Override
	public final boolean deleteUserAccount(final UserAccount userAccount) {

		if (userAccount == null) {
			throw new IllegalArgumentException("The passed user account was null.");
		}

		final RowDeletor deletor = connectionPool.createRowDeletor();

		final String userid = userAccount.getId();
		final String username = userAccount.getUsername();

		deletor.deleteRow(COLUMN_FAMILY_USER, userid, CONSISTENCY_LEVEL_WRITE);
		deletor.deleteRow(COLUMN_FAMILY_USERNAME, username, CONSISTENCY_LEVEL_WRITE);

		return true;

	}

}
