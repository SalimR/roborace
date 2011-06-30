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
package net.skhome.roborace.web.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.skhome.common.domain.ResourceAlreadyExistsException;
import net.skhome.common.domain.ResourceNotFoundException;
import net.skhome.roborace.domain.model.UserAccount;
import net.skhome.roborace.domain.service.UserService;
import net.skhome.roborace.web.controller.UserController;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(Theories.class)
public class UserControllerUnitTest {

	@DataPoint
	public static final UserAccount ACCOUNT_TO_BE_CREATED = new UserAccount(null, "username", "password");
	
	@DataPoint
	public static final UserAccount ACCOUNT_TO_BE_UPDATED = new UserAccount(UUID.randomUUID().toString(), "username", "password");
	
	@Mock
	private UserService service = null;

	@Mock
	private HttpServletResponse response = null;

	private UserController controller = null;

	@Before
	public void prepare() {

		MockitoAnnotations.initMocks(this);

		// automatic constructor injection currently not supported by mockito
		controller = new UserController(service);

	}

	@Test
	public void shouldFindAccountByUsername() {

		// assume
		final String uuid = UUID.randomUUID().toString();
		final String username = "username";
		final String password = "password";

		// given
		final UserAccount givenAccount = new UserAccount(uuid, username, password);

		given(service.findUserAccountByUsername(username)).willReturn(givenAccount);

		// when
		final UserAccount resultAccount = controller.findByUsername(username);

		// then
		assertThat("account", resultAccount, is(equalTo(givenAccount)));

	}

	@Test(expected = ResourceNotFoundException.class)
	public void shouldThrowExceptionOnNotExistentUsername() {

		// assume
		final String username = "unavailable";

		// given
		given(service.findUserAccountByUsername(username)).willReturn(null);

		// when
		controller.findByUsername(username);

		// then
		fail("an excpetion was expected");

	}

	@Test
	public void shouldFindAccountByPrimaryKey() {

		// assume
		final String uuid = UUID.randomUUID().toString();
		final String username = "username";
		final String password = "password";

		// given
		final UserAccount givenAccount = new UserAccount(uuid, username, password);

		given(service.findUserAccountByPrimaryKey(uuid)).willReturn(givenAccount);

		// when
		final UserAccount resultAccount = controller.findByPrimaryKey(uuid);

		// then
		assertThat("account", resultAccount, is(equalTo(givenAccount)));

	}

	@Test(expected = ResourceNotFoundException.class)
	public void shouldThrowExceptionOnNotExistentPrimaryKey() {

		// assume
		final String uuid = "21";

		// given
		given(service.findUserAccountByPrimaryKey(uuid)).willReturn(null);

		// when
		controller.findByPrimaryKey(uuid);

		// then
		fail("an excpetion was expected");

	}

	@Theory(nullsAccepted = false)
	public void shouldCreateAccount(final UserAccount account) {

		// assume

		// given
		given(service.createUserAccount(account)).willReturn(true);

		// when
		final Map<String, String> result = controller.createUserAccount(account, response);
		
		// then
		verify(service).createUserAccount(account);
		verify(response).setStatus(HttpServletResponse.SC_CREATED);
		assertNotNull("result should not be null", result);
		assertThat("result should not be empty", result.size(), is(equalTo(1)));
		assertThat("result should contain id", result.keySet(), hasItem("id"));

	}

	@Theory(nullsAccepted = false)
	@Test(expected = ResourceAlreadyExistsException.class)
	public void shouldThrowExceptionAtCreateOnExistingUsername(final UserAccount account) {

		// assume

		// given
		given(service.createUserAccount(account)).willReturn(false);

		// when
		controller.createUserAccount(account, response);
		
		// then
		fail("an exception was expected");

	}

	@Theory(nullsAccepted = false)
	public void shouldUpdateAccount(final UserAccount account) {

		// assume

		// given
		given(service.updateUserAccount(account)).willReturn(true);

		// when
		controller.updateUserAccount(account, response);
		
		// then
		verify(service).updateUserAccount(account);
		verify(response).setStatus(HttpServletResponse.SC_OK);

	}

	@Theory(nullsAccepted = false)
	@Test(expected = ResourceNotFoundException.class)
	public void shouldThrowExceptionAtUpdateOnNotExistingUserAccount(final UserAccount account) {

		// assume

		// given
		given(service.updateUserAccount(account)).willReturn(false);

		// when
		controller.updateUserAccount(account, response);
		
		// then
		fail("an exception was expected");

	}
	
}
