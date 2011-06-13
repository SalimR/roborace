package net.skhome.twissandra.domain.service;

import net.skhome.twissandra.domain.entity.UserAccount;

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
	 * @param username username
	 * 
	 * @return user account or <code>null</code>
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	public UserAccount findUserAccountByUsername(String username); 
	
	/**
	 * Returns the user account for the given primary key.
	 * 
	 * @param id primary key as uuid
	 * 
	 * @return user account or <code>null</code>
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	public UserAccount findUserAccountByPrimaryKey(String id);

	/**
	 * Checks if the given username is still available.
	 * 
	 * @param username username to check
	 * 
	 * @return <code>true</code> if that username is still available
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	public boolean isUsernameAvailable(String username); 
	
	/**
	 * Creates a new user account.
	 * 
	 * @param userAccount user account
	 * 
	 * @return <code>true</code> if the account was created
	 */
	public boolean createUserAccount(UserAccount account);
	
	/**
	 * Update an existing user account.
	 * 
	 * @param userAccount user account
	 * 
	 * @return <code>true</code> if the account was updated
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	public boolean updateUserAccount(UserAccount account);
	
}
