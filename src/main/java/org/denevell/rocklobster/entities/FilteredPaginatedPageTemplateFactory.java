package org.denevell.rocklobster.entities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.denevell.rocklobster.utils.FileUtils;

public class FilteredPaginatedPageTemplateFactory extends PaginatedPageTemplateFactory {
	
	@Override
	public List<FileTemplate> generatePages(List<BlogPost> bps) {
		File[] pagesTemplate = FileUtils.getFilesInDirectory(new File("."), ".*\\[.*\\]\\.\\d+\\.pagination.template");
		for (File file : pagesTemplate) {
			// Set the template to the above 
			// Filter the blog posts based on the attribute from the file
			// Call the super method
			// Add FileTemplates to the list of FileTemplates to return
		}
		return new ArrayList<FileTemplate>();
	}
	
}
