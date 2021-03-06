package com.InternetRelayChat.chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {

	private static final long serialVersionUID = 1L;
	private int ID = -1;
	private String name, address;
	private int port;

	private DatagramSocket socket;
	private InetAddress ip;
	private Thread send;

	public Client(String name, String address, int port) {
		this.name = name;
		this.address = address;
		this.port = port;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}

	public boolean openConnection(String address, int port) {
		try {
			socket = new DatagramSocket();// contruct us datagram socket based
											// on that port
			ip = InetAddress.getByName(address);
		}  catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
		// return rootPaneCheckingEnabled;

	}

	public String receive() {
		byte[] data = new byte[1024];
		DatagramPacket packet = new DatagramPacket(data, data.length);
		try {
			socket.receive(packet);// will freeze our application ..works as a
									// while loop. .get going until it gets some
									// data from the network
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String message = new String(packet.getData());
		return message;

	}

	public void send(final byte[] data) {
		send = new Thread("send") {
			public void run() {
				DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
				try {
					socket.send(packet);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
		send.start();
	}
	public void close(){
		new Thread (){
		public void run(){
			synchronized(socket){
				socket.close();
		}
		}
		
		}.start();
	}

	public void setID(int ID) {

		this.ID = ID;
	}

	public int getID() {
		return ID;
	}

}
