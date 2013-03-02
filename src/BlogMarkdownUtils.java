import java.util.ArrayList;

import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;

import entities.BlogPost;


public class BlogMarkdownUtils {

	public static void convertMDToHTML(ArrayList<BlogPost> bps) {
		for (BlogPost blogPost : bps) {
			String html = new PegDownProcessor(Extensions.ALL).markdownToHtml(blogPost.getPost());
			blogPost.setPost(html);
		}
	}
	
	
}
