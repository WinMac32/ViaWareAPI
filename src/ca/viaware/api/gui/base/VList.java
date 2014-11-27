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

import javax.swing.BoxLayout;

@SuppressWarnings("serial")
public class VList extends VPanel {

	private VListElement currentClicked;
	
	public VList() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	public void setClicked(VListElement e) {
		if (currentClicked != null) currentClicked.setClicked(false);
		currentClicked = e;
	}
	
	public VListElement getAbove(VListElement below) {
		for (int i = 0; i < getComponentCount(); i++) {
			if (this.getComponent(i).equals(below)) {
				if (i > 0) return (VListElement) getComponent(i-1);
			}
		}
		
		return null;
	}
	
}
