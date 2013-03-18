package org.denevell.rocklobster.templates.infrastructure;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.denevell.rocklobster.blogposts.BlogPost;
import org.denevell.rocklobster.templates.FilteredPaginatedPageTemplate;
import org.denevell.rocklobster.templates.PageTemplate;
import org.denevell.rocklobster.utils.FileUtils;
import org.denevell.rocklobster.utils.MetadataUtils;
import org.denevell.rocklobster.utils.PaginationUtils;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class FilteredPaginatedPageTemplateFactory extends PageTemplateFactory {

	private List<BlogPost> mUnfilteredBlogposts;

	@Override
	public List<PageTemplate> generatePages(List<BlogPost> bps) {
		mUnfilteredBlogposts = bps;
		ArrayList<PageTemplate> fileTemplates = new ArrayList<PageTemplate>();
		File[] pagesTemplate = FileUtils.getFilesInDirectory(new File("."), ".*\\[.*\\]\\.\\d+\\.pagination.template.*");
		// Create a filtered pagination filter for each file found
		for (File templateFile: pagesTemplate) {
			// Filter posts by meta data value
			String metadataKey = PaginationUtils.getMetadataAttributeFromFilename(templateFile.getName());
			String[] metadataValues = MetadataUtils.getDistinctValuesOfMetadata(metadataKey, bps);
			List<FilteredPostsAndMetadata> filteredBlogPosts = filterBlogpostsByMetadataAttributes(metadataKey, metadataValues, bps);
		    // Generate a template for each filter of posts
			for (FilteredPostsAndMetadata filteredBpsAndMetadata : filteredBlogPosts) {
				// Get pagination info
			    int perPagePagination = PaginationUtils.getPaginationNumberFromFilename(templateFile.getName());
			    int totalPages = PaginationUtils.getTotalPaginationPages(filteredBpsAndMetadata.bps.size(), perPagePagination);
				List<PageTemplate> templates = generateFileTemplatesForEachPage(filteredBpsAndMetadata, templateFile, 
						perPagePagination, totalPages);
				fileTemplates.addAll(templates);
			}
		}
		return fileTemplates;
	}

	private List<FilteredPostsAndMetadata> filterBlogpostsByMetadataAttributes(final String metadataKey, String[] allMetadataValues, List<BlogPost> bps) {
		List<FilteredPostsAndMetadata> listOfBpsLists = new ArrayList<FilteredPostsAndMetadata>();
		for (final String metaDatavalue : allMetadataValues) {
			if(metaDatavalue.trim().length()==0) continue;
			Iterable<BlogPost> filteredBpsIterable = Iterables.filter(bps, new Predicate<BlogPost>() {
				public boolean apply(BlogPost p) {
					String value = p.getMetadata().get(metadataKey);
					return value.contains(metaDatavalue);
				}
			});
			List<BlogPost> filteredBps = Arrays.asList(Iterables.toArray(filteredBpsIterable, BlogPost.class));
			FilteredPostsAndMetadata filteredBpsByMetadata = new FilteredPostsAndMetadata(metaDatavalue, metadataKey, filteredBps);
			listOfBpsLists.add(filteredBpsByMetadata);
		}
		
		return listOfBpsLists;
	}
	
	private List<PageTemplate> generateFileTemplatesForEachPage(FilteredPostsAndMetadata filteredPosts, File file, int perPagePaginationNumber, int totalPages) {
		List<PageTemplate> fts = new ArrayList<PageTemplate>();
		for(int currentPage = 1;((currentPage-1)*perPagePaginationNumber)<filteredPosts.bps.size();currentPage++) {
			List<BlogPost> subList = PaginationUtils.getSublistForPagination(filteredPosts.bps, perPagePaginationNumber, currentPage);
			FilteredPaginatedPageTemplate filterTemplate = 
					new FilteredPaginatedPageTemplate(mUnfilteredBlogposts, file.getName(), subList, filteredPosts.metadataKey, filteredPosts.metadataValue, currentPage, totalPages); 
			filterTemplate.generateContent();
			fts.add(filterTemplate);		
		}
		return fts;
	}	

	public class FilteredPostsAndMetadata {
		public List<BlogPost> bps ;
		public String metadataKey;
		public String metadataValue;
		public FilteredPostsAndMetadata(String metaDatavalue, String metadataKey, List<BlogPost> filteredBps) {
			this.metadataValue = metaDatavalue;
			this.metadataKey = metadataKey;
			this.bps = filteredBps;
		}
	}
}
