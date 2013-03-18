package org.denevell.rocklobster.templates.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.io.File;

import org.denevell.rocklobster.blogposts.BlogPost;
import org.denevell.rocklobster.templates.PageTemplate;
import org.denevell.rocklobster.templates.SinglePageTemplate;
import org.denevell.rocklobster.utils.FileUtils;

public class SinglePageTemplateFactory extends PageTemplateFactory {
	
	
	@Override
	public List<PageTemplate> generatePages(List<BlogPost> bps) {
		List<PageTemplate> fts = new ArrayList<PageTemplate>();
		for (BlogPost blogPost : bps) {
			File[] files = FileUtils.getFilesInDirectory(new File("."), ".*singlepage.template.*");
			File f = files[0];
			String fileName = f.getName();
			String fileNameReversed = new StringBuffer(fileName).reverse().toString();
			String fileSuffix = fileNameReversed.subSequence(0, fileNameReversed.indexOf('.')).toString();
			fileSuffix = new StringBuffer(fileSuffix).reverse().toString();
			SinglePageTemplate singlePageTemplate = new SinglePageTemplate(bps, blogPost, fileName, fileSuffix);
			singlePageTemplate.generateContent();
			fts.add(singlePageTemplate);
		}
		return fts;		
	}

}
