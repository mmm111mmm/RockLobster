package org.denevell.rocklobster.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.denevell.rocklobster.blogposts.BlogPost;

public class MetadataUtils {

	public static String[] getDistinctValuesOfMetadata(final String metadataKey, List<BlogPost> bps) {
		Set<String> allValues = new HashSet<String>();
		for (BlogPost blogPost : bps) {
			List<String> splitList = getSplitMetadataOfKey(metadataKey, blogPost);
			allValues.addAll(splitList);
		}
		return allValues.toArray(new String[0]);
	}
	
	public static List<String> getValuesOfMetadata(final String metadataKey, List<BlogPost> bps) {
		List<String> allValues = new ArrayList<String>();
		for (BlogPost blogPost : bps) {
			List<String> splitList = getSplitMetadataOfKey(metadataKey, blogPost);
			allValues.addAll(splitList);
		}
		return allValues;
	}
	
	private static List<String> getSplitMetadataOfKey(String metadataKey, BlogPost blogPost) {
		String values = blogPost.getMetadata().get(metadataKey);
		String[] split = values.split("[\\ ,|]");
		List<String> splitList = Arrays.asList(split);
		return splitList;
	}		
	
}
