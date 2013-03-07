package org.denevell.rocklobster.plugins;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.denevell.rocklobster.entities.BlogPost;
import org.denevell.rocklobster.plugins.infrastructure.Plugin;

import com.mdimension.jchronic.Chronic;
import com.mdimension.jchronic.utils.Span;

public class PrettyDatePlugin implements Plugin {

	@Override
	public String getOuput(List<BlogPost> bps, String[] args) { 
		if(args.length!=2) throw new RuntimeException("Two arguments are required in PrettyDatePlugin.");
		String date = args[0];
		date = date.replaceAll("-\\d\\d\\d\\d", ""); // jchronic doesn't like negative time zones...
		String formatOptions = args[1];
		DateFormat dateFormat = new SimpleDateFormat(formatOptions);
		
		Span d = Chronic.parse(date);
		if(d==null) {
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
