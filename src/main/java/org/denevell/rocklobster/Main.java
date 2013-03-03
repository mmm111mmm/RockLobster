package org.denevell.rocklobster;
import java.io.File;
import java.util.List;

import org.denevell.rocklobster.entities.BlogPost;
import org.denevell.rocklobster.entities.FileTemplateFactory;
import org.denevell.rocklobster.utils.GitUtils;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;



public class Main {
	
	private static  String URL_REMOTE_REPO; //i.e "git@github.com:denevell/BlogPosts.git";
	private static final String DIR_LOCAL_REPO = "git-repo";
	public static String sOutputDir = "";
	public static Repository sFileGitRepo;

	public static void main(String[] s) throws Exception {
		if(s.length<2) { 
			System.err.println("Please provide repository url and output dir.");
			return;
		}
		// Set up vars
		URL_REMOTE_REPO = s[0];
		sOutputDir = s[1];
		String absolutePath = new File(DIR_LOCAL_REPO).getAbsolutePath(); // For location of files
		sFileGitRepo = new FileRepository(absolutePath+"/.git"); // To refereces our git repo
		// Blog things up
		System.out.println("## Cloning (or pulling existing) git repo");
		GitUtils.cloneOrPullExistingRepository(URL_REMOTE_REPO, sFileGitRepo); 
		System.out.println("## Parsing blog files");
		List<BlogPost> bps = BlogPostParsing.parseFilesInDirectory(absolutePath);
		System.out.println("## Converting blog files from markdown");
		BlogMarkdownUtils.convertMDToHTML(bps);
		System.out.println("## Creating single and paginated pages from factories onto filesystem");
		BlogFileCreationUtils.createPosts(bps, FileTemplateFactory.getFactories());
	}
	
}