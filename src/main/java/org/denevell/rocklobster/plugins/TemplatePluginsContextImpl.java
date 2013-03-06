package org.denevell.rocklobster.plugins;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.denevell.rocklobster.entities.BlogPost;
import org.denevell.rocklobster.utils.ClassUtils;

import com.google.common.base.Function;

public class TemplatePluginsContextImpl implements TemplatePluginsContext {

	private List<BlogPost> mAllBlogposts;
	private HashMap<String, Plugin> mPluginsHash;

	public TemplatePluginsContextImpl(List<BlogPost> unfilteredBlogposts) {
		mAllBlogposts = unfilteredBlogposts;
		mPluginsHash = new HashMap<String, Plugin>();
		try {
			Set<Class<Plugin>> classes = ClassUtils.getClassesInPackage("org.denevell.rocklobster.plugins", Plugin.class);
			for (Class<Plugin> pl: classes) {
				Plugin inst = pl.newInstance();
				mPluginsHash.put(inst.getName(), inst);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Map<String, Object> getTemplateScopes() {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("plugins", new Function<String, String>() {
			@Override
			public String apply(String input) {
				String[] split = input.split(" ");
				String pluginName = split[0];
				Plugin plugin = mPluginsHash.get(pluginName);
				if(plugin==null) {
					return "Plugin not found";
				} else {
					return plugin.getOuput(mAllBlogposts);
				}
			}
		});
		return hm;
	}

}