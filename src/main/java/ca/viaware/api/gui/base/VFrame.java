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

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import ca.viaware.api.gui.base.event.CloseListener;

@SuppressWarnings("serial")
public class VFrame extends JFrame {

	private VPanel mainPanel;
	
	private CloseListener closeListener;
	
	public VFrame(String title, int width, int height) {
		this.mainPanel = new VPanel();
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
		this.setTitle(title);
		this.setSize(width, height);
		
		this.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {}
			@Override
			public void windowIconified(WindowEvent e) {}
			@Override
			public void windowDeiconified(WindowEvent e) {}
			@Override
			public void windowDeactivated(WindowEvent e) {}
			@Override
			public void windowClosing(WindowEvent e) {
				if (closeListener != null) closeListener.onClose();
			}
			@Override
			public void windowClosed(WindowEvent e) {}
			@Override
			public void windowActivated(WindowEvent e) {}
		});
	}
	
	public VFrame(String title) {
		this(title, 200, 200);
	}
	
	public VFrame() {
		this("");
	}
	
	public VPanel getMainPanel() {
		return mainPanel;
	}
	
	public void setCloseListener(CloseListener closeListener) {
		this.closeListener = closeListener;
	}
	
	
}
