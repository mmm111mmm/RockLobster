package org.denevell.rocklobster.entities;

import java.util.List;


public class FilteredPaginatedPageTemplate extends PaginatedPageTemplate {

	public FilteredPaginatedPageTemplate(String templateFilename, List<BlogPost> blogPostSelection, int currentPage, int totalPages) {
		super(templateFilename, blogPostSelection, currentPage, totalPages);
	}

}
