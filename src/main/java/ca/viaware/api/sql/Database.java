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

import ca.viaware.api.logging.Log;
import ca.viaware.api.sql.exceptions.ViaWareSQLException;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

public class Database {

	protected SQLiteConnection connection;

	public Database(SQLiteConnection connection) {
		this.connection = connection;
	}

	public DatabaseResults query(String sql, Object... args) throws ViaWareSQLException {

		try {
			DatabaseResults results = new DatabaseResults();
			SQLiteStatement statement = connection.prepare(sql);

			for (int i = 0; i < args.length; i++) {
				if (args[i] instanceof Double) {
					statement.bind(i+1, (Double) args[i]);
				} else if (args[i] instanceof Integer) {
					statement.bind(i+1, (Integer) args[i]);
				} else if (args[i] instanceof String) {
					statement.bind(i+1, (String) args[i]);
				} else if (args[i] instanceof Long) {
					statement.bind(i+1, (Long) args[i]);
				} else {
					Log.error("Argument " + i + " is invalid type!");
				}
			}
			
			Log.info("SQL query: " + statement.toString());

			while (statement.step()) {
				DatabaseRow row = new DatabaseRow();
				for (int i = 0; i < statement.columnCount(); i++) {
					row.setColumn(statement.getColumnName(i), statement.columnValue(i));
				}
				results.addRow(row);
			}

			statement.dispose();

			return results;
		} catch (SQLiteException e) {
			throw new ViaWareSQLException(e);
		}
	}
	
	public int getLastInsertedID() {
		try {
			DatabaseResults results = query("SELECT last_insert_rowid()");
			return results.getRow(0).getInt(results.getRow(0).getColumNames().toArray()[0].toString());
		} catch (ViaWareSQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public void close() {
		connection.dispose();
	}

}
