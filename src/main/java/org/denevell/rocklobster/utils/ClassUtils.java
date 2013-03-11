package org.denevell.rocklobster.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import com.google.common.collect.Lists;

public class ClassUtils {
	
	@SuppressWarnings("unchecked")
	public static <T>List<T> getClassesInPackage(String packageName, T typeOfClass) throws ClassNotFoundException {
		List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
		classLoadersList.add(ClasspathHelper.contextClassLoader());
		classLoadersList.add(ClasspathHelper.staticClassLoader());

		Reflections reflections = new Reflections(new ConfigurationBuilder()
		    .setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner())
		    .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
		    .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(packageName))));	
		
		Class<T> type = (Class<T>) typeOfClass;
		Set<T> allClasses = (Set<T>) reflections.getSubTypesOf(type);
		ArrayList<T> newArrayList = Lists.newArrayList(allClasses);
		return newArrayList;
	}

}
