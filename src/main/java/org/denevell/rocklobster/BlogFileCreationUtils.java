package org.denevell.rocklobster;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import org.denevell.rocklobster.blogposts.BlogPost;
import org.denevell.rocklobster.templates.PageTemplate;
import org.denevell.rocklobster.templates.infrastructure.PageTemplateFactory;
import org.denevell.rocklobster.utils.FileUtils;

import java.io.File;


public class BlogFileCreationUtils {
	
	public static void createPosts(List<BlogPost> bps, List<PageTemplateFactory> factories) throws FileNotFoundException {
		// Get the file templates from the factories
		copyResourcesFolder();
		for (PageTemplateFactory factory : factories) {
			List<PageTemplate> templates = factory.generatePages(bps);
			// Output them to the fs
			for (PageTemplate fileTemplate : templates) {
				String filename = fileTemplate.getPostProcessedFilename();
				if(!Main.OUTPUT_DIR.endsWith("/")) Main.OUTPUT_DIR+="/";
				FileUtils.createDir(Main.OUTPUT_DIR);
				PrintWriter pw = new PrintWriter(Main.OUTPUT_DIR + filename);
				pw.print(fileTemplate.getContent());
				pw.close();
			}
		}	
	}

	public static void copyResourcesFolder() {
           try{
        	org.apache.commons.io.FileUtils.copyDirectoryToDirectory(new File("resources"),new File(Main.OUTPUT_DIR+="/"));
           }catch(Exception e){
        	e.printStackTrace();
           }

	}
}
