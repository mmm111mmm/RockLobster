package entities;

public class PaginatedPage {

	private String fileName;
	private int currentPage;
	private String content;
	public PaginatedPage(String fileName, int currentPage, String content) {
		this.fileName = fileName;
		this.currentPage = currentPage;
		this.content = content;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}
