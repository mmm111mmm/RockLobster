package org.denevell.rocklobster.entities;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.denevell.rocklobster.utils.FileUtils;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class FilteredPaginatedPageTemplateFactory extends PaginatedPageTemplateFactory {

	@Override
	public List<FileTemplate> generatePages(List<BlogPost> bps) {
		ArrayList<FileTemplate> fileTemplates = new ArrayList<FileTemplate>();
		File[] pagesTemplate = FileUtils.getFilesInDirectory(new File("."), ".*\\[.*\\]\\.\\d+\\.pagination.template");
		for (File fileTemplate : pagesTemplate) {
			String metadataKey = getMetadataAttributeFromFilename(fileTemplate.getName());
			List<FilteredBpsAndMetadata> filteredBpses = filterBlogpostsByAttribute(metadataKey, bps);
			List<FileTemplate> paginatedTemplates = getFileTemplatesByFilteredBlogPosts(fileTemplate, filteredBpses);
			fileTemplates.addAll(paginatedTemplates);
		}
		return fileTemplates;
	}

	private List<FileTemplate> getFileTemplatesByFilteredBlogPosts(File file, List<FilteredBpsAndMetadata> filteredBpses) {
		List<FileTemplate> allFilteredPaginatedTemplates = new ArrayList<FileTemplate>();;
		for (FilteredBpsAndMetadata filteredBpsByMetadata : filteredBpses) {
			setPagesTemplates(new File[] {file});
			List<FileTemplate> paginatedTemplatesForTheFilter = super.generatePages(filteredBpsByMetadata.bps);
			// Change the filename to replace metadata part, and add in the metadata the file tempalte scope
			replaceMetadataFromFilenameAndAddMetadataTemplateScope(paginatedTemplatesForTheFilter, filteredBpsByMetadata);
			allFilteredPaginatedTemplates.addAll(paginatedTemplatesForTheFilter);
		}
		return allFilteredPaginatedTemplates;
	}

	private List<FilteredBpsAndMetadata> filterBlogpostsByAttribute(final String metadataKey, List<BlogPost> bps) {
		// Get all values for the metadata
		Set<String> allValues = new HashSet<String>();
		for (BlogPost blogPost : bps) {
			List<String> splitList = getSplitMetadataOfKey(metadataKey, blogPost);
			allValues.addAll(splitList);
		}
		// Filter the bps by each value
		List<FilteredBpsAndMetadata> listOfBpsLists = new ArrayList<FilteredBpsAndMetadata>();
		for (final String metaDatavalue : allValues) {
			if(metaDatavalue.trim().length()==0) continue;
			Iterable<BlogPost> filteredBpsIterable = Iterables.filter(bps, new Predicate<BlogPost>() {
				public boolean apply(BlogPost p) {
					String value = p.getMetadata().get(metadataKey);
					return value.contains(metaDatavalue);
				}
			});
			BlogPost[] filteredBps = Iterables.toArray(filteredBpsIterable, BlogPost.class);
			List<BlogPost> filteredBpsAsList = Arrays.asList(filteredBps);
			FilteredBpsAndMetadata filteredBpsByMetadata = new FilteredBpsAndMetadata(metaDatavalue, metadataKey, filteredBpsAsList);
			listOfBpsLists.add(filteredBpsByMetadata);
		}
		
		return listOfBpsLists;
	}

	private List<String> getSplitMetadataOfKey(String metadataKey, BlogPost blogPost) {
		String values = blogPost.getMetadata().get(metadataKey);
		String[] split = values.split("[\\ ,|]");
		List<String> splitList = Arrays.asList(split);
		return splitList;
	}

	private String getMetadataAttributeFromFilename(String filename) {
		Pattern p = Pattern.compile(".*\\[(.*)\\].*");
		Matcher m = p.matcher(filename);
		m.matches();
		String metadataAttribute = m.group(1);
		return metadataAttribute;
	}
	
	private void replaceMetadataFromFilenameAndAddMetadataTemplateScope(List<FileTemplate> paginatedTemplatesForTheFilter, FilteredBpsAndMetadata metadata) {
		for (FileTemplate ft : paginatedTemplatesForTheFilter) {
			PaginatedPageTemplate filteredFile = (PaginatedPageTemplate) ft;
			String replace = ft.getPostProcessedFilename().replace("["+metadata.metadataKey+"]", metadata.metaDatavalue);
			filteredFile.setPostProcessedFilname(replace);
			ft.getTemplateScopes().put("metadata_filter", metadata.metaDatavalue);
			ft.generateContent();
		}
	}
	
	private class FilteredBpsAndMetadata {
		public List<BlogPost> bps ;
		public String metaDatavalue;
		public String metadataKey;
		public FilteredBpsAndMetadata(String metaDatavalue, String metadataKey, List<BlogPost> filteredBpsAsList) {
			this.metaDatavalue = metaDatavalue;
			this.metadataKey = metadataKey;
			this.bps = filteredBpsAsList;
		}
	}
}
