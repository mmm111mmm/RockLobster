package org.denevell.rocklobster.templates.infrastructure;

import java.util.ArrayList;
import java.util.List;

import org.denevell.rocklobster.blogposts.BlogPost;
import org.denevell.rocklobster.templates.PageTemplate;
import org.denevell.rocklobster.utils.ClassUtils;


public abstract class PageTemplateFactory {
	
	public static List<PageTemplateFactory> getFactories() {
		List<PageTemplateFactory> factories = new ArrayList<PageTemplateFactory>();
		try {
			List<Class<PageTemplateFactory>> classes = ClassUtils.getClassesInPackage("org.denevell.rocklobster.entities", PageTemplateFactory.class);
			for (Class<PageTemplateFactory> factoryClass: classes) {
				PageTemplateFactory inst = factoryClass.newInstance();
				factories.add(inst);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error grabbing the FileTemplateFactories via reflection.");
		}
		return factories;
	}

	public abstract List<PageTemplate> generatePages(List<BlogPost> bps);

}
