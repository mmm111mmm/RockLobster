package org.denevell.rocklobster.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.denevell.rocklobster.utils.FileUtils;


public class SinglePageTemplate extends FileTemplate {
	
	private BlogPost mBp;
	private String mPageTemplateString;
	
	/**
	 * @param bp
	 * You should call SinglePageTemplateFactory, since these objects, along with the other FileTemplate
	 * classes, are created via Factories. This this case all it does is specify the template file.
	 * But the other factories do exciting, exotic things, for various values of exotic.
	 * @param pageTemplateFile
	 */
	public SinglePageTemplate(BlogPost bp, String pageTemplateFile) {
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

}