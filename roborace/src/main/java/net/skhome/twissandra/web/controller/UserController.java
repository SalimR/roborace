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
package net.skhome.twissandra.web.controller;

import java.util.Collections;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.skhome.common.domain.ResourceAlreadyExistsException;
import net.skhome.common.domain.ResourceNotFoundException;
import net.skhome.twissandra.domain.entity.UserAccount;
import net.skhome.twissandra.domain.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller for REST support to manage user accounts. This controller provides different options to search for user
 * accounts as well as to create, update and delete them.
 * 
 * @author Sascha Krueger (sascha@skhome.net)
 */
@Controller
@RequestMapping(value = "/account")
public class UserController {

	private UserService service;

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
	public UserAccount findByPrimaryKey(@PathVariable("id") final String id) {

		final UserAccount account = service.findUserAccountByPrimaryKey(id);
		if (account != null) {
			return account;
		} else {
			throw new ResourceNotFoundException(id);
		}

	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> createUserAccount(@RequestBody @Valid final UserAccount account, final HttpServletResponse response) {

		if (service.createUserAccount(account)) {
			response.setStatus(HttpServletResponse.SC_CREATED);
			return Collections.singletonMap("id", account.getId());
		} else {
			throw new ResourceAlreadyExistsException(account.getUsername());
		}

	}

	@RequestMapping(method = RequestMethod.PUT)
	public void updateUserAccount(@RequestBody @Valid final UserAccount account, final HttpServletResponse response) {

		if (service.updateUserAccount(account)) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			throw new ResourceNotFoundException(account.getId());
		}

	}
}
