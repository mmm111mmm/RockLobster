package org.denevell.rocklobster.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Pattern;

public class FileUtils {

	public static void createDir(String dir) {
		File f = new File(dir);
		f.mkdir();
	}
	
	public static String getStringFromFile (String fileLocation) {
		Scanner in = null;
		try {		
			File f = new File(fileLocation);
			if(f.exists()) {
				in = new Scanner(new FileReader(fileLocation));
				String s, str="";
				while(in.hasNext() && (s=in.nextLine())!=null) str+=s+"\n";
				return str;
			} else {
				return null; 
			}					
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if(in!=null) in.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}	

	public static String getStringFromRegexFile (String fileLocation) {
		File[] files = getFilesInDirectory(new File("."), fileLocation);
		File f = files[0];
		return getStringFromFile(f.getName());
	}	
	
	public static File[] getFilesInDirectory(File f, String regex) {
		final Pattern p = Pattern.compile(regex);
		File[] pagesTemplates = f.listFiles(new FileFilter() {
			@Override
			public boolean accept(File f) {
				return p.matcher(f.getName()).matches();
			}
		});
		return pagesTemplates;
	}

}
