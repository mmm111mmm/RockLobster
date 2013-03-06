package org.denevell.rocklobster.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.denevell.rocklobster.entities.BlogPost;

public class PaginationUtils {
	
	public static int getPaginationNumberFromFilename(String fileName) {
		Pattern p = Pattern.compile(".*\\.(\\d+)\\.pagination.template");
		Matcher m = p.matcher(fileName);
		m.matches();
		String num = m.group(1);
		return Integer.valueOf(num);
	}

	public static List<BlogPost> getSublistForPagination(List<BlogPost> bps, int paginationSize, int currentPage) {
		int startPostNum = (currentPage-1)*paginationSize;
		int endPostNum = currentPage*paginationSize;
		if(endPostNum>bps.size()) endPostNum = bps.size();
		List<BlogPost> subList = bps.subList(startPostNum, endPostNum);
		return subList;
	}	
	
	public static int getTotalPaginationPages(int numOfBlogposts, int perPagePaginationNumber) {
		return (int) Math.ceil((double)numOfBlogposts/ (double)perPagePaginationNumber);
	}
	
	public static String getMetadataAttributeFromFilename(String filename) {
		Pattern p = Pattern.compile(".*\\[(.*)\\].*");
		Matcher m = p.matcher(filename);
		m.matches();
		String metadataAttribute = m.group(1);
		return metadataAttribute;
	}

}
