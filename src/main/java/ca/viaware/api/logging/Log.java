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
package ca.viaware.api.logging;

import java.util.Calendar;

public class Log {

	public static void info(String msg) {
		System.out.println("[" + getTime() + "][INFO] " + msg);
	}
	
	public static void info(String msg, Object... args) {
		for (int i = 0; i < args.length; i++) {
			msg = msg.replace("%" + i, "\"" + args[i].toString() + "\"");
		}
		info(msg);
	}
	
	public static void error(String msg) {
		System.err.println("[" + getTime() + "][ERROR] " + msg);
	}
	
	public static void error(String msg, Object... args) {
		for (int i = 0; i < args.length; i++) {
			msg = msg.replace("%" + i, "\"" + args[i].toString() + "\"");
		}
		error(msg);
	}
	
	private static String getTime() {
		
		Calendar cal = Calendar.getInstance();
		
		return "" + cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
	}
	
}
