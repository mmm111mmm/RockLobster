import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import utils.FileUtils;
import entities.ContentAndFilename;


public class BlogFileCreationUtils {
	
	public static void createPosts(List<? extends ContentAndFilename> pages) throws FileNotFoundException {
		for (ContentAndFilename page : pages) {
			String filename = page.getFilename();
			filename = filename.replace(".md", ".html");
			FileUtils.createDir(Main.sOutputDir);
			PrintWriter pw = new PrintWriter(Main.sOutputDir + filename);
			pw.print(page.getContent());
			pw.close();
		}	
	}

}
