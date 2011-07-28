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

import net.skhome.common.domain.ResourceAlreadyExistsException;
import net.skhome.common.domain.ResourceNotFoundException;
import net.skhome.roborace.domain.model.UserAccount;
import net.skhome.roborace.domain.service.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

/**
 * Controller for REST support to manage user accounts. This controller provides different options to search for user accounts
 * as well as to create, update and delete them.
 *
 * @author Sascha Krueger (sascha@skhome.net)
 */
@Controller
@RequestMapping(value = "/account")
public class UserController {

	private final UserService service;

	@Inject
	public UserController(final UserService service) {
		super();
		this.service = service;
	}

	@RequestMapping(value = "/username/{username}", method = RequestMethod.GET)
	@ResponseBody
	public UserAccount findByUsername(@PathVariable("username") final String username) {

		final UserAccount account = service.findUserAccountByUsername(username);
		if (account != null) {
			return account;
		} else {
			throw new ResourceNotFoundException(username);
		}

	}

	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	@ResponseBody
	public UserAccount findByPrimaryKey(@PathVariable("id") final String uuid) {

		final UserAccount account = service.findUserAccountByPrimaryKey(uuid);
		if (account != null) {
			return account;
		} else {
			throw new ResourceNotFoundException(uuid);
		}

	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> createUserAccount(@RequestBody @Valid final UserAccount account,
	                                             final HttpServletResponse response) {

		try {
			service.createUserAccount(account);
			response.setStatus(HttpServletResponse.SC_CREATED);
			return Collections.singletonMap("id", account.getId());
		} catch (DataAccessException ex) {
			throw new ResourceAlreadyExistsException(account.getUsername());
		}

	}

	@RequestMapping(method = RequestMethod.PUT)
	public void updateUserAccount(@RequestBody @Valid final UserAccount account, final HttpServletResponse response) {

		try {
			service.updateUserAccount(account);
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (DataAccessException ex) {
			throw new ResourceNotFoundException(account.getId());
		}

	}
}
