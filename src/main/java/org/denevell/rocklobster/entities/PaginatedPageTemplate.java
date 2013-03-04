package org.denevell.rocklobster.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.denevell.rocklobster.utils.FileUtils;


public class PaginatedPageTemplate extends FileTemplate {
	
	private String mTemplateFilename;
	private String mTemplateString;
	private int mTotalPages;
	private int mCurrentPage;
	private List<BlogPost> mBlogPosts;
	private String mPostProcessedFilname;
	private HashMap<String, Object> mScopes;
	private static final String PAGINATION_URL_LEADING_TEXT = ".";
	private static final String PAGINATION_URL_ENDING_TEXT = "";	

	/**
	 * Create PaginatedPageTemplates via PaginatedPageTemplateFactory, which will
	 * generate all the templates based on files found in the directory, containing
	 * the per page pagination metadata in the filename.
	 */
	public PaginatedPageTemplate(String templateFilename, List<BlogPost> blogPostSelection, int currentPage, int totalPages) {
		mTemplateFilename = templateFilename;
		mTemplateString = FileUtils.getStringFromFile(mTemplateFilename);
		mCurrentPage = currentPage;
		mTotalPages = totalPages;
		mBlogPosts = blogPostSelection;
		mPostProcessedFilname = getGeneratedPaginationFilename(mCurrentPage, mTemplateFilename);
		// Set template scopes
		mScopes = new HashMap<String, Object>();
		mScopes.put("posts", getBlogPosts());
		mScopes.put("num_pages_total", mTotalPages);
		mScopes.put("num_pages_current", String.valueOf(mCurrentPage));
		mScopes.put("next_page_relative_url", (mCurrentPage>=mTotalPages) ? false : getGeneratedPaginationFilename(mCurrentPage+1, mTemplateFilename));
		mScopes.put("previous_page_relative_url", (mCurrentPage<=1) ? false : getGeneratedPaginationFilename(mCurrentPage-1, mTemplateFilename));
	}

	@Override
	public List<BlogPost> getBlogPosts() {
		return mBlogPosts;
	}

	@Override
	public String getTemplate() {
		return mTemplateString;
	}

	@Override
	public Map<String, Object> getTemplateScopes() {
		return mScopes;		
	}

	@Override
	public String getPostProcessedFilename() {
		return mPostProcessedFilname;
	}
	
	public void setPostProcessedFilname(String postProcessedFilname) {
		this.mPostProcessedFilname = postProcessedFilname;
	}
	
	private String getGeneratedPaginationFilename(int currentPage, String relativeFileName) {
		String urlPageNumText = "";
		if(currentPage!=1) {
			urlPageNumText = PAGINATION_URL_LEADING_TEXT + String.valueOf(currentPage-1) + PAGINATION_URL_ENDING_TEXT;
		}
		return relativeFileName.replaceFirst("\\.\\d+\\.pagination\\.template", urlPageNumText+".html");
	}	

}
