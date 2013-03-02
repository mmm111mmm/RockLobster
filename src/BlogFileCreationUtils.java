import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import utils.FileUtils;
import entities.Page;


public class BlogFileCreationUtils {
	
	public static void createPosts(ArrayList<Page> pages) throws FileNotFoundException {
		for (Page page : pages) {
			String filename = page.getFilename();
			filename = filename.replace(".md", ".html");
			FileUtils.createDir(Main.sOutputDir);
			PrintWriter pw = new PrintWriter(Main.sOutputDir + filename);
			pw.print(page.getString());
			pw.close();
		}	
	}

}
