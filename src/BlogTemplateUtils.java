import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.FileUtils;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import entities.BlogPost;
import entities.Page;
import entities.PaginatedPage;


public class BlogTemplateUtils {

	private static final String PAGINATION_URL_LEADING_TEXT = ".";
	private static final String PAGINATION_URL_ENDING_TEXT = "";
	private static final String SINGLEPAGE_TEMPLATE_LOCATION = "singlepage.template";

	public static ArrayList<Page> convertBlogPostToSinglePages(ArrayList<BlogPost> bps) throws IOException {
		String pageTemplateString = FileUtils.getStringFromFile(SINGLEPAGE_TEMPLATE_LOCATION);
		if(pageTemplateString==null) throw new RuntimeException("No singlepages.template file found in CWD.");
		ArrayList<Page> pages = new ArrayList<Page>();
		for (BlogPost blogPost : bps) {
		    MustacheFactory mf = new DefaultMustacheFactory();		
			Mustache mustache = mf.compile(new StringReader(pageTemplateString), "");
		    HashMap<String, Object> scopes = new HashMap<String, Object>();
		    scopes.put("post", blogPost.getPost());
		    scopes.put("attr", blogPost.getMetadata());
		    StringWriter writer = new StringWriter();	    
		    mustache.execute(writer, scopes);
		    writer.flush();
		    pages.add(new Page(blogPost.getFilename(), writer.getBuffer().toString()));
		}
		return pages;
	}

	public static ArrayList<PaginatedPage> convertBlogPostToPaginatedPages(ArrayList<BlogPost> bps) {
		ArrayList<PaginatedPage> allPaginations = new ArrayList<PaginatedPage>();
		File[] pagesTemplates = FileUtils.getFilesInDirectory(new File("."), ".*\\.\\d+\\.pagination.template");
		for (File file : pagesTemplates) {
			int paginationNumber = getPaginationNumberFromFilename(file.getAbsolutePath());
			String pageTemplateString = FileUtils.getStringFromFile(file.getAbsolutePath());
			ArrayList<PaginatedPage> paginatedPages = applyPaginationTemplateToBlogPosts(bps, paginationNumber, file.getName(), pageTemplateString);
			allPaginations.addAll(paginatedPages);
		}
		return allPaginations;
	}

	private static int getPaginationNumberFromFilename(String absoluteFileName) {
		Pattern p = Pattern.compile(".*\\.(\\d+)\\.pagination.template");
		Matcher m = p.matcher(absoluteFileName);
		m.matches();
		String num = m.group(1);
		return Integer.valueOf(num);
	}

	private static ArrayList<PaginatedPage> applyPaginationTemplateToBlogPosts(ArrayList<BlogPost> bps, int paginationSize, String relativeFileName, String pageTemplateString) {
		ArrayList<PaginatedPage> pps = new ArrayList<PaginatedPage>();
	    int totalPages = (int) Math.ceil((double)bps.size() / (double)paginationSize);
	    MustacheFactory mf = new DefaultMustacheFactory();		
		Mustache mustache = mf.compile(new StringReader(pageTemplateString), "");
		for(int currentPage = 1;((currentPage-1)*paginationSize)<bps.size();currentPage++) {
			// Apply the template to this series of posts
			List<BlogPost> subList = getSublistForPagination(bps, paginationSize, currentPage);
			HashMap<String, Object> scopes = setScopeForPagination(totalPages, currentPage, subList, relativeFileName);
		    StringWriter writer = new StringWriter();	    
		    mustache.execute(writer, scopes);
		    writer.flush();
		    // Add this paginated page to the rest
			PaginatedPage pp = new PaginatedPage(getGeneratedPaginationFilename(currentPage, relativeFileName), currentPage, writer.getBuffer().toString());
			pps.add(pp);
		}
		return pps;
	}

	private static HashMap<String, Object> setScopeForPagination(int totalPages, int currentPage, List<BlogPost> subList, String relativeFilename) {
		HashMap<String, Object> scopes = new HashMap<String, Object>();
		scopes.put("posts", subList);
		scopes.put("num_pages_total", totalPages);
		scopes.put("num_pages_current", String.valueOf(currentPage));
		scopes.put("next_page_relative_url", (currentPage>=totalPages) ? false : getGeneratedPaginationFilename(currentPage+1, relativeFilename));
		scopes.put("previous_page_relative_url", (currentPage<=1) ? false : getGeneratedPaginationFilename(currentPage-1, relativeFilename));
		return scopes;
	}

	private static String getGeneratedPaginationFilename(int currentPage, String relativeFileName) {
		String urlPageNumText = "";
		if(currentPage!=1) {
			urlPageNumText = PAGINATION_URL_LEADING_TEXT + String.valueOf(currentPage-1) + PAGINATION_URL_ENDING_TEXT;
		}
		return relativeFileName.replaceFirst("\\.\\d+\\.pagination\\.template", urlPageNumText+".html");
	}

	private static List<BlogPost> getSublistForPagination( ArrayList<BlogPost> bps, int paginationSize, int currentPage) {
		int startPostNum = (currentPage-1)*paginationSize;
		int endPostNum = currentPage*paginationSize;
		if(endPostNum>bps.size()) endPostNum = bps.size();
		List<BlogPost> subList = bps.subList(startPostNum, endPostNum);
		return subList;
	}


}
