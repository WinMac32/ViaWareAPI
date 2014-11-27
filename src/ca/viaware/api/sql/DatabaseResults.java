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

import java.util.ArrayList;

import ca.viaware.api.utils.StringUtils;

public class DatabaseResults {

	private ArrayList<DatabaseRow> rows;
	
	public DatabaseResults() {
		rows = new ArrayList<DatabaseRow>();
	}
	
	public void addRow(DatabaseRow row) {
		rows.add(row);
	}
	
	public DatabaseRow getRow(int row) {
		return rows.get(row);
	}
	
	public int getRowCount() {
		return rows.size();
	}
	
	public String toString() {
		String output = "";
		if (getRowCount() > 0) {
			String separator = StringUtils.repeatLength("-", getRow(0).getColumNames().size() * 20);
			output += separator + "\n";
			output += StringUtils.padMonospacedCenter("Database Results", separator.length()) + "\n";
			output += separator + "\n";
			for (String col : getRow(0).getColumNames()) {
				output += StringUtils.padMonospaced(col, 20);
			}
			output += "\n";
			output += separator + "\n";
			for (DatabaseRow row : rows) {
				for (String col : row.getColumNames()) {
					output += StringUtils.padMonospaced(row.getStringForced(col), 20);
				}
				output += "\n";
			}
			output += separator;
		} else {
			output += "NO DATA IN THIS TABLE YET";
		}
		return output;
	}
	
}
