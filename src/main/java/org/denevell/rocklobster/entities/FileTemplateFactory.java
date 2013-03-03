package org.denevell.rocklobster.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.denevell.rocklobster.utils.ClassUtils;


public abstract class FileTemplateFactory {
	
	public static List<FileTemplateFactory> getFactories() {
		List<FileTemplateFactory> factories = new ArrayList<FileTemplateFactory>();
		try {
			Set<Class<FileTemplateFactory>> classes = ClassUtils.getClassesInPackage("org.denevell.rocklobster.entities", FileTemplateFactory.class);
			for (Class<FileTemplateFactory> factoryClass: classes) {
				FileTemplateFactory inst = factoryClass.newInstance();
				factories.add(inst);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error grabbing the FileTemplateFactories via reflection.");
		}
		return factories;
	}

	public abstract List<FileTemplate> generatePages(List<BlogPost> bps);

}
