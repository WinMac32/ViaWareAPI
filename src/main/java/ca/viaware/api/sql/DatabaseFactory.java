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
package ca.viaware.api.sql;

import java.io.File;

import ca.viaware.api.logging.Log;
import ca.viaware.api.sql.exceptions.ViaWareSQLException;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;

public class DatabaseFactory {

	public Database getDatabase(String path) throws ViaWareSQLException {
		Log.info("Loading database \"" + path + "\"...");
		SQLiteConnection connection = new SQLiteConnection(new File(path));
		try {
			connection.open(true);
		} catch (SQLiteException e) {
			throw new ViaWareSQLException(e);
		}
		Log.info("Database loaded successfully.");
		return new Database(connection);
	}
	
}
