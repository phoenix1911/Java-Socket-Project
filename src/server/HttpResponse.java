package server;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.security.KeyStore.PrivateKeyEntry;

import servlet.LoginServlet;
import utils.DynamicResourcesProperties;
import utils.PathPropertiesUtils;
import utils.StateMessageProperties;

public class HttpResponse {

	private HttpRequest request;
	private Socket socket;
	private PrintStream printStream;
	private File file;
	private String statemsg;
	private FileReader fileReader;
	private String resourceFile;

	public HttpResponse(HttpRequest request, Socket socket) throws IOException {
		this.resourceFile = request.getRequestFileName();
		// 获取输出流
		this.printStream = new PrintStream(socket.getOutputStream());
		this.request = request;
		this.socket = socket;
	}

	public void sendResponse() throws IOException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
		System.out.println("in sendResponse...");
		
		//如果是动态资源使用动态资源的方式处理 return跳出方法
		String substring = resourceFile.substring(1);
		System.out.println(substring);
		String classname = DynamicResourcesProperties.getValue(substring);
		System.out.println(classname);
		if (classname!=null) {
			//反射得到字节码对象
			Class<?> class1 = Class.forName(classname);
			Method method = class1.getDeclaredMethod("service",HttpRequest.class,HttpResponse.class);
			Object newInstance = class1.newInstance();
			method.invoke(newInstance,request, this);
			return;
		}
		
		
		
		
		
		file = new File(PathPropertiesUtils.getValue("WebResources") + "\\" + resourceFile);
		System.out.println(file.getAbsolutePath());
		System.out.println(file.exists());
		if (resourceFile.equals("/")) {
			statemsg = StateMessageProperties.getValue("200");
			fileReader = new FileReader(file = new File(PathPropertiesUtils.getValue("welcomepage")));
		} else if (file.exists()) {
			statemsg = StateMessageProperties.getValue("200");
			fileReader = new FileReader(file);
		} else {
			statemsg = StateMessageProperties.getValue("400");
			fileReader = new FileReader(file = new File(PathPropertiesUtils.getValue("errorpage")));
		}
		send(fileReader);
		
		printStream.close();
	}


	public void send(FileReader fileReader) throws IOException {
		int r;
		String string = "";
		while ((r = fileReader.read()) != -1) {
			string += (char) r + "";
		}
		printStream.println(request.getRequestHttpVersion() + " " + statemsg);
		printStream.println("Content-type:text/html");
		printStream.println();
		printStream.println(string);
		fileReader.close();
	}

	public void setResourceFile(String resourceFile) {
		this.resourceFile = resourceFile;
	}
}