package org.denevell.rocklobster.plugins.infrastructure;

import java.util.List;

import org.denevell.rocklobster.blogposts.BlogPost;

public interface Plugin {
	public String getOuput(List<BlogPost> bps, String[] args);
	public String getName();
}
