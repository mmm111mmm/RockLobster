package org.denevell.rocklobster.plugins.infrastructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.denevell.rocklobster.blogposts.BlogPost;
import org.denevell.rocklobster.utils.ClassUtils;

import com.google.common.base.Function;

public class TemplatePluginsContextImpl implements TemplatePluginsContext {

	private List<BlogPost> mAllBlogposts;
	private HashMap<String, Plugin> mPluginsHash;

	public TemplatePluginsContextImpl(List<BlogPost> unfilteredBlogposts) {
		mAllBlogposts = unfilteredBlogposts;
		mPluginsHash = new HashMap<String, Plugin>();
		try {
			List<Class<Plugin>> classes = ClassUtils.getClassesIncludingDirecory("org.denevell.rocklobster.plugins",  Plugin.class, "plugins/");
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
				String[] split = input.split("\\|\\|", 2);
				String pluginName = split[0];
				String pluginArgs = "";
				if(split.length>1) pluginArgs = split[1];
				Plugin plugin = mPluginsHash.get(pluginName);
				if(plugin==null) {
					return "Plugin not found";
				} else {
					String[] arg = pluginArgs.split("\\|\\|");
					return plugin.getOuput(mAllBlogposts, arg);
				}
			}
		});
		return hm;
	}

}