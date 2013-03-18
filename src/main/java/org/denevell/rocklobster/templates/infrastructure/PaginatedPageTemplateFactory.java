package org.denevell.rocklobster.templates.infrastructure;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.denevell.rocklobster.blogposts.BlogPost;
import org.denevell.rocklobster.templates.PageTemplate;
import org.denevell.rocklobster.templates.PaginatedPageTemplate;
import org.denevell.rocklobster.utils.FileUtils;
import org.denevell.rocklobster.utils.PaginationUtils;


public class PaginatedPageTemplateFactory extends PageTemplateFactory {
	
	private File[] mPagesTemplates;

	public PaginatedPageTemplateFactory() {
		mPagesTemplates = FileUtils.getFilesInDirectory(new File("."), ".*[^\\]]\\.\\d+\\.pagination.template.*");
	}
	
	public List<PageTemplate> generatePages(List<BlogPost> bps) {
		List<PageTemplate> fts = new ArrayList<PageTemplate>();
		for (File pageTemplate : mPagesTemplates) {
			int perPagePaginationNumber = PaginationUtils.getPaginationNumberFromFilename(pageTemplate.getName());
		    int totalPages = PaginationUtils.getTotalPaginationPages(bps.size(), perPagePaginationNumber);
			List<PageTemplate> templateFiles = generateFileTemplatesForEachPage(bps, pageTemplate, perPagePaginationNumber, totalPages);
			fts.addAll(templateFiles);
		}
		return fts;
	}

	protected void setPagesTemplates(File[] mPagesTemplates) {
		this.mPagesTemplates = mPagesTemplates;
	}

	protected static List<PageTemplate> generateFileTemplatesForEachPage(List<BlogPost> bps, File file, int perPagePaginationNumber, int totalPages) {
		List<PageTemplate> fts = new ArrayList<PageTemplate>();
		for(int currentPage = 1;((currentPage-1)*perPagePaginationNumber)<bps.size();currentPage++) {
			List<BlogPost> subList = PaginationUtils.getSublistForPagination(bps, perPagePaginationNumber, currentPage);
			PageTemplate ft = new PaginatedPageTemplate(bps, file.getName(), subList, currentPage, totalPages);
			ft.generateContent();
			fts.add(ft);
		}
		return fts;
	}

}
