package servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import server.HttpRequest;
import server.HttpResponse;

public class LoginServlet {
	private HttpRequest request;
	private HttpResponse response;

	public LoginServlet() {
		super();
	}

	public LoginServlet(HttpRequest request, HttpResponse response) {
		super();
		this.request = request;
		this.response = response;
	}

	public void service(HttpRequest request, HttpResponse response) throws Exception {
		System.out.println("service.............");
		Map<String, String> requestBodyMap = request.getRequestBodyMap();
		Set<Entry<String, String>> entrySet = requestBodyMap.entrySet();
		for (Entry<String, String> entry : entrySet) {
			System.out.println("service: " + entry.getKey() + " : " + entry.getValue());
		}
		if (requestBodyMap.get("pwd").equals("123") && requestBodyMap.get("name").equals("zs")) {
			System.out.println("µÇÂ½³É¹¦");
			response.setResourceFile("success.html");
		} else {
			System.out.println("µÇÂ½Ê§°Ü");
			response.setResourceFile("fail.html");
		}
		response.sendResponse();
	}

}
