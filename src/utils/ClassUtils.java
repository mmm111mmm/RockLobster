package utils;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ClassUtils {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends Class>List<T> getNonJarClassesInPackage(String packageName, T typeOfClass) throws ClassNotFoundException {
		List<T> classes = new ArrayList<T>();
		URL root = Thread.currentThread().getContextClassLoader().getResource(packageName.replace(".", "/"));

		// Filter .class files.
		File[] files = new File(root.getFile()).listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.endsWith(".class");
		    }
		});

		// Find classes implementing the passed in class type.
		for (File file : files) {
		    String className = file.getName().replaceAll(".class$", "");
		    Class<?> cls = Class.forName(packageName + "." + className);
			if (typeOfClass.isAssignableFrom(cls) && !typeOfClass.getName().equals(cls.getName())) {
		        classes.add((T) cls);
		    }
		}
		return classes;

	}

}
