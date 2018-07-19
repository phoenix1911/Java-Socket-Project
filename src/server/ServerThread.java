package server;

import java.io.IOException;
import java.net.Socket;

public class ServerThread implements Runnable {
	private Socket socket;

	public ServerThread(Socket socket) {
		super();
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			HttpRequest request = new HttpRequest(socket);
			request.getRequestInfo();
			HttpResponse response = new HttpResponse(request,socket);
			response.sendResponse();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			try {
				if(socket != null)
					socket.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}

	}
}
