package org.denevell.rocklobster.entities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.denevell.rocklobster.utils.FileUtils;


public class PaginatedPageTemplateFactory extends FileTemplateFactory {
	
	public List<FileTemplate> generatePages(List<BlogPost> bps) {
		List<FileTemplate> fts = new ArrayList<FileTemplate>();
		File[] pagesTemplates = FileUtils.getFilesInDirectory(new File("."), ".*\\.\\d+\\.pagination.template");
		for (File pageTemplate : pagesTemplates) {
			int perPagePaginationNumber = getPaginationNumberFromFilename(pageTemplate.getName());
		    int totalPages = (int) Math.ceil((double)bps.size()/ (double)perPagePaginationNumber);
			List<FileTemplate> templateFiles = generateFileTemplatesForEachPage(bps, pageTemplate, perPagePaginationNumber, totalPages);
			fts.addAll(templateFiles);
		}
		return fts;
	}
	
	protected static int getPaginationNumberFromFilename(String absoluteFileName) {
		Pattern p = Pattern.compile(".*\\.(\\d+)\\.pagination.template");
		Matcher m = p.matcher(absoluteFileName);
		m.matches();
		String num = m.group(1);
		return Integer.valueOf(num);
	}

	protected static List<FileTemplate> generateFileTemplatesForEachPage(List<BlogPost> bps, File file, int perPagePaginationNumber, int totalPages) {
		List<FileTemplate> fts = new ArrayList<FileTemplate>();
		for(int currentPage = 1;((currentPage-1)*perPagePaginationNumber)<bps.size();currentPage++) {
			List<BlogPost> subList = getSublistForPagination(bps, perPagePaginationNumber, currentPage);
			FileTemplate ft = new PaginatedPageTemplate(file.getName(), subList, currentPage, totalPages);
			ft.generateContent();
			fts.add(ft);
		}
		return fts;
	}

	protected static List<BlogPost> getSublistForPagination(List<BlogPost> bps, int paginationSize, int currentPage) {
		int startPostNum = (currentPage-1)*paginationSize;
		int endPostNum = currentPage*paginationSize;
		if(endPostNum>bps.size()) endPostNum = bps.size();
		List<BlogPost> subList = bps.subList(startPostNum, endPostNum);
		return subList;
	}	

}
