import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import utils.FileUtils;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import entities.BlogPost;
import entities.Page;


public class BlogTemplateUtils {
	


	private static final String SINGLEPAGE_TEMPLATE_LOCATION = "singlepage.template";
	private static final String sSinglePostTemplate = 
    					"<html>" + "\n" +
    							"<head>" +" \n" +
	    							"<title>" +" \n" +
				    					"{{#attr}}{{title}}{{/attr}}" + "\n" +
	    							"</title" +" \n" +
    							"</head>" +" \n" +
	    						"<body>" +" \n" +
		    						"<div class=\"blog-post\">" + "\n" +
	    								"{{& post}}" + "\n" +
		    						"<div>" + "\n" +
		    						"<div class=\"blog-tags\">" + "\n" +
				    					"Tags: {{#attr}}{{tags}}{{/attr}}" +"\n" +
		    						"<div>" + "\n" +
		    						"<div class=\"blog-date\">" + "\n" +
				    					"Posted: {{#attr}}{{date}}{{/attr}}" +"\n" +
		    						"<div>" + "\n" +
    							"</body>" + "\n" +
    					"</html>";

	public static ArrayList<Page> convertBlogPostToSinglePages(ArrayList<BlogPost> bps) throws IOException {
		//Get the template from, optionally, the dir the program is running in.
		String pageTemplateString = "";
		if((pageTemplateString=FileUtils.getStringFromFile(SINGLEPAGE_TEMPLATE_LOCATION))==null) {
			pageTemplateString = sSinglePostTemplate;
		}
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
		File f = new File(".");
		File[] pagesTemplates = FileUtils.getFilesInDirectory(f, ".*\\.pages.template");
		for (File file : pagesTemplates) {
			System.out.println(file);
		}
		return null;
	}


}
