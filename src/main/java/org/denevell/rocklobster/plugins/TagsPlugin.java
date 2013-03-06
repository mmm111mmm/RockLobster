package org.denevell.rocklobster.plugins;

import java.util.List;

import org.denevell.rocklobster.entities.BlogPost;
import org.denevell.rocklobster.utils.MetadataUtils;

public class TagsPlugin implements Plugin {

	@Override
	public String getOuput(List<BlogPost> bps, String[] args) { 
		if(args.length!=5) throw new RuntimeException("Tags plugin needs five arguments: wrapperStart, wrapperEnd, elementStart, elementEnd and inbetweenElement. Separated by '||'.");
		String wrapperStart = args[0];
		String wrapperEnd = args[1];
		String elementStart = args[2];
		String elementEnd= args[3];
		String elementInBetween = args[4];
		
		String[] tags = MetadataUtils.getDistinctValuesOfMetadata("tags", bps);
		String totalTagsOutput = "";
		for (int i = 0; i < tags.length; i++) {
			if(tags==null || tags[i].length()==0) continue;
			String elementStartReplaced = elementStart.replaceAll("\\[tagname\\]", tags[i]);
			String inbetweenElement = "";
			if(totalTagsOutput.length()!=0) {
				inbetweenElement=elementInBetween;
			} 
			totalTagsOutput += inbetweenElement+elementStartReplaced+tags[i]+elementEnd;
		}
		return wrapperStart+totalTagsOutput+wrapperEnd;
	}

	@Override
	public String getName() {
		return "all-tags";
	}

}
