package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PathPropertiesUtils {
	private static Properties properties;
	
	
	static{
		properties = new Properties();
		try {
			properties.load(PathPropertiesUtils.class.getResourceAsStream("path.properties"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	static public String getValue(String key) {
		return properties.getProperty(key);
	}
	
	//test
	public static void main(String[] args) throws FileNotFoundException, IOException {
		String value = PathPropertiesUtils.getValue("welcomepage");
		System.out.println(value);
	}
}
