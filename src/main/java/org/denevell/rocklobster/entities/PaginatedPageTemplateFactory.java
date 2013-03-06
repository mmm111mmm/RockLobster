package org.denevell.rocklobster.entities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.denevell.rocklobster.utils.FileUtils;
import org.denevell.rocklobster.utils.PaginationUtils;


public class PaginatedPageTemplateFactory extends FileTemplateFactory {
	
	private File[] mPagesTemplates;

	public PaginatedPageTemplateFactory() {
		mPagesTemplates = FileUtils.getFilesInDirectory(new File("."), ".*[^\\]]\\.\\d+\\.pagination.template");
	}
	
	public List<FileTemplate> generatePages(List<BlogPost> bps) {
		List<FileTemplate> fts = new ArrayList<FileTemplate>();
		for (File pageTemplate : mPagesTemplates) {
			int perPagePaginationNumber = PaginationUtils.getPaginationNumberFromFilename(pageTemplate.getName());
		    int totalPages = PaginationUtils.getTotalPaginationPages(bps.size(), perPagePaginationNumber);
			List<FileTemplate> templateFiles = generateFileTemplatesForEachPage(bps, pageTemplate, perPagePaginationNumber, totalPages);
			fts.addAll(templateFiles);
		}
		return fts;
	}

	protected void setPagesTemplates(File[] mPagesTemplates) {
		this.mPagesTemplates = mPagesTemplates;
	}

	protected static List<FileTemplate> generateFileTemplatesForEachPage(List<BlogPost> bps, File file, int perPagePaginationNumber, int totalPages) {
		List<FileTemplate> fts = new ArrayList<FileTemplate>();
		for(int currentPage = 1;((currentPage-1)*perPagePaginationNumber)<bps.size();currentPage++) {
			List<BlogPost> subList = PaginationUtils.getSublistForPagination(bps, perPagePaginationNumber, currentPage);
			FileTemplate ft = new PaginatedPageTemplate(bps, file.getName(), subList, currentPage, totalPages);
			ft.generateContent();
			fts.add(ft);
		}
		return fts;
	}

}
