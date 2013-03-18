package org.denevell.rocklobster.templates;

import java.util.List;
import java.util.Map;

import org.denevell.rocklobster.blogposts.BlogPost;
import org.denevell.rocklobster.utils.FileUtils;


public class PaginatedPageTemplate extends PageTemplate {
	
	private String mTemplateFilename;
	private String mTemplateString;
	private int mTotalPages;
	private int mCurrentPage;
	private List<BlogPost> mBlogPosts;
	private static final String PAGINATION_URL_LEADING_TEXT = ".";
	private static final String PAGINATION_URL_ENDING_TEXT = "";	

	/**
	 * Create PaginatedPageTemplates via PaginatedPageTemplateFactory, which will
	 * generate all the templates based on files found in the directory, containing
	 * the per page pagination metadata in the filename.
	 */
	public PaginatedPageTemplate(List<BlogPost> allBlogposts, String templateFilename, List<BlogPost> blogPostSelection, int currentPage, int totalPages) {
		super(allBlogposts);
		mTemplateFilename = templateFilename;
		mTemplateString = FileUtils.getStringFromFile(mTemplateFilename);
		mCurrentPage = currentPage;
		mTotalPages = totalPages;
		mBlogPosts = blogPostSelection;
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
		Map<String, Object> scopes = super.getTemplateScopes();
		scopes.put("posts", getBlogPosts());
		scopes.put("num_pages_total", mTotalPages);
		scopes.put("num_pages_current", String.valueOf(mCurrentPage));
		scopes.put("next_page_relative_url", (mCurrentPage>=mTotalPages) ? false : getGeneratedPaginationFilename(mCurrentPage+1, mTemplateFilename));
		scopes.put("previous_page_relative_url", (mCurrentPage<=1) ? false : getGeneratedPaginationFilename(mCurrentPage-1, mTemplateFilename));
		return scopes;
	}

	@Override
	public String getPostProcessedFilename() {
		return getGeneratedPaginationFilename(mCurrentPage, mTemplateFilename);
	}
	
	protected String getGeneratedPaginationFilename(int currentPage, String relativeFileName) {
		String urlPageNumText = "";
		if(currentPage!=1) {
			urlPageNumText = PAGINATION_URL_LEADING_TEXT + String.valueOf(currentPage) + PAGINATION_URL_ENDING_TEXT;
		}
		return relativeFileName.replaceFirst("\\.\\d+\\.pagination\\.template", urlPageNumText);
	}	

}
