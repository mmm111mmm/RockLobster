package entities;

public class PaginatedPage implements ContentAndFilename {

	private String fileName;
	private int currentPage;
	private String content;
	public PaginatedPage(String fileName, int currentPage, String content) {
		this.fileName = fileName;
		this.currentPage = currentPage;
		this.content = content;
	}

	public String getFilename() {
		return fileName;
	}

	public void setFilename(String fileName) {
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
