package org.denevell.rocklobster.plugins;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.denevell.rocklobster.blogposts.BlogPost;
import org.denevell.rocklobster.plugins.infrastructure.Plugin;
import org.denevell.rocklobster.utils.LogUtils;

import com.mdimension.jchronic.Chronic;
import com.mdimension.jchronic.utils.Span;

public class PrettyDatePlugin implements Plugin {
	
	private Logger LOG = LogUtils.getLog(PrettyDatePlugin.class);

	@Override
	public String getOuput(List<BlogPost> bps, String[] args) { 
		if(args.length!=2) throw new RuntimeException("Two arguments are required in PrettyDatePlugin.");
		String date = args[0];
		date = date.replaceAll("-\\d\\d\\d\\d", ""); // jchronic doesn't like negative time zones...
		String formatOptions = args[1];
		DateFormat dateFormat = new SimpleDateFormat(formatOptions);
		
		Span d = Chronic.parse(date);
		if(d==null) {
			LOG.error("The date " + date + " could not be parsed by jchronic.");
			return date;
		} else {
	 		long unixDate = d.getBegin()*1000l;
			Date parsedDate = new Date(unixDate);
			String formattedDate = dateFormat.format(parsedDate);
			return formattedDate;
		}
	}

	@Override
	public String getName() {
		return "pretty-date";
	}

}
