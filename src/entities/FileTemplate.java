package entities;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

public abstract class FileTemplate {
	private String mContent;
	public abstract List<BlogPost> getBlogPosts();
	public abstract String getTemplate();
	public abstract Map<String, Object> getTemplateScopes();
	public abstract String getPostProcessedFilename();
	public void generateContent() {
	    MustacheFactory mf = new DefaultMustacheFactory();		
		Mustache mustache = mf.compile(new StringReader(getTemplate()), "");
	    StringWriter writer = new StringWriter();	    
	    mustache.execute(writer, getTemplateScopes());
	    writer.flush();	
	    mContent = writer.getBuffer().toString();
	}
	public String getContent() {
		return mContent;
	}
}
