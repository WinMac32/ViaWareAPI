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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

@SuppressWarnings({ "unused", "serial" })
public class VButton extends JButton {

	private Color activeBackground;
	private Color hoverBackground;
	private Color selectedBackground;
	private String text;
	
	private boolean mouseOver;
	private boolean mouseClicking;

    public VButton(String text) {
        super(text);
    }

    public VButton(String text, ActionListener listener, String actionCommand) {
        this(text);
        addActionListener(listener);
        setActionCommand(actionCommand);
    }

    public VButton() {
        this("");
    }

}
