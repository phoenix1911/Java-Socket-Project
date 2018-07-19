package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.sound.midi.VoiceStatus;

public class HttpRequest {

	private String requestMethod;
	private String requestLine;
	private String requestPath;
	private String requestFileName;
	private String requestHttpVersion;
	private Socket socket;
	private BufferedReader bufferedReader;
	private Map<String, String> requestHeadMap = new TreeMap<>();
	private Map<String, String> requestBodyMap = new TreeMap<>();

	public HttpRequest() {
		super();

	}

	public HttpRequest(Socket socket) {
		super();
		this.socket = socket;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()), 100);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void getRequestInfo() throws Exception {

		getRequestLine();
		getRequestHead();
		getRequestBody();
		System.out.println("**************");
	}

	public void getRequestBody() throws IOException {
		if ("GET".equals(requestMethod)) {
			return;
		} else if ("POST".equals(requestMethod)) {
			int r;
			char[] chars = new char[100];
			String string = "";
			bufferedReader.read(chars, 0, 100);
			for (char c : chars) {
				string += c;
			}
			System.out.println("ruquestbody : " + string);
			String[] split = string.split("&");
			for (String string2 : split) {
				String[] split2 = string2.split("=");
				requestBodyMap.put(split2[0], split2[1]);
			}
			// traverseMap(requestBodyMap);
			// System.out.println("getRequestBody结束");

		}
	}

	public void getRequestHead() throws IOException {

		String string;
		while (!(string = bufferedReader.readLine()).equals("")) {
			// 显示请求头信息
			// System.out.println(string);
			String[] split = string.split(": ");
			requestHeadMap.put(split[0], split[1]);
		}
		// traverseMap(requestBodyMap);
	}

	public void traverseMap(Map<String, String> map) {
		Set<Entry<String, String>> entrySet = map.entrySet();
		for (Entry<String, String> entry : entrySet) {
			System.out.println("key : " + entry.getKey() + "      " + "value : " + entry.getValue());
		}
	}

	public void getRequestLine() throws IOException {

		// 请求行分割
		requestLine = bufferedReader.readLine();
		String[] split = requestLine.split(" ");
		requestMethod = split[0];
		requestPath = split[1];
		requestHttpVersion = split[2];

		// 显示请求行信息
		// test requestLine分割
		for (int j = 0; j < split.length; j++) {
			System.out.println(split[j]);
		}
		requestFileName = split[1];
		if (requestPath.indexOf("?") != -1) {
			String[] split2 = requestPath.split("[?]");
			requestFileName = split2[0];
			for (String string : split2) {
				System.out.println("split2" + string);
			}
			String[] split3 = split2[1].split("&");
			for (String string2 : split3) {
				String[] split4 = string2.split("=");
				requestBodyMap.put(split4[0], split4[1]);
			}
		}
//		traverseMap(requestBodyMap);

	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public String getRequestFileName() {
		return requestFileName;
	}

	public String getRequestHttpVersion() {
		return requestHttpVersion;
	}

	public BufferedReader getBufferedReader() {
		return bufferedReader;
	}
	public Map<String, String> getRequestHeadMap() {
		return requestHeadMap;
	}

	public Map<String, String> getRequestBodyMap() {
		return requestBodyMap;
	}

}
