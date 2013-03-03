package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.FileUtils;

public class SinglePageTemplate extends FileTemplate {
	
	private BlogPost mBp;
	private String mPageTemplateString;
	private static final String SINGLEPAGE_TEMPLATE_LOCATION = "singlepage.template";
	
	private SinglePageTemplate(BlogPost bp, String pageTemplateFile) {
		mBp = bp;
		mPageTemplateString = FileUtils.getStringFromFile(pageTemplateFile);
		if(mPageTemplateString==null) throw new RuntimeException("No singlepages.template file found in CWD.");
	}

	@Override
	public List<BlogPost> getBlogPosts() {
		ArrayList<BlogPost> a = new ArrayList<BlogPost>();
		a.add(mBp);
		return a;
	}

	@Override
	public String getTemplate() {
		return mPageTemplateString;
	}

	@Override
	public Map<String, Object> getTemplateScopes() {
		    HashMap<String, Object> scopes = new HashMap<String, Object>();
		    BlogPost blogPost = getBlogPosts().get(0);
			scopes.put("post", blogPost.getPost());
		    scopes.put("attr", blogPost.getMetadata());
		return scopes;
	}

	@Override
	public String getPostProcessedFilename() {
		String filename = mBp.getFilename().replace(".md", ".html");
		return filename;
	}

	public static List<FileTemplate> generatePageTemplates(List<BlogPost> bps) {
		List<FileTemplate> fts = new ArrayList<FileTemplate>();
		for (BlogPost blogPost : bps) {
			fts.add(new SinglePageTemplate(blogPost, SINGLEPAGE_TEMPLATE_LOCATION));
		}
		return fts;
	}

}
