package edu.dandaev_it.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesUtil {
	private static final Properties PROPERTIES = new Properties();

	static{
		loadProperties();
	}

	public static String get(String key){
		return PROPERTIES.getProperty(key);
	}

	private static void loadProperties() {
		try (InputStream resourceAsStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
			PROPERTIES.load(resourceAsStream);
			PROPERTIES.list(System.out);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private PropertiesUtil(){
	}
}
