import java.util.List;

import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;

import entities.BlogPost;


public class BlogMarkdownUtils {

	public static void convertMDToHTML(List<BlogPost> bps) {
		for (BlogPost blogPost : bps) {
			String html = new PegDownProcessor(Extensions.ALL).markdownToHtml(blogPost.getPost());
			blogPost.setPost(html);
		}
	}
	
	
}
