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

import java.util.HashMap;
import java.util.Set;

public class DatabaseRow {

	private HashMap<String,Object> columns;
	
	public DatabaseRow() {
		columns = new HashMap<String,Object>();
	}
	
	public void setColumn(String col, Object val) {
		columns.put(col, val);
	}
	
	public double getDouble(String col) {
		return (Double) columns.get(col);
	}
	
	public long getLong(String col) {
		return (Long) columns.get(col);
	}
	
	public String getString(String col) {
		return (String) columns.get(col);
	}
	
	public String getStringForced(String col) {
		if (columns.get(col) instanceof Integer) {
			return Integer.toString(getInt(col));
		} else if (columns.get(col) instanceof Long) {
			return Long.toString(getLong(col));
		} else if (columns.get(col) instanceof Double) {
			return Double.toString(getDouble(col));
		} else if (columns.get(col) instanceof String) {
			return getString(col);
		}
		return "UNKNOWN TYPE";
	}
	
	public int getInt(String col) {
		return (Integer) columns.get(col);
	}
	
	public Set<String> getColumNames() {
		return columns.keySet();
	}
}
