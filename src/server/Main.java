package server;

import java.net.ServerSocket;
import java.net.Socket;

import utils.BasicConfigToolProperties;

public class Main {
	public static void main(String[] args) throws Exception {
		String port = BasicConfigToolProperties.getValue("port");
		ServerSocket serverSocket = new ServerSocket(Integer.parseInt(port));
		while (true) {
			Socket socket = serverSocket.accept();
			ServerThread serverThread = new ServerThread(socket);
			Thread thread = new Thread(serverThread);
			thread.start();
		}
	}
}
