import java.io.File;
import java.util.ArrayList;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;

import utils.GitUtils;


import entities.BlogPost;
import entities.Page;


public class Main {
	
	private static final String URL_REMOTE_REPO = "git@github.com:denevell/BlogPosts.git";
	private static final String DIR_LOCAL_REPO = "git-repo";
	public static Repository sFileGitRepo;

	/**
	 * Welcome to the post procedural Java program I've ever written
	 */
	public static void main(String[] s) throws Exception {
		String absolutePath = new File(DIR_LOCAL_REPO).getAbsolutePath(); // For location of files
		sFileGitRepo = new FileRepository(absolutePath+"/.git"); // To refereces our git repo
		
		GitUtils.cloneOrPullExistingRepository(URL_REMOTE_REPO, sFileGitRepo); 
		ArrayList<BlogPost> bps = BlogPostParsing.parseFilesInDirectory(absolutePath);
		BlogMarkdownUtils.convertMDToHTML(bps);
		ArrayList<Page> pages = BlogTemplateUtils.convertBlogPostToSinglePages(bps);
		BlogFileCreationUtils.createPosts(pages);
	}
	



}
