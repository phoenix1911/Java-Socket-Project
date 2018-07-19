package utils;

import java.io.IOException;
import java.util.Properties;

public class StateMessageProperties {
	private static Properties properties;
	
	
	static{
		properties = new Properties();
		try {
			properties.load(StateMessageProperties.class.getResourceAsStream("statemsg.properties"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	static public String getValue(String key) {
		return properties.getProperty(key);
	}
	
	//test
//	public static void main(String[] args) throws FileNotFoundException, IOException {
//		Properties properties = PropertiesUtils.getPathProperties();
//		System.out.println(properties.getProperty("WebResources"));
//		System.out.println(properties.getProperty("errorpage"));
//		System.out.println(properties.getProperty("welcomepage"));
//		
//		Properties properties2 = PropertiesUtils.getStatemsgProperties();
//		System.out.println(properties2.getProperty("200"));
//		System.out.println(properties2.getProperty("404"));
//	}
}
