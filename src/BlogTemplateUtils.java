import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import entities.BlogPost;
import entities.Page;


public class BlogTemplateUtils {
	


	private static String singlePostTemplate = 
    					"<html>" + "\n" +
    							"<head>" +" \n" +
	    							"<title>" +" \n" +
				    					"{{#attr}}{{Title}}{{/attr}}" + "\n" +
	    							"</title" +" \n" +
    							"</head>" +" \n" +
	    						"<body>" +" \n" +
		    						"<div class=\"blog-post\">" + "\n" +
	    								"{{& post}}" + "\n" +
		    						"<div>" + "\n" +
		    						"<div class=\"blog-tags\">" + "\n" +
				    					"Tags: {{#attr}}{{Tags}}{{/attr}}" +"\n" +
		    						"<div>" + "\n" +
		    						"<div class=\"blog-date\">" + "\n" +
				    					"Posted: {{#attr}}{{Date}}{{/attr}}" +"\n" +
		    						"<div>" + "\n" +
    							"</body>" + "\n" +
    					"</html>";

	public static ArrayList<Page> convertBlogPostToSinglePages(ArrayList<BlogPost> bps) throws IOException {
		ArrayList<Page> pages = new ArrayList<Page>();
		for (BlogPost blogPost : bps) {
		    MustacheFactory mf = new DefaultMustacheFactory();		
			Mustache mustache = mf.compile(new StringReader(singlePostTemplate), "");
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

}
