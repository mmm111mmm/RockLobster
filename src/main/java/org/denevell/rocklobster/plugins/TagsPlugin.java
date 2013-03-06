package org.denevell.rocklobster.plugins;

import java.util.List;

import org.denevell.rocklobster.entities.BlogPost;

public class TagsPlugin implements Plugin {

	@Override
	public String getOuput(List<BlogPost> bps) {
		return "tags, innit";
	}

	@Override
	public String getName() {
		return "tags";
	}

}
