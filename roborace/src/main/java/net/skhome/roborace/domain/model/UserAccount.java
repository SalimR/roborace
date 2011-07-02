package net.skhome.roborace.domain.model;

import javax.validation.constraints.NotNull;

import net.skhome.common.domain.model.AbstractEntity;

/**
 * Entity class representing a user account.
 * 
 * @author Sascha Krueger (sascha@skhome.net)
 */
public class UserAccount extends AbstractEntity {

	private static final long serialVersionUID = 4700863184147200202L;

	@NotNull
	private String username;

	@NotNull
	private String password;

	/**
	 * Create a new user account for the given credentials.
	 * 
	 * @param username
	 *            login name
	 * @param password
	 *            password (hopefully encrypted)
	 */
	public UserAccount(final String username, final String password) {
		this(null, username, password);
	}

	/**
	 * Create a new user account for the given credentials.
	 * 
	 * @param uuid
	 *            primary key from repository
	 * @param username
	 *            login name
	 * @param password
	 *            password (hopefully encrypted)
	 */
	public UserAccount(final String uuid, final String username, final String password) {
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
