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
package ca.viaware.api.gui.base;

@SuppressWarnings("serial")
public class VScrollingList extends VPanel {
	
	private VList list;
	private VScrollPane scrollPane;
	
	public VScrollingList() {
		list = new VList();
		scrollPane = new VScrollPane(list);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		add(scrollPane);
	}
	
	public void add(VListElement e) {
		list.add(e);
	}
	
	public VList getList() {
		return list;
	}
	
	public VScrollPane getScrollPane() {
		return scrollPane;
	}
}
