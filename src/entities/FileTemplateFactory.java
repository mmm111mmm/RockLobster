package entities;

import java.util.ArrayList;
import java.util.List;

import utils.ClassUtils;

public abstract class FileTemplateFactory {
	
	public static List<FileTemplateFactory> getFactories() {
		List<FileTemplateFactory> factories = new ArrayList<FileTemplateFactory>();
		try {
			List<Class<FileTemplateFactory>> classes = ClassUtils.getNonJarClassesInPackage("entities", FileTemplateFactory.class);
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
