package com.InternetRelayChat.chat.server;

import java.net.InetAddress;

//to store information about a client that is connected to us
public class ServerClient {
	public String name;
	public InetAddress address;
	public int port;

	private final int ID;// id for each client
	public int attempt = 0;// internet drops out server sends packet,no
							// reply,increment atttempt to send packet again!>5

	public ServerClient(String name, InetAddress address, int port, final int ID) {
		this.ID = ID;
		this.name = name;
		this.address = address;
		this.port = port;

	}

	public int getID() {
		return ID;
	}

}
