package org.denevell.rocklobster.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.denevell.rocklobster.entities.BlogPost;

public class MetadataUtils {

	public static String[] getDistinctValuesOfMetadata(final String metadataKey, List<BlogPost> bps) {
		Set<String> allValues = new HashSet<String>();
		for (BlogPost blogPost : bps) {
			List<String> splitList = getSplitMetadataOfKey(metadataKey, blogPost);
			allValues.addAll(splitList);
		}
		return allValues.toArray(new String[0]);
	}
	
	private static List<String> getSplitMetadataOfKey(String metadataKey, BlogPost blogPost) {
		String values = blogPost.getMetadata().get(metadataKey);
		String[] split = values.split("[\\ ,|]");
		List<String> splitList = Arrays.asList(split);
		return splitList;
	}		
	
}
