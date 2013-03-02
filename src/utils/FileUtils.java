package utils;

import java.io.File;

public class FileUtils {

	public static void createDir(String dir) {
		File f = new File(dir);
		f.mkdir();
	}

}
