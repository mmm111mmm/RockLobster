import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import entities.Page;


public class BlogFileCreationUtils {
	
	public static void createPosts(ArrayList<Page> pages) throws FileNotFoundException {
		for (Page page : pages) {
			PrintWriter pw = new PrintWriter(Main.sOutputDir + page.getFilename()+".html");
			pw.print(page.getString());
			pw.close();
		}	
	}
}
