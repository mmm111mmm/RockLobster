package org.denevell.rocklobster.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.denevell.rocklobster.Main;

public class LogUtils {
	
	public static Logger getLog(Class<?> c) {
		try {
			InputStream resourceAsStream = Main.class.getResourceAsStream("/log4j.properties");
			Reader isr = new InputStreamReader(resourceAsStream);
			Properties p = new Properties();
			p.load(isr);
			PropertyConfigurator.configure(p);
			Logger l = Logger.getLogger(c);
			return l;
		} catch (IOException e) {
			e.printStackTrace();
			return Logger.getLogger(c); 
		}
	}

}
