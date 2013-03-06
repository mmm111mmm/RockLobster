package org.denevell.rocklobster;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import org.denevell.rocklobster.entities.BlogPost;
import org.denevell.rocklobster.entities.PageTemplate;
import org.denevell.rocklobster.entities.PageTemplateFactory;
import org.denevell.rocklobster.utils.FileUtils;


public class BlogFileCreationUtils {
	
	public static void createPosts(List<BlogPost> bps, List<PageTemplateFactory> factories) throws FileNotFoundException {
		// Get the file templates from the factories
		for (PageTemplateFactory factory : factories) {
			List<PageTemplate> templates = factory.generatePages(bps);
			// Output them to the fs
			for (PageTemplate fileTemplate : templates) {
				String filename = fileTemplate.getPostProcessedFilename();
				FileUtils.createDir(Main.sOutputDir);
				PrintWriter pw = new PrintWriter(Main.sOutputDir + filename);
				pw.print(fileTemplate.getContent());
				pw.close();
			}
		}	
	}

}
