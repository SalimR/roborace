package net.skhome.roborace.domain.repository;

import net.skhome.roborace.domain.model.BattlefieldMap;

/**
 * This repository provides CRUD functionalities for battlefield map information.
 *
 * @author Sascha Krüger (sascha@skhome.net)
 */
public interface BattlefieldMapRepository {

	/**
	 * Queries the repository to find the map with the given name. The name of the map must be unique within the repository.
	 *
	 * @param name
	 * 		name of the map
	 *
	 * @return map or <code>null</code> if no such map exists
	 */
	public BattlefieldMap findBattlefieldMapByName(String name);

	/**
	 * Queries the repository to find the map for the given unique primary key.
	 *
	 * @param uuid
	 * 		primary key of the map
	 *
	 * @return map or <code>null</code> if no such map exists
	 */
	public BattlefieldMap findBattlefieldMapById(String uuid);


	/**
	 * Creates a new map in the repository.
	 *
	 * @param map
	 * 		map to be created
	 *
	 * @throws org.springframework.dao.DuplicateKeyException
	 * 		if a map with that name already exists
	 */
	public void createBattlefieldMap(BattlefieldMap map);

	/**
	 * Updates the given map in the repository.
	 *
	 * @param map
	 * 		map to be updated
	 */
	public void updateBattlefieldMap(BattlefieldMap map);

	/**
	 * Checks if the given map name is available. Will return <code>false</code> if the given name is <code>null</code>.
	 *
	 * @param name
	 * 		name to be checked
	 *
	 * @return <code>true</code> if map name is available, <code>false</code> otherwise
	 */
	public boolean isNameAvailable(String name);

}
