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
package ca.viaware.api.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionListenerThread extends Thread {

	private ServerSocket server;
	private ConnectionHandler handler;
	
	public ConnectionListenerThread(int port, ConnectionHandler handler) {
		try {
			this.server = new ServerSocket(port);
			this.handler = handler;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		
		while (true) {
			try {
				Socket client = server.accept();
				new ConnectionHandlerRunnerThread(handler, client).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
