package org.denevell.rocklobster.templates;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.lang.StringBuffer;

import org.denevell.rocklobster.Main;
import org.denevell.rocklobster.blogposts.BlogPost;
import org.denevell.rocklobster.utils.FileUtils;


public class SinglePageTemplate extends PageTemplate {
	
	private BlogPost mBp;
	private String mPageTemplateString;
	private String mTemplateSuffix;
	
	/**
	 * @param bp
	 * You should call SinglePageTemplateFactory, since these objects, along with the other FileTemplate
	 * classes, are created via Factories. This this case all it does is specify the template file.
	 * But the other factories do exciting, exotic things, for various values of exotic.
	 * @param pageTemplateFile
	 */
	public SinglePageTemplate(List<BlogPost> allBlogposts, BlogPost bp, String pageTemplateFile, String fileSuffix) {
		super(allBlogposts);
		mBp = bp;
		mPageTemplateString = FileUtils.getStringFromRegexFile(pageTemplateFile);
		mTemplateSuffix = fileSuffix;
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
		    Map<String, Object> scopes = super.getTemplateScopes();
		    BlogPost blogPost = getBlogPosts().get(0);
			scopes.put("post", blogPost.getPost());
		    scopes.put("attr", blogPost.getMetadata());
		return scopes;
	}

	@Override
	public String getPostProcessedFilename() {
		String filename = mBp.getFilename().replace(Main.CONTENT_FILE_SUFFIX, "." + mTemplateSuffix); 
		return filename;
	}

}
