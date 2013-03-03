package org.denevell.rocklobster.entities;

import java.util.List;



public class FilteredPaginatedPageTemplateFactory extends PaginatedPageTemplateFactory {
	
	@Override
	public List<FileTemplate> generatePages(List<BlogPost> bps) {
		// Based on filename, filter the blog posts
		return super.generatePages(bps);
	}

}
