package org.denevell.rocklobster;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.denevell.rocklobster.blogposts.BlogPost;
import org.denevell.rocklobster.utils.GitUtils;
import org.denevell.rocklobster.utils.LogUtils;
import org.eclipse.jgit.lib.Repository;

import com.mdimension.jchronic.Chronic;
import com.mdimension.jchronic.utils.Span;


public class BlogPostParsing {
	
	private static Logger LOG = LogUtils.getLog(Main.class);
	private Repository mGitRepo;
	
	/**
	 * 
	 * @param gitRepo Used to add meta data from git commits
	 */
	public BlogPostParsing(Repository gitRepo) {
		mGitRepo = gitRepo;
	}
	
	public List<BlogPost> parseFilesInDirectory(String directory) throws Exception {
		ArrayList<BlogPost> bps = new ArrayList<BlogPost>();
		File[] files = new File(directory).listFiles();
		for (File f: files) {
			if(!f.isFile()) continue;
			BlogPost bp;
			try {
				bp = parseStringIntoPostAndMetadata(f);
				bps.add(bp);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		BlogPost[] sortedArray = bps.toArray(new BlogPost[0]);
		sortBlogPostsByDate(sortedArray);
		return Arrays.asList(sortedArray);
	}

	private void sortBlogPostsByDate(BlogPost[] sortedArray) {
		Arrays.sort(sortedArray, new Comparator<BlogPost>() {
			@Override
			public int compare(BlogPost o1, BlogPost o2) {
				String dateString1 = o1.getMetadata().get("date");
				String dateString2 = o2.getMetadata().get("date");
				dateString1 = dateString1.replaceAll("-\\d\\d\\d\\d", ""); // jchronic doesn't like negative time zones...
				dateString2 = dateString2.replaceAll("-\\d\\d\\d\\d", ""); // jchronic doesn't like negative time zones...
				Span blogPostDateSpan1 = Chronic.parse(dateString1);
				Span blogPostDateSpan2 = Chronic.parse(dateString2);
				long blogPost1 = (blogPostDateSpan1==null) ? 0 : blogPostDateSpan1.getBegin();
				long blogPost2 = (blogPostDateSpan2==null) ? 0 : blogPostDateSpan2.getBegin();
				if(blogPost1==0) {
					LOG.error("Sorting problem: the date on " + o1 + " could not be parsed by jchronic.");
				}
				if(blogPost2==0) {
					LOG.error("Sorting problem: the date on " + o2 + " could not be parsed by jchronic.");
				}
				if(blogPost1 == blogPost2) {
					return 0;
				} else if(blogPost1 < blogPost2) {
					return 1;
				} else {
					return -1;
				}
			}
		});
	}

	private BlogPost parseStringIntoPostAndMetadata(File f) throws Exception {
		Scanner in = new Scanner(new FileReader(f.getAbsoluteFile()));
		BlogPost bp = new BlogPost();
		boolean endOfMetadata = false;
		String str = "", t = "";
		while(in.hasNext() && (t=in.nextLine())!=null) {
			if(t.equals("") && !endOfMetadata) endOfMetadata = true;
			else if(!endOfMetadata){
				String[] matadata = t.split(":", 2);
				String key = matadata[0].toLowerCase();
				String value = matadata[1];
				bp.addMetadata(key, value.trim());
			} else {
				str+=t + "\n";
			}
		}
		// Manually add Title and date if doesn't exist
		addMissingMetadata(f, bp);
		bp.setPost(str);
		bp.setFilename(f.getName());
		return bp;
	}

	private void addMissingMetadata(File f, BlogPost bp) throws Exception {
		String name2 = f.getName();
		if(!bp.getMetadata().containsKey("title")) {
			String name = name2;
			String[] split = name.split("\\.");
			if(split.length>0) {
				bp.addMetadata("title",split[0]);
			} else {
				bp.addMetadata("title","Unknown title!");
			}
		} 
		if(!bp.getMetadata().containsKey("date")) {
			long lastModified = f.lastModified();
			try {
				lastModified = GitUtils.getFirstCommitDataForFile(mGitRepo, name2);
			} catch(Exception e) {
				e.printStackTrace();
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			bp.addMetadata("date",sdf.format(lastModified));
		} 
	}

}
