import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;

import utils.FileUtils;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import entities.BlogPost;
import entities.Page;


public class BlogTemplateUtils {

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

	public static ArrayList<Page> convertBlogPostToIndexPages(ArrayList<BlogPost> bps) {
		File[] pagesTemplates = FileUtils.getFilesInDirectory(new File("."), ".*\\.pages.template");
		String pageTemplateString = FileUtils.getStringFromFile(pagesTemplates[0].getAbsolutePath());
		// Apply mustache to the template;
	    MustacheFactory mf = new DefaultMustacheFactory();		
		Mustache mustache = mf.compile(new StringReader(pageTemplateString), "");
	    HashMap<String, Object> scopes = new HashMap<String, Object>();
	    scopes.put("posts", bps);
	    scopes.put("num_pages_total", "100");
	    scopes.put("num_pages_current", "1");
	    StringWriter writer = new StringWriter();	    
	    mustache.execute(writer, scopes);
	    writer.flush();
		System.out.println(writer.getBuffer().toString());
		return null;
	}


}
