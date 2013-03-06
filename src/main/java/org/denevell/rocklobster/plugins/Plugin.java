package org.denevell.rocklobster.plugins;

import java.util.List;

import org.denevell.rocklobster.entities.BlogPost;

public interface Plugin {
	public String getOuput(List<BlogPost> bps, String[] args);
	public String getName();
}
