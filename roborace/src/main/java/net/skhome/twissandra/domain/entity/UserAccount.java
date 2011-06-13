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

package net.skhome.twissandra.domain.entity;

import javax.validation.constraints.NotNull;

import net.skhome.common.domain.entity.AbstractEntity;

/**
 * Entity class representing a user account.
 *
 * @author Sascha Krueger (sascha@skhome.net)
 */
public class UserAccount extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	private String username;

	@NotNull
	private String password;

	public UserAccount() {
	}

	public UserAccount(String uuid, String username, String password) {
		super(uuid);
		this.username = username;
		this.password = password;
	}

	public final String getUsername() {
		return username;
	}

	public final void setUsername(final String username) {
		this.username = username;
	}

	public final String getPassword() {
		return password;
	}

	public final void setPassword(final String password) {
		this.password = password;
	}

}
