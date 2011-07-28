package net.skhome.roborace.domain.service;

import net.skhome.roborace.domain.model.UserAccount;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * The user service provides functionality to manage user accounts.
 *
 * @author Sascha Krueger
 */
public interface UserService {

	/**
	 * Returns the user account for the given username.
	 *
	 * @param username
	 * 		username
	 *
	 * @return user account or <code>null</code>
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	public UserAccount findUserAccountByUsername(String username);

	/**
	 * Returns the user account for the given primary key.
	 *
	 * @param uuid
	 * 		primary key as uuid
	 *
	 * @return user account or <code>null</code>
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	public UserAccount findUserAccountByPrimaryKey(String uuid);

	/**
	 * Checks if the given username is still available.
	 *
	 * @param username
	 * 		username to check
	 *
	 * @return <code>true</code> if that username is still available
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	public boolean isUsernameAvailable(String username);

	/**
	 * Creates a new user account.
	 *
	 * @return <code>true</code> if the account was created
	 */
	public void createUserAccount(UserAccount account);

	/**
	 * Update an existing user account.
	 *
	 * @return <code>true</code> if the account was updated
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	public void updateUserAccount(UserAccount account);

}
