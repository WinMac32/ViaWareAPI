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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import ca.viaware.api.logging.Log;

@SuppressWarnings("serial")
public class VListElement extends VPanel {

	private boolean clicked = false;
	private boolean hovering = false;
	private VList parent;

	private String actionCommand;
	private ArrayList<ActionListener> clickListeners;
	private ArrayList<ActionListener> doubleClickListeners;
	
	private long lastClicked;
	
	public VListElement(final VList parent) {

		setOpaque(false);
		this.parent = parent;

		clickListeners = new ArrayList<ActionListener>();
		doubleClickListeners = new ArrayList<ActionListener>();
		lastClicked = System.currentTimeMillis();
		
		final VListElement instance = this;
		addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {

			}
			@Override
			public void mousePressed(MouseEvent e) {

			}
			@Override
			public void mouseExited(MouseEvent e) {
				hovering = false;
				repaint();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				hovering = true;
				repaint();
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				parent.setClicked(instance);
				setClicked(true);
				for (ActionListener listener : clickListeners) {
					listener.actionPerformed(new ActionEvent(instance, ActionEvent.ACTION_FIRST, actionCommand));
				}
				long currentTime = System.currentTimeMillis();
				if (currentTime - lastClicked < 400) {
					for (ActionListener listener : doubleClickListeners) {
						listener.actionPerformed(new ActionEvent(instance, ActionEvent.ACTION_FIRST, actionCommand));
					}
					currentTime = 0;
				}
				lastClicked = currentTime;
			}
		});

	}
	
	public VList getParentList() {
		return parent;
	}

	public void setActionCommand(String actionCommand) {
		this.actionCommand = actionCommand;
	}
	
	public void addActionListener(ActionListener listener) {
		clickListeners.add(listener);
	}
	
	public void addDoubleClickActionListener(ActionListener listener) {
		doubleClickListeners.add(listener);
	}

	public boolean isOdd() {
		return (parent.getAbove(this) != null ? !parent.getAbove(this).isOdd() : false);
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		if (clicked) {
			g2d.setColor(new Color(0x69A8FB));
			g2d.fillRect(0, 0, getWidth(), getHeight());
		} else {
			if (hovering) {
				g2d.setColor(new Color(0xADD1FF));
				g2d.fillRect(0, 0, getWidth(), getHeight());
			} else {
				g2d.setColor(new Color((isOdd() ? 0xF2F4F7 : 0xFFFFFF)));
				g2d.fillRect(0, 0, getWidth(), getHeight());
			}
			g2d.setColor(new Color(0xE5E5E5));
			g2d.drawLine(0, 0, getWidth(), 0);
		}
		super.paint(g);
	}

}
