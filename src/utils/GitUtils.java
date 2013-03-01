package utils;
import java.io.File;
import java.util.Map;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;


public class GitUtils {

	public static long getFirstCommitDataForFile(Repository r, String relativeName) throws Exception {
		Iterable<RevCommit> cs = new Git(r).log().addPath(relativeName).call();
		long t = 0;
		for (RevCommit revCommit : cs) {
			long commitTime = (long)revCommit.getCommitTime();
			t = commitTime*1000;
			break;
		}
		return t;
	}
	
	public static void cloneOrPullExistingRepository(String remoteRepo, Repository dotGitfileRepo) {
		try {
			Map<String, Ref> refs = dotGitfileRepo.getAllRefs();
			if(refs!=null && refs.size()!=0) {
				Git.wrap(dotGitfileRepo).pull().call();
			} else {
				File parentDir = dotGitfileRepo.getDirectory().getParentFile();
				parentDir.mkdirs();
				Git.cloneRepository()
					.setURI(remoteRepo)
					.setDirectory(parentDir)
					.call();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
