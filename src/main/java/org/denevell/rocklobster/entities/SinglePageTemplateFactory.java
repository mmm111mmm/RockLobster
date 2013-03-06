package org.denevell.rocklobster.entities;

import java.util.ArrayList;
import java.util.List;

public class SinglePageTemplateFactory extends FileTemplateFactory {
	
	private static final String SINGLEPAGE_TEMPLATE_LOCATION = "singlepage.template";
	
	@Override
	public List<FileTemplate> generatePages(List<BlogPost> bps) {
		List<FileTemplate> fts = new ArrayList<FileTemplate>();
		for (BlogPost blogPost : bps) {
			SinglePageTemplate singlePageTemplate = new SinglePageTemplate(bps, blogPost, SINGLEPAGE_TEMPLATE_LOCATION);
			singlePageTemplate.generateContent();
			fts.add(singlePageTemplate);
		}
		return fts;		
	}

}
