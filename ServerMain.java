package com.InternetRelayChat.chat.server;

public class ServerMain {
	private int port;
	private Server server;

	public ServerMain(int port) {
		this.port = port;
		server = new Server(port);

	}

	public static void main(String[] args) {
		int port;
		if (args.length != 1) {
			System.out.println("User:  java -jar InternetRelayChat.jar [port]");
			return;
		}
		port = Integer.parseInt(args[0]);// deynamically given by the user
		new ServerMain(port);
	}
}
