package org.denevell.rocklobster.plugins;

import java.util.List;

import org.denevell.rocklobster.entities.BlogPost;

public class SinglePageTagsPlugin implements Plugin {

	@Override
	public String getOuput(List<BlogPost> bps, String[] args) { 
		if(args.length!=6) throw new RuntimeException("Six arguments required in SinglePageTagsPlugin.");
		String tags = args[0];
		String[] tagsSplit = tags.split("[\\ |,]");
		String wrapperStart = args[1];
		String wrapperEnd = args[2];
		String elementStart = args[3];
		String elementEnd = args[4];
		String elementInBetween = args[5];
		
		String totalTagsOutput = "";
		for (String tag : tagsSplit) {
			if(tag==null || tag.length()==0) continue;
			String elementStartReplaced = elementStart.replaceAll("\\[tagname\\]", tag);
			String inbetweenElement = "";
			if(totalTagsOutput.length()!=0) {
				inbetweenElement=elementInBetween;
			} 
			totalTagsOutput += inbetweenElement+elementStartReplaced+tag+elementEnd;
		}
		return wrapperStart+totalTagsOutput+wrapperEnd;
	}

	@Override
	public String getName() {
		return "single-page-tags";
	}

}
