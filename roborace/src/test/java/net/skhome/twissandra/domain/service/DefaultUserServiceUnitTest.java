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
package net.skhome.twissandra.domain.service;

import java.util.UUID;

import net.skhome.twissandra.domain.entity.UserAccount;
import net.skhome.twissandra.domain.repository.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class DefaultUserServiceUnitTest {

	@DataPoint
	public static final UserAccount USER_ACCOUNT = new UserAccount(UUID.randomUUID().toString(), "username", "password");
	
	@Mock
	private UserRepository repository = null;

	private DefaultUserService service = null;

	@Before
	public void prepare() {
		MockitoAnnotations.initMocks(this);
		service = new DefaultUserService(repository);
	}

	@Test
	public void shouldFindUserAccountByUsername() {

		// assume
		final String username = "username";
		
		// given
		given(repository.findUserAccountByUsername(username)).willReturn(USER_ACCOUNT);
		
		// when
		final UserAccount resultAccount = service.findUserAccountByUsername(username);
		
		// then
		verify(repository).findUserAccountByUsername(username);
		assertNotNull(resultAccount);
		assertThat(resultAccount, is(sameInstance(USER_ACCOUNT)));

	}

	@Test
	public void shouldFindUserAccountByPrimaryKey() {
		
		// assume
		final String uuid = UUID.randomUUID().toString();
		
		// given
		given(repository.findUserAccountByPrimaryKey(uuid)).willReturn(USER_ACCOUNT);
		
		// when
		final UserAccount resultAccount = service.findUserAccountByPrimaryKey(uuid);
		
		// then
		verify(repository).findUserAccountByPrimaryKey(uuid);
		assertNotNull(resultAccount);
		assertThat(resultAccount, is(sameInstance(USER_ACCOUNT)));
		
	}

	@Test
	public void shouldReturnUsernameAvailable() {
		
		// assume
		final String username = "something";
		
		// given
		given(repository.findUserAccountByUsername(username)).willReturn(null);
		
		// when
		final boolean result = service.isUsernameAvailable(username);
		
		// then
		verify(repository).findUserAccountByUsername(username);
		assertThat(result, is(true));
		
	}

	@Test
	public void shouldReturnUsernameUnAvailable() {
		
		// assume
		final String username = "username";
		
		// given
		given(repository.findUserAccountByUsername(username)).willReturn(USER_ACCOUNT);
		
		// when
		final boolean result = service.isUsernameAvailable(username);
		
		// then
		verify(repository).findUserAccountByUsername(username);
		assertThat(result, is(false));
		
	}
	
	@Test
	public void shouldCreateUserAccount() {
		
		// assume
		final String username = "username";
		final String password = "password";
		final UserAccount account = new UserAccount(null, username, password);
		
		// given
		given(repository.findUserAccountByUsername(username)).willReturn(null);
		given(repository.createOrUpdateUserAccount(account)).willReturn(true);
		
		// when
		final boolean result = service.createUserAccount(account);
		
		// then
		verify(repository).findUserAccountByUsername(username);
		verify(repository).createOrUpdateUserAccount(account);
		assertThat(result, is(true));
		
	}

	@Test
	public void shouldNotCreateUserAccount() {
		
		// assume
		final String username = "username";
		final String password = "password";
		final UserAccount account = new UserAccount(null, username, password);
		
		// given
		given(repository.findUserAccountByUsername(username)).willReturn(account);
		
		// when
		final boolean result = service.createUserAccount(account);
		
		// then
		verify(repository).findUserAccountByUsername(username);
		verify(repository, never()).createOrUpdateUserAccount(account);
		assertThat(result, is(false));
		
	}

	@Test
	public void shouldUpdateUserAccount() {

		// assume
		final String username = "username";
		final String password = "password";
		final UserAccount account = new UserAccount(null, username, password);
		
		// given
		given(repository.findUserAccountByUsername(username)).willReturn(account);
		given(repository.createOrUpdateUserAccount(account)).willReturn(true);
		
		// when
		final boolean result = service.updateUserAccount(account);
		
		// then
		verify(repository).findUserAccountByUsername(username);
		verify(repository).createOrUpdateUserAccount(account);
		assertThat(result, is(true));
	
	}

	@Test
	public void shouldNotUpdateUserAccount() {

		// assume
		final String username = "username";
		final String password = "password";
		final UserAccount account = new UserAccount(null, username, password);
		
		// given
		given(repository.findUserAccountByUsername(username)).willReturn(null);
		
		// when
		final boolean result = service.updateUserAccount(account);
		
		// then
		verify(repository).findUserAccountByUsername(username);
		verify(repository, never()).createOrUpdateUserAccount(account);
		assertThat(result, is(false));
	
	}
	
}
