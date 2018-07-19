package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class DynamicResourcesProperties {
	private static Properties properties;
	
	
	static{
		properties = new Properties();
		try {
			properties.load(DynamicResourcesProperties.class.getResourceAsStream("dynamicResources.properties"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	
	}
	
	static public String getValue(String key) {
		return properties.getProperty(key);
	}
	
//	test
	public static void main(String[] args) throws FileNotFoundException, IOException {
		System.out.println(DynamicResourcesProperties.getValue("loginServlet"));
		
	}
}
