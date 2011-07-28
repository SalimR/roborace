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

import net.skhome.roborace.domain.model.UserAccount;

/**
 * Classes implementing this interface provide access to user account information.
 *
 * @author Sascha Krueger (sascha@skhome.net)
 */
public interface UserRepository {

	/**
	 * Queries the repository to find the user account for the given username. The username must be unique within the repository.
	 *
	 * @param username
	 * 		username of the account
	 *
	 * @return user account or <code>null</code> if no such account exists
	 */
	public UserAccount findUserAccountByUsername(String username);

	/**
	 * Queries the repository to find the user account for the given primary key. The primary key must be unique within the
	 * repository.
	 *
	 * @param uuid
	 * 		unique primary key
	 *
	 * @return user account or <code>null</code> if no such account exists
	 */
	public UserAccount findUserAccountByPrimaryKey(String uuid);

	/**
	 * Adds the given user account to the repository.
	 *
	 * @param userAccount
	 * 		user account
	 *
	 * @return user account with updated id
	 */
	public void createOrUpdateUserAccount(UserAccount userAccount);

	/**
	 * Removes the given user account from the repository.
	 *
	 * @param userAccount
	 * 		user account
	 */
	public boolean deleteUserAccount(UserAccount userAccount);

}
