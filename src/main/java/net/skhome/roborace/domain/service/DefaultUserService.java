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

package net.skhome.roborace.domain.service;

import net.skhome.roborace.domain.model.UserAccount;
import net.skhome.roborace.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Default implementation of the user service.
 *
 * @author Sascha Krueger
 */
@Service
public class DefaultUserService implements UserService {

	private final UserRepository repository;

	@Inject
	public DefaultUserService(final UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserAccount findUserAccountByUsername(final String username) {
		return repository.findUserAccountByUsername(username);
	}

	@Override
	public UserAccount findUserAccountByPrimaryKey(final String uuid) {
		return repository.findUserAccountByPrimaryKey(uuid);
	}

	@Override
	public boolean isUsernameAvailable(final String username) {
		return (findUserAccountByUsername(username) == null);
	}

	@Override
	public void createUserAccount(final UserAccount account) {
		if (isUsernameAvailable(account.getUsername())) {
			repository.createOrUpdateUserAccount(account);
		}
	}

	@Override
	public void updateUserAccount(final UserAccount account) {
		repository.createOrUpdateUserAccount(account);
	}

}
