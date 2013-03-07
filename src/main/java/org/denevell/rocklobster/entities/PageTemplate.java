package org.denevell.rocklobster.entities;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import org.denevell.rocklobster.plugins.infrastructure.TemplatePluginsContext;
import org.denevell.rocklobster.plugins.infrastructure.TemplatePluginsContextImpl;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

public abstract class PageTemplate implements TemplatePluginsContext {
	private String mContent;
	private TemplatePluginsContext mPluginContext;
	
	public PageTemplate(List<BlogPost> unfilteredBlogposts) {
		mPluginContext = new TemplatePluginsContextImpl(unfilteredBlogposts);
	}
	@Override
	public Map<String, Object> getTemplateScopes() { 
		return mPluginContext.getTemplateScopes();
	}
	public abstract List<BlogPost> getBlogPosts();
	public abstract String getTemplate();
	public abstract String getPostProcessedFilename();
	public void generateContent() {
	    MustacheFactory mf = new DefaultMustacheFactory();		
		Mustache mustache = mf.compile(new StringReader(getTemplate()), "");
	    StringWriter writer = new StringWriter();	    
	    Map<String, Object> templateScopes = getTemplateScopes();
		mustache.execute(writer, templateScopes);
	    writer.flush();	
	    mContent = writer.getBuffer().toString();
	}
	public String getContent() {
		return mContent;
	}
}
