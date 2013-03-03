package org.denevell.rocklobster;
import java.util.List;

import org.denevell.rocklobster.entities.BlogPost;
import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;



public class BlogMarkdownUtils {

	public static void convertMDToHTML(List<BlogPost> bps) {
		for (BlogPost blogPost : bps) {
			String html = new PegDownProcessor(Extensions.ALL).markdownToHtml(blogPost.getPost());
			blogPost.setPost(html);
		}
	}
	
	
}
