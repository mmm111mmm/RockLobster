import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import utils.FileUtils;
import entities.FileTemplate;


public class BlogFileCreationUtils {
	
	public static void createPosts(List<? extends FileTemplate> pages) throws FileNotFoundException {
		for (FileTemplate page : pages) {
			String filename = page.getPostProcessedFilename();
			FileUtils.createDir(Main.sOutputDir);
			PrintWriter pw = new PrintWriter(Main.sOutputDir + filename);
			pw.print(page.getContent());
			pw.close();
		}	
	}

}
