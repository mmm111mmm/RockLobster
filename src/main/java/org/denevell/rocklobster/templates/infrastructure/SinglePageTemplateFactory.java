package org.denevell.rocklobster.templates.infrastructure;

import java.util.ArrayList;
import java.util.List;

import org.denevell.rocklobster.blogposts.BlogPost;
import org.denevell.rocklobster.templates.PageTemplate;
import org.denevell.rocklobster.templates.SinglePageTemplate;

public class SinglePageTemplateFactory extends PageTemplateFactory {
	
	private static final String SINGLEPAGE_TEMPLATE_LOCATION = "singlepage.template";
	
	@Override
	public List<PageTemplate> generatePages(List<BlogPost> bps) {
		List<PageTemplate> fts = new ArrayList<PageTemplate>();
		for (BlogPost blogPost : bps) {
			SinglePageTemplate singlePageTemplate = new SinglePageTemplate(bps, blogPost, SINGLEPAGE_TEMPLATE_LOCATION);
			singlePageTemplate.generateContent();
			fts.add(singlePageTemplate);
		}
		return fts;		
	}

}
