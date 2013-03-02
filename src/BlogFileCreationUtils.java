import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import utils.FileUtils;

import entities.Page;


public class BlogFileCreationUtils {
	
	public static void createPosts(ArrayList<Page> pages) throws FileNotFoundException {
		for (Page page : pages) {
			String string = Main.sOutputDir + page.getFilename()+".html";
			FileUtils.createDir(Main.sOutputDir);
			PrintWriter pw = new PrintWriter(string);
			pw.print(page.getString());
			pw.close();
		}	
	}

}
