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
package ca.viaware.api.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class CommandParser {

    private ArrayList<String> arguments;
    private HashMap<String, String> flags;

    public CommandParser(String[] args) {

        this.flags = new HashMap<String, String>();
        this.arguments = new ArrayList<String>();

        boolean inQuotes = false;
        boolean inFlag = false;

        String currFlag = "";
        String flagData = "";

        for (String arg : args) {
            if (inFlag) {
                if (arg.startsWith("-")) {
                    flags.put(currFlag, null);
                    currFlag = arg.substring(1);
                } else if (arg.equals("/")) {
                    flags.put(currFlag, null);
                    inFlag = false;
                } else {
                    flagData += arg.replace("\"", "") + " ";
                    if (arg.contains("\"")) {
                        inQuotes = !inQuotes;
                        if (!inQuotes) {
                            flags.put(currFlag, flagData.trim());
                            flagData = "";
                            inFlag = false;
                        }
                    } else {
                        flags.put(currFlag, flagData.trim());
                        flagData = "";
                        inFlag = false;
                    }
                }
            } else {
                if (arg.startsWith("-")) {
                    inFlag = true;
                    currFlag = arg.substring(1);
                } else {
                    arguments.add(arg);
                }
            }
        }
        if (inFlag) flags.put(currFlag, null);

    }

    public boolean hasFlag(String flag) {
        return flags.containsKey(flag);
    }

    public boolean flagHasData(String flag) {
        if (hasFlag(flag)) {
            return flags.get(flag) != null;
        } else return false;
    }

    public String getFlag(String flag) {
        return flags.get(flag);
    }

    public int getArgCount() {
        return arguments.size();
    }

    public String getArg(int index) {
        return arguments.get(index);
    }

    public String toString() {
        String s = "\n-- ARGS --\n";

        for (String arg : arguments) {
            s += arg + "\n";
        }

        s += "-- FLGS --\n";

        for (Entry<String,String> flag : flags.entrySet()) {
            s += flag.getKey() + " -> " + (flagHasData(flag.getKey()) ? flag.getValue() : "NO DATA") + "\n";
        }

        return s;
    }

}
