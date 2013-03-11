package org.denevell.rocklobster.plugins;

import java.util.List;

import org.denevell.rocklobster.blogposts.BlogPost;
import org.denevell.rocklobster.plugins.infrastructure.Plugin;

public class ExamplePlugin implements Plugin {

	@Override
	public String getOuput(List<BlogPost> bps, String[] args) {
		return "Example plugin output";
	}

	@Override
	public String getName() {
		return "example-plugin";
	}

}
