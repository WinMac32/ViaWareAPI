/*
Copyright 2014 Seth Traverse

This file is part of ViaWare API.

ViaWare API is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

ViaWare API is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with ViaWare API.  If not, see <http://www.gnu.org/licenses/>.
 */
package ca.viaware.api.sql.factory;

import java.util.ArrayList;

import ca.viaware.api.logging.Log;
import ca.viaware.api.sql.Database;
import ca.viaware.api.sql.DatabaseResults;
import ca.viaware.api.sql.DatabaseRow;
import ca.viaware.api.sql.exceptions.ViaWareSQLException;
import ca.viaware.api.sql.factory.obj.DatabaseObject;

public abstract class BaseFactory<T extends DatabaseObject> {

	private ArrayList<T> objectCache;
	private String table;
	private Database database;
	
	/**
	 * Construct a base factory. Takes an initialized database and a table name.<p>
	 * @param database Initialized database
	 * @param table Table name
	 */
	public BaseFactory(Database database, String table) {
		objectCache = new ArrayList<T>();
		this.table = table;
		this.database = database;
	}
	
	/**
	 * Initialize the factory. Basically just creates the table if it doesn't exist.
	 */
	public void init() {
		Log.info("Initializing factory on table \"" + getTable() + "\"");
		generateTable();
	}
	
	public ArrayList<T> getCache() {
		return objectCache;
	}
	
	public Database getDatabase() {
		return database;
	}
	
	public String getTable() {
		return table;
	}
	
	/**
	 * The recommended method for adding objects to the database.<p>
	 * Automatically inserts and gets the generated primary key, returning
     * the object for convenience.
	 * @param object
	 */
	public T insertAndAdd(T object) {
		insert(object);
		int newId = getDatabase().getLastInsertedID();
		object.setId(newId);
		add(object);
        return object;
	}
	
	/**
	 * Add an object to the cache, does not call the insert function. <p>
	 * This function should only be used by subclasses to implement their own
	 * caching systems. This function does do a little bit of checking to try and
     * prevent uninitialized objects from going into the cache. Just don't do it.
	 * keys.
	 * @param object
	 */
	protected void add(T object){
		if (object.getId() == -1) {
			Log.error("Cannot add object into cache that is not initialized with a unique ID");
			return;
		}
		objectCache.add(object);
	}
	
	/**
	 * Get database object using unique primary key of column name 'id'<p>
	 * Tries the cache first, then goes on to try the database if it isn't in the cache
	 * @param id Unique primary key
	 * @return
	 */
	public T get(int id) {
		for (T object : objectCache) {
			if (object.getId() == id) {
				return object;
			}
		}
		//Not in cache, use database.
		try {
			DatabaseResults result = getDatabase().query("SELECT * FROM " + getTable() + " WHERE id=?", id);
			if (result.getRowCount() > 0) {
				T object = loadFromQuery(result.getRow(0));
				add(object);
				return object;
			}
		} catch (ViaWareSQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<T> getAll() {
		try {
			DatabaseResults result = getDatabase().query("SELECT * FROM " + getTable());
			objectCache.clear();
			for (int i = 0; i < result.getRowCount(); i++) {
				T object = loadFromQuery(result.getRow(i));
				add(object);
			}
		} catch (ViaWareSQLException e) {
			e.printStackTrace();
		}
		return objectCache;
	}
	
	/**
	 * Deletes all objects in the cache and in the database.<br>
	 * Use with caution, no going back after this!
	 */
	public void deleteAll() {
		Log.info("Deleting all objects managed by factory in table \"" + getTable() + "\"...");
		objectCache.clear();
		try {
			getDatabase().query("DELETE FROM " + getTable());
		} catch (ViaWareSQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Forces an update on all cached objects.
	 */
	public void saveAll() {
		Log.info("Forcing update on all cached objects...");
		for (T object : objectCache) {
			update(object);
		}
	}
	
	/**
	 * Prints the table using the DatabaseResults toString function<p>
	 * Warning: This is inefficient as it reads all data from the table
	 * without using the cache. This should only be used for debug purposes.
	 */
	public void printTable() {
		try {
			DatabaseResults results = getDatabase().query("SELECT * FROM " + getTable());
			System.out.println(results);
		} catch (ViaWareSQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Generate the table if it does not exist
	 */
	protected abstract void generateTable();
	
	/**
	 * Load a database object from query.
	 * @param row
	 * @return
	 */
	public abstract T loadFromQuery(DatabaseRow row);
	
	public abstract void update(T object);
	public abstract void insert(T object);
	
	public void remove(T object) {
		try {
			getDatabase().query("DELETE FROM " + getTable() + " WHERE id=?", object.getId());
			objectCache.remove(object);
		} catch (ViaWareSQLException e) {
			e.printStackTrace();
		}
	}
	
}
